#!/usr/bin/env bash

# Copyright 2022 Jiaqi Liu. All rights reserved.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     https://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

# run parent(HBase) init
sh /etc/hbase-init.sh

# Prepare for Pig tutorial - http://pig.apache.org/docs/latest/start.html#tutorial
cd $PIG_HOME
ant
rm pig-*-SNAPSHOT-core-h2.jar # compile cleanup

# Create the pigtutorial.tar.gz file
cd tutorial
ant

# Unzip the pigtutorial.tar.gz file
tar -xzvf pigtutorial.tar.gz
cd pigtmp

# Load tutorial data to HDFS
hdfs dfs -mkdir -p /user/root/
hdfs dfs -copyFromLocal excite.log.bz2 /user/root

# Load files to HBase demo
cd $PIG_HOME/lib
hdfs dfs -mkdir -p /user/root/tourist
hdfs dfs -put tourist.avro /user/root/tourist

/bin/bash # enter container shell
