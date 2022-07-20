![circe](https://socialify.git.ci/QubitPi/circe/image?description=1&font=Inter&issues=1&language=1&logo=https%3A%2F%2Fuser-images.githubusercontent.com%2F16126939%2F177421632-30a61ffb-c656-4101-a49d-8e3ddb1b9bee.png&owner=1&pulls=1&theme=Light)

[ ![Project Management](https://img.shields.io/badge/Project%20Management-0052CC?style=for-the-badge&logo=trello&logoColor=white) ](https://trello.com/b/crIQuEeA)
[![License Badge](https://img.shields.io/badge/Apache%202.0-F25910.svg?style=for-the-badge&logo=Apache&logoColor=white) ](https://www.apache.org/licenses/LICENSE-2.0)

[git history](./gource/gource.mp4)

Circe
=====

<img src="https://user-images.githubusercontent.com/16126939/177421632-30a61ffb-c656-4101-a49d-8e3ddb1b9bee.png" width="200" />

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

[![ci](https://github.com/QubitPi/circe/actions/workflows/release.yml/badge.svg?branch=master)](https://github.com/QubitPi/circe/actions/workflows/release.yml)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=QubitPi_circe&metric=coverage)](https://sonarcloud.io/summary/new_code?id=QubitPi_circe)


Binaries for Circe are stored in [GitHub Package](https://github.com/QubitPi/circe/packages/1520507). To install an
circe from GitHub Packages, edit the pom.xml file to include the package as a dependency:

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
