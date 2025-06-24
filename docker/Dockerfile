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

FROM jack20191124/hbase

LABEL maintainer="Jiaqi (Jack) Liu"
LABEL maintainer-email="jiaqixy@protonmail.com"

ARG PIG_VERSION=0.17.0

RUN apt-get install -y ant

# Download Pig
RUN wget https://downloads.apache.org/pig/pig-$PIG_VERSION/pig-$PIG_VERSION.tar.gz
RUN tar -xzvf pig-$PIG_VERSION.tar.gz
RUN rm pig-$PIG_VERSION.tar.gz

# Rename "pig-<version>" to "pig"
RUN mv pig-$PIG_VERSION pig

# Configure Pig env variables
ENV PIG_HOME /pig
ENV PATH $PATH:$PIG_HOME/bin
ENV PIG_CLASSPATH /hadoop/etc/hadoop/
ENV HADOOP_CONF_DIR /hadoop/etc/hadoop/

# Add script for initializaing Pig interactive env.
ADD apache-pig-init.sh /etc/apache-pig-init.sh
RUN chown root:root /etc/apache-pig-init.sh
RUN chmod 700 /etc/apache-pig-init.sh
ENV INIT /etc/apache-pig-init.sh

# Add files for HBase demo
ADD hbase/script.pig $PIG_HOME/lib
ADD hbase/tourist.avro $PIG_HOME/lib
