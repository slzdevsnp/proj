#!/bin/bash

# kafka-topics.sh --bootstrap-server localhost:9092 --list
# kafka-topics.sh --bootstrap-server localhost:9092 --topic user_frames  --describe

broker_host=localhost:9092
zookeeper_host=localhost:2181
topic_name=user_frames

echo reduce msg retention to 1s
kafka-configs.sh --zookeeper ${zookeeper_host} \
 --alter --entity-type topics \
 --add-config retention.ms=1000 \
 --entity-name ${topic_name} 

echo see if value was changed
kafka-configs.sh --zookeeper ${zookeeper_host} \
 --entity-type topics \
 --describe \
 --entity-name ${topic_name} 


echo sleeping 3 secs
sleep 3

echo restore retention values to default
kafka-configs.sh --zookeeper ${zookeeper_host} \
 --alter --entity-type topics \
 --delete-config retention.ms \
 --entity-name ${topic_name} 

echo see if value was changed
kafka-configs.sh --zookeeper ${zookeeper_host} \
 --entity-type topics \
 --describe \
 --entity-name ${topic_name} 


