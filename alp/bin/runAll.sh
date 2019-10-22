#!/bin/bash

#############################################
#  prerequisites:
# java 8,mvn, $KAFKA_HOME/bin expected to be in the unix $PATH 
# in $KAFKA_HOME/config/server.properties   delete.topic.enable=true
#############################################


#set top variables to appropriate values

broker_host=localhost:9092
zookeeper_host=localhost:2181


## do not touch the params below 
path=/tmp
jsnfname=stream_prd.jsonl

mvn_projdir=../uhits/kafka-user-hits
artifact=kafka-user-hits-1.0.jar
classmain=com.github.slzdevsnp.kafka.consumers.ConsumerMetricCounter

generator=./data-generator-osx

topic_name=user_frames_p
npartitions=3
repl_factor=1

group_id=users_metric_app


### steps######

echo ========================
echo ==== step: re-create kafka topic $topic_name ..
echo ========================

echo  == attempt to delete topic $topic_name ..
kafka-topics.sh --bootstrap-server ${broker_host} \
 --topic ${topic_name} --delete

echo  == attempt to create topic $topic_name ..
kafka-topics.sh --bootstrap-server ${broker_host} \
 --topic ${topic_name} --create \
 --partitions ${npartitions} --replication-factor ${repl_factor} 

echo  == verify the topic $topic_name ..
kafka-topics.sh --bootstrap-server ${broker_host} \
--topic ${topic_name}  --describe

echo ========================
echo step: generating 1M json dataset. can take up to a 1 min..
echo ========================
#large dataset
cnt=1000000
rval=1000
nval=100000

${generator} -c ${cnt} -o ${path}/${jsnfname} -r ${rval} -n ${nval}


echo ========================
echo === step: enqueue the generated dataset to the kafka topic ${topic_name}
echo ========================

cat ${path}/${jsnfname} | kafka-console-producer.sh \
                            --broker-list ${broker_host} \
                            --topic ${topic_name}

echo ========================
echo ==== step: compile java mavan project..
echo ========================

cdir=`pwd`

cd ${mvn_projdir}
mvn clean package
cd ${cdir}

echo ========================
echo == step: run the java metrics app
echo ========================
cd ${mvn_projdir}
classpath="target/${artifact}:target/dependency/*" 
java -cp ${classpath} ${classmain} ${broker_host} ${topic_name} ${group_id}
cd ${cdir}


exit 0









