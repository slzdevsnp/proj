#!/bin/bash

# kafka-consumer-groups.sh  --bootstrap-server localhost:9092 --list
# kafka-consumer-groups.sh  --bootstrap-server localhost:9092 --group users_metric_app --describe
broker_host=localhost:9092
topic_name=user_frames_p
group_name=users_metric_app
offset_to="--to-earliest" # "--to-latest"  "--to-offset 10L"


kafka-consumer-groups.sh  --bootstrap-server ${broker_host} \
	--group ${group_name} --topic ${topic_name} \
       	--reset-offsets --execute ${offset_to} 


