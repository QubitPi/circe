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
package com.qubitpi.circe;

import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.util.MD5Hash;
import org.apache.pig.EvalFunc;
import org.apache.pig.data.Tuple;

import net.jcip.annotations.Immutable;
import net.jcip.annotations.ThreadSafe;

import java.io.IOException;

/**
 * An UDF that hashes the first tuple field.
 * <p>
 * This UDF is particularly useful for loading HBase using Pig scripts. Hotspotting is a common problem to avoid in
 * HBase row key design. A good strategy for that is evenly spreading row keys across region servers by hashing the key
 * using
 *
 * <pre>
 *     hex(md5(key))+key
 * </pre>
 *
 * <p>
 * Example:
 * <pre>
 * {@code
 * DEFINE Md5Hash com.qubitpi.circe.Md5Hash();
 *
 * relation =
 *     FOREACH relation
 *         GENERATE
 *             Md5Hash(row_key)  AS row_key:bytearray,
 *             (bytearray) field1 AS field1:bytearray,
 *             (bytearray) field2 AS field2:bytearray,
 *             ...
 * }
 * </pre>
 *
 * As long as the HBase table regions is pre-splitted using {@code HexStringSplit}, for example,
 *
 * <pre>
 * {@code
 * create "{table}", "d", { NUMREGIONS => 100, SPLITALGO => "HexStringSplit" }
 * disable "{table}"
 * alter "{table}", NAME => "d", VERSIONS => 2
 * enable "{table}"
 * }
 * </pre>
 *
 * then the write load will be evenly distributed.
 */
@Immutable
@ThreadSafe
public class Md5Hash extends EvalFunc<String> {

    @Override
    public String exec(final Tuple input) throws IOException {
        if (input == null || input.size() == 0) {
            return null;
        }

        return hashKey(input);
    }

    /**
     * Extracts the first field (idx = 0) of a tuple and hashes the field to hes(md5(field)) + field.
     *
     * @param input  The Tuple to be processed.
     *
     * @return the first field of the tuple which has been hashed
     *
     * @throws IOException if the tuple is empty, i.e. having no fields
     */
    protected static String hashKey(final Tuple input) throws IOException {
        final String originalKey = (String) input.get(0);

        return hashKey(originalKey);
    }

    /**
     * Hashes a string into a hex string using hex(md5(string)) + string.
     *
     * @param key  The string to be hashed
     *
     * @return the hash in hex string suffixed by the original string
     */
    protected static String hashKey(final String key) {
        return MD5Hash.getMD5AsHex(Bytes.toBytes(key)) + key;
    }
}
