Docker Image for Apache Pig on Hadoop
=====================================

[![GitHub Workflow Status]][GitHub Workflow URL]
[![Docker Hub][Docker Pulls Badge]][Docker Hub URL]
[![Apache License Badge]][Apache License, Version 2.0]

![apache-logo](https://user-images.githubusercontent.com/16126939/176544349-0cb18331-158a-4d27-88c2-b47a6326ab0c.png)

This image includes the following features:

- Quickly stand up an Apache Pig instance on top of a real Hadoop Distributed File System (HDFS).
- Users can interact with Apache Pig on the HDFS that mimics a production MapReduce workflow.
- The container of this image also runs on top of a HBase instance in pseudo-distributed mode
- Users could use the container to inject data into HBase through Apache Pig scripts

Get Image
---------

### Docker Hub

We can pull the image from [my docker hub](https://hub.docker.com/r/jack20191124/apachepig/):

``console
docker pull jack20191124/apachepig
``

### GitHub

We could also build the image from [my source repository](https://github.com/QubitPi/jupiter/tree/apachepig/):

```console
git clone https://github.com/QubitPi/hadoop-latin.git
cd hadoop-latin/docker
docker build -t jack20191124/apachepig .
```

Standup a Container
-------------------

Once image is on our machine(either by pulling or building), we can have Apache Pig on Mapreduce Execution Mode in 
seconds:

```console
docker run --name=apachepig -it jack20191124/apachepig /etc/apache-pig-init.sh
```

The container will spin up a HDFS in the container and takes us into its shell in the end

Run Apache Pig Tutorial Inside Container
----------------------------------------

Other than preparing a running HDFS and HBase for us on startup, the container also automatically prepares environment 
for us so that we can quickly run a Pig tutorial example:

```console
cd ../tutorial/pigtmp/
pig script1-hadoop.pig
```

The last command runs the Pig scripts in MapReduce mode. After MapReduce job finishes, we can review the result files:

```console
hdfs dfs -cat /user/root/script1-hadoop-results/*
```
    
Running Apache Pig Command
--------------------------

We can run Pig in interactive mode using the Grunt shell. Invoke the Grunt shell using the "pig" command (as shown below) 
and then enter our Pig Latin statements and Pig commands interactively at the command line.

### Example

These Pig Latin statements extract all user IDs from the `/etc/passwd` file.

First, copy the `/etc/passwd` file to our local working directory.

```console
cd home/
cp /etc/passwd .
```

Next, invoke the Grunt shell by typing the "pig" command (in local mode).

```console
pig -x local
```

Then, enter the Pig Latin statements interactively at the grunt prompt (be sure to include the semicolon after each 
statement). The DUMP operator will display the results to our terminal screen.

```console
grunt> A = load 'passwd' using PigStorage(':');
grunt> B = LIMIT A 1;
grunt> DUMP B;
```

Apache Pig with HBase
---------------------

### Simple Grunt Example

In this example, we are simply going to use the HBase shell to create a table and then load the data, manipulate, and
dump the data in Grunt.

From the HBase shell, create the `tourist` table with the column family info:

```console
$HBASE_HOME/bin/hbase shell

create "tourist", "hotel", "scenic area", "transportation"
```

Create a row with the `hotel` column family and the column keys `name` and `rating`:

```console
put "tourist", "row1", "hotel:name", "name1"
put "tourist", "row1", "hotel:rating", "5"
```

We can verify that the row has been inserted into the table:

```console
hbase(main):001:0> scan "tourist"
ROW                                 COLUMN+CELL                                                                                            
 row1                               column=hotel:name, timestamp=1532568254759, value=name1                                                
 row1                               column=hotel:rating, timestamp=1532568260126, value=5 
```

Close our HBase shell and open up Grunt(`pig`).

Load the data from the `tourist` table and display the data with the following commands:

```console
tourist = LOAD 'hbase://tourist'
USING org.apache.pig.backend.hadoop.hbase.HBaseStorage('hotel:name hotel:rating', '-loadKey true')
AS (id:bytearray, name:chararray, rating:chararray);

describe tourist
dump tourist
```

We should see a lot of logs from the map-reduce jobs, the inputs, outputs, counters, and finally the tuples containing 
our data as shown below:

```console
(row1,name1,5)
```
    
### Load Avro into HBase

In this example, we will have Apache Pig load a Avro file and write it to the HBase table we created above using a
script.

```console
cd $PIG_HOME/lib
pig script.pig
```
    
Now if we go to HBase shell and scan the same `tourist` table, we will see new data have been added by the script

```console
hbase(main):001:0> scan "tourist"
ROW                                 COLUMN+CELL                                                                                            
 row1                               column=hotel:name, timestamp=1532568254759, value=name1                                                
 row1                               column=hotel:rating, timestamp=1532568260126, value=5                                                  
 row2                               column=hotel:name, timestamp=1532568270939, value=name2                                                
 row2                               column=hotel:rating, timestamp=1532568270939, value=5                                                  
 row3                               column=hotel:name, timestamp=1532568270952, value=name3                                                
 row3                               column=hotel:rating, timestamp=1532568270952, value=4  
```

License
-------

The use and distribution terms for [Apache Pig][Docker Hub URL] are covered by the [Apache License, Version 2.0].

[Apache License Badge]: https://img.shields.io/badge/Apache%202.0-FE5D26.svg?style=for-the-badge&logo=Apache&logoColor=white
[Apache License, Version 2.0]: https://www.apache.org/licenses/LICENSE-2.0

[Docker Pulls Badge]: https://img.shields.io/docker/pulls/jack20191124/apachepig?style=for-the-badge&logo=docker&logoColor=white&labelColor=5BBCFF&color=7EA1FF
[Docker Hub URL]: https://hub.docker.com/r/jack20191124/apachepig

[GitHub Workflow Status]: https://img.shields.io/github/actions/workflow/status/QubitPi/hadoop-latin/ci-cd.yaml?branch=master&logo=github&style=for-the-badge&label=CI/CD&labelColor=2088FF
[GitHub Workflow URL]: https://github.com/QubitPi/hadoop-latin/actions/workflows/ci-cd.yaml
