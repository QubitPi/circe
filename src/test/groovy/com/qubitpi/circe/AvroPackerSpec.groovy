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
package com.qubitpi.circe

import org.apache.avro.Schema
import org.apache.avro.SchemaBuilder
import org.apache.avro.generic.GenericData
import org.apache.avro.generic.GenericDatumReader
import org.apache.avro.io.BinaryDecoder
import org.apache.avro.io.DecoderFactory
import org.apache.pig.data.DataByteArray
import org.apache.pig.data.Tuple
import org.apache.pig.data.TupleFactory

import spock.lang.Specification

class AvroPackerSpec extends Specification {

    /**
     * Avro columns
     */
    static final String COLUMN_1 = "c1"
    static final String COLUMN_2 = "c2"
    static final String COLUMN_3 = "c3"

    /**
     * Column values
     */
    static final String VALUE_1 = "v1"
    static final String VALUE_2 = "v2"
    static final String VALUE_3 = "v3"

    Schema schema

    AvroPacker udf

    def setup() {
        schema = SchemaBuilder
                .record("testSchema")
                .fields()
                        .name(COLUMN_1).type().nullable().stringType().stringDefault(null)
                        .name(COLUMN_2).type().nullable().stringType().stringDefault(null)
                        .name(COLUMN_3).type().nullable().stringType().stringDefault(null)
                .endRecord()

        udf = new AvroPacker(schema, "")
    }

    def "A null tuple gets serialized into null"() {
        expect:
        udf.exec(null) == null
    }

    def "An empty tuple gets serialized into null"() {
        given: "a tuple whose size is 0"
        Tuple tuple = Mock(Tuple) {
            size() >> 0
        }

        expect: "the tuple serializes to null"
        udf.exec(tuple) == null
    }

    def "UDF serializes a tuple into an deserializable Avro record"() {
        given: "a tuple of 3 columns with values"
        Tuple tuple = TupleFactory.getInstance().newTuple(3)
        tuple.set(0, VALUE_1)
        tuple.set(1, VALUE_2)
        tuple.set(2, VALUE_3)

        when: "the tuple is packed into an Avro record and gets deserialized"
        DataByteArray dataByteArray = udf.exec(tuple)

        and: "the Avro record is deserialized"
        BinaryDecoder decoder = DecoderFactory.get().binaryDecoder(dataByteArray.get(), null)
        GenericDatumReader<GenericData.Record> genericDatumReader = new GenericDatumReader<>(schema)
        GenericData.Record record = genericDatumReader.read(null, decoder)

        then: "the deserialization reflects the original tuple"
        ((String) record.get(COLUMN_1)) == VALUE_1
        ((String) record.get(COLUMN_2)) == VALUE_2
        ((String) record.get(COLUMN_3)) == VALUE_3

        and: "schemas match"
        record.getSchema() == schema
    }
}
