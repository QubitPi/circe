[![License Badge](https://img.shields.io/badge/Apache%202.0-F25910.svg?style=for-the-badge&logo=Apache&logoColor=white) ](https://www.apache.org/licenses/LICENSE-2.0)

Circe
=====

<img src="https://user-images.githubusercontent.com/16126939/177264366-e855129d-69c5-459d-abdd-a8248a26f45f.png" width="200" />

Circe(named on the Greek goddess of transformation) is a collection of Apache Pig UDF's.

Apache Pig provides extensive support for user defined functions (UDFs) as a way to specify custom processing. The UDFs
can be implemented in multiple languages, among which Java is supported most extensively. All parts of the processing
including data load/store, column transformation, and aggregation can be customized. Java functions are also more
efficient because they are implemented in the same language as Apache Pig and because additional interfaces are
supported such as the Algebraic Interface and the Accumulator Interface.

Circe offers assorted UDFs:

* [HBase Storage Optimizer](./src/main/java/com/qubitpi/circe/Md5Hash.java)
* [HBase row key hashing](./src/main/java/com/qubitpi/circe/AvroPacker.java)

Binaries (How to Get It)
------------------------

Binaries for Circe are stored in GitHub Package. To install an circe from GitHub Packages, edit the pom.xml file to
include the package as a dependency:

```xml
<dependencies>
    <dependency>
        <groupId>com.qubitpi</groupId>
        <artifactId>circe</artifactId>
        <version>x.y.z</version>
    </dependency>
</dependencies>
```

License
-------

The use and distribution terms for this software are covered by the Apache License, Version 2.0
( http://www.apache.org/licenses/LICENSE-2.0.html ).
