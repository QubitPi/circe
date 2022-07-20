![circe](https://socialify.git.ci/QubitPi/circe/image?description=1&font=Inter&issues=1&language=1&logo=https%3A%2F%2Fuser-images.githubusercontent.com%2F16126939%2F177421632-30a61ffb-c656-4101-a49d-8e3ddb1b9bee.png&owner=1&pulls=1&theme=Light)

<div align="center">
<a href="https://trello.com/b/crIQuEeA"><img alt="Project Management" src="https://img.shields.io/badge/Project%20Management-0052CC?style=for-the-badge&logo=trello&logoColor=white"></a>
<a href="https://www.apache.org/licenses/LICENSE-2.0"><img alt="License Badge" src="https://img.shields.io/badge/Apache%202.0-F25910.svg?style=for-the-badge&logo=Apache&logoColor=white"></a>
<img alt="GitHub last commit" src="https://img.shields.io/github/last-commit/QubitPi/circe?style=for-the-badge">
<a href="https://github.com/QubitPi/circe/actions/workflows/release.yml"><img alt="GitHub Workflow Status" src="https://img.shields.io/github/workflow/status/QubitPi/circe/Release?logo=github&style=for-the-badge"></a>

<a href="https://sonarcloud.io/summary/new_code?id=QubitPi_circe"><img alt="Quality gate" src="https://sonarcloud.io/api/project_badges/quality_gate?project=QubitPi_circe"></a>

<a href="https://sonarcloud.io/summary/new_code?id=QubitPi_circe"><img alt="SonarCloud" src="https://sonarcloud.io/images/project_badges/sonarcloud-orange.svg"></a>

[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=QubitPi_circe&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=QubitPi_circe)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=QubitPi_circe&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=QubitPi_circe)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=QubitPi_circe&metric=coverage)](https://sonarcloud.io/summary/new_code?id=QubitPi_circe)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=QubitPi_circe&metric=sqale_index)](https://sonarcloud.io/summary/new_code?id=QubitPi_circe)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=QubitPi_circe&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=QubitPi_circe)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=QubitPi_circe&metric=reliability_rating)](https://sonarcloud.io/summary/new_code?id=QubitPi_circe)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=QubitPi_circe&metric=duplicated_lines_density)](https://sonarcloud.io/summary/new_code?id=QubitPi_circe)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=QubitPi_circe&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=QubitPi_circe)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=QubitPi_circe&metric=bugs)](https://sonarcloud.io/summary/new_code?id=QubitPi_circe)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=QubitPi_circe&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=QubitPi_circe)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=QubitPi_circe&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=QubitPi_circe)

https://user-images.githubusercontent.com/16126939/179881795-cf3b51ef-9748-43e3-8f3b-86be59538187.mp4

</div>

Circe
=====

Circe(named on the Greek goddess of transformation) is a collection of Apache Pig UDF's.

Apache Pig provides extensive support for user defined functions (UDFs) as a way to specify custom processing. The UDFs
can be implemented in multiple languages, among which Java is supported most extensively. All parts of the processing
including data load/store, column transformation, and aggregation can be customized. Java functions are also more
efficient because they are implemented in the same language as Apache Pig and because additional interfaces are
supported such as the Algebraic Interface and the Accumulator Interface.

Circe offers assorted UDFs:

* [HBase Storage Optimizer](https://qubitpi.github.io/circe/apidocs/com/qubitpi/circe/Md5Hash.html)
* [HBase row key hashing](https://qubitpi.github.io/circe/apidocs/com/qubitpi/circe/AvroPacker.html)

Binaries (How to Get It)
------------------------

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
