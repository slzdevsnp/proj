#!/bin/bash

# kafka-topics.sh --bootstrap-server localhost:9092 --list
# kafka-topics.sh --bootstrap-server localhost:9092 --topic user_frames  --describe

broker_host=localhost:9092
topic_name=user_frames_p
npartitions=3
repl_factor=1

kafka-topics.sh --bootstrap-server ${broker_host} \
 --topic ${topic_name} --create \
 --partitions ${npartitions} --replication-factor ${repl_factor} 

