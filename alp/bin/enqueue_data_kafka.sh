#!/bin/bash


path=../data/inc
broker_host=localhost:9092
topic_name=user_frames

#small dataset
#topic_name=user_frames
#fname=stream_smpl.jsonl 

#large daaset
fname=stream_prd.jsonl
topic_name=user_frames_p

cat ${path}/${fname} | kafka-console-producer.sh --broker-list ${broker_host} --topic ${topic_name}

