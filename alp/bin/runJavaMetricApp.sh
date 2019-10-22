#!/bin/bash

broker_host=localhost:9092
topic_name=user_frames_p
group_id=users_metric_app

mvn_projdir=../uhits/kafka-user-hits
artifact=kafka-user-hits-1.0.jar
classmain=com.github.slzdevsnp.kafka.consumers.ConsumerMetricCounter

cdir=`pwd`
cd ${mvn_projdir}
classpath="target/${artifact}:target/dependency/*" \

echo $classpath

java -cp ${classpath} ${classmain} ${broker_host} ${topic_name} ${group_id}


cd ${cdir}

