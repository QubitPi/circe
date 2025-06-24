/*
 * Copyright Jiaqi Liu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.qubitpi.hadooplatin;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.pig.EvalFunc;
import org.apache.pig.data.DataByteArray;
import org.apache.pig.data.Tuple;
import org.apache.pig.impl.util.avro.AvroStorageDataConversionUtilities;

import net.jcip.annotations.Immutable;
import net.jcip.annotations.ThreadSafe;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * An UDF that transforms a tuple into a serialized Avro row in byte array.
 * <p>
 * This UDF is designed for HBase data storage efficiency. According to
 * {@code https://hbase.apache.org/book.html#physical.view}, a row key is repeated for each column qualifier. This is
 * going to be a lot of row key and timestamp duplications which together waste too much storage spaces in HBase. A good
 * solution for it is to deduplicate by collapsing all of the value columns of a single row key into one value column.
 * This UDF does this deduplication.
 * <p>
 * {@code AvroPacker} is an Eval Function. The UDF collapses the non-row-key columns of a row into a single Avro record
 * and then avro-serializes it into byte array so that each column value can be retrieved very fast using Avro API on
 * the byte array later when an HBase row is retrieved back.
 * <p>
 * Here is an example usage in a Pig script:
 * <pre>
 * {@code
 * REGISTER UDF-<version>.jar;
 * DEFINE AvroPacker org.qubitpi.hadooplatin.AvroPacker('schema.avsc', '/path/to/schema.avsc');
 *
 * relation =
 *     FOREACH relation
 *         GENERATE
 *             (chararray) row_key                                    AS row_key:chararray,
 *             (bytearray) AvroPacker(column1, column2, ..., columnN) AS values:bytearray;
 * }
 * </pre>
 * Note that the {@code schema.avsc} is the schema of the Avro representation of the tuple being
 * collapsed, i.e. {@code column1}, {@code column2}, ..., {@code column N}. The {@code /path/to/schema.avsc} is the path
 * on HDFS. This means you will need to load the {@code schema.avsc} file to HDFS before running Pig script.
 */
@Immutable
@ThreadSafe
public class AvroPacker extends EvalFunc<DataByteArray> {

    /**
     * The Avro schema for which to determine the type for input tuple.
     */
    private final Schema schema;

    /**
     * A singleton list of HDFS location of the Avro schema file.
     */
    private final List<String> schemaFileHdfsPath;

    /**
     * Writs data of the schema out to an {@code OutputStream}.
     */
    private final GenericDatumWriter<GenericData.Record> genericDatumWriter;

    /**
     * Constructs a new {@code AvroPacker} with the specified path to the Avro schema for which to determine
     * the type for input tuple.
     * <p>
     * This constructor calls {@link #AvroPacker(Schema, String)}.
     *
     * @param schemaFileName  The path to the Avro schema
     * @param schemaFileHdfsPath  A singleton list of HDFS location of the Avro schema file
     *
     * @throws NullPointerException if any one of the constructor arguments is {@code null}
     * @throws IOException if error occurs while parsing the schema file
     */
    @SuppressWarnings("checkstyle:MultipleStringLiterals")
    public AvroPacker(final String schemaFileName, final String schemaFileHdfsPath) throws IOException {
        // A new schema parser has to be instantiated at each UDF run to avoid "cannot redefine" runtime error.
        this(
                new Schema.Parser()
                        .parse(
                                new File(
                                        Objects.requireNonNull(schemaFileName, "schemaFileName")
                                )
                        ),
                Objects.requireNonNull(schemaFileHdfsPath, "schemaFileHdfsPath")
        );
    }

    /**
     * Constructs a new {@code AvroPacker} with the specified Avro schema for which to determine the type for input
     * tuple.
     *
     * @param schema  The Avro schema
     * @param schemaFileHdfsPath  A singleton list of HDFS location of the Avro schema file
     *
     * @throws NullPointerException if any one of the constructor arguments is {@code null}
     */
    @SuppressWarnings("checkstyle:MultipleStringLiterals")
    public AvroPacker(final Schema schema, final String schemaFileHdfsPath) {
        this.schema = Objects.requireNonNull(schema);
        this.genericDatumWriter = new GenericDatumWriter<>(schema);
        this.schemaFileHdfsPath = Collections.singletonList(
                Objects.requireNonNull(schemaFileHdfsPath, "schemaFileHdfsPath")
        );
    }

    @Override
    public DataByteArray exec(final Tuple input) throws IOException {
        if (input == null || input.size() == 0) {
            return null;
        }

        final GenericData.Record record = AvroStorageDataConversionUtilities.packIntoAvro(input, schema);

        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final BinaryEncoder encoder = EncoderFactory.get().binaryEncoder(out, null);
        genericDatumWriter.write(record, encoder);
        encoder.flush();
        out.close();

        return new DataByteArray(out.toByteArray());
    }

    @Override
    public List<String> getCacheFiles() {
        return schemaFileHdfsPath;
    }
}
