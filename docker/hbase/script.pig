REGISTER piggybank.jar
REGISTER avro-*.jar
REGISTER jackson-core-asl-*.jar
REGISTER jackson-mapper-asl-*.jar
REGISTER snappy-java-*.jar

records = LOAD 'tourist' USING org.apache.pig.piggybank.storage.avro.AvroStorage();

STORE records INTO 'hbase://tourist'
USING org.apache.pig.backend.hadoop.hbase.HBaseStorage ('hotel:name hotel:rating');
