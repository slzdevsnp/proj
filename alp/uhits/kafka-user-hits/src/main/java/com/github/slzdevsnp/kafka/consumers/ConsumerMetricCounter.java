package com.github.slzdevsnp.kafka.consumers;

import com.google.gson.JsonParser;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

public class ConsumerMetricCounter {


    private static KafkaConsumer<String,String> mkConsumer(String bootstrapServers,
                                                    String groupId,
                                                    String topic ){

        // create consumer configs
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"); //earliest/latest/none

        // create consumer
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);

        // subscribe consumer to our topic(s)
        consumer.subscribe(Arrays.asList(topic)); //subscribe to a collection of topics


        return consumer;
    }
    private static JsonParser jsonParser = new JsonParser();
    private static String extractUidFromFrame(String frameJson){
        try {
            return jsonParser.parse(frameJson)
                    .getAsJsonObject()
                    .get("uid")
                    .getAsString();
        }catch(NullPointerException e){
            return "";
        }
    }
    private static Long extractTsFromFrame(String frameJson){
        //gson
        try {
            return jsonParser.parse(frameJson)
                    .getAsJsonObject()
                    .get("ts")
                    .getAsLong();
        }catch(NullPointerException e){
            return 0L;
        }
    }
    private static void updateMetrics(Map<Long,Set<String>> map,
                                       Long ts, String uid){
        Long ts_m1= ts / 60 * 60; //arithmetic division
        if (map.containsKey(ts_m1)){
            map.get(ts_m1).add(uid);
        }else {
            HashSet<String> cset = new HashSet<>();
            cset.add(uid);
            map.put(ts_m1, cset);
        }
    }
    private static double computeAverage(Map<Long,Set<String>> map){
        int ncnt = 0;
        for (Long k : map.keySet()){
            ncnt += map.get(k).size();
        }
        double avg_stat = 1.0* ncnt / map.size();
        return avg_stat;
    }
    public static void main(String[] args) {

        final Logger logger = LoggerFactory.getLogger(ConsumerMetricCounter.class);

         if (args.length != 3){
            logger.info("expected posititonal command line params: "
                    + "bootstrapServers  topic  groupId");
            return;
        }
        String bootstrapServers = args[0];
        String topic = args[1];
        String groupId = args[2];

        /*
        String bootstrapServers = "127.0.0.1:9092";
        String topic = "user_frames_p"; //"user_frames"
        String groupId = "users_metric_app";
        */


        int EXIT_EMPTY_COUNTS = 30;

        KafkaConsumer<String,String> consumer = mkConsumer(bootstrapServers,groupId,topic);

        //storage for the metric
        Map<Long, Set<String>> mtr = new HashMap<Long,Set<String>>();


        int emptyCounts = 0;
        int recCount = 0;
        //poll data indefinitely until no data is incomming during 200 ms * 30 times
        while(true){
            ConsumerRecords<String, String> records =
                    consumer.poll(Duration.ofMillis(200));
            if (records.count() == 0){
                emptyCounts += 1;
                if (emptyCounts >= EXIT_EMPTY_COUNTS){ break;}

            }
            for(ConsumerRecord<String,String> record: records){
                String uid  = extractUidFromFrame(record.value());
                Long ts =  extractTsFromFrame(record.value());
                LocalDateTime datetime =  LocalDateTime.ofInstant(Instant.ofEpochSecond(ts),
                        ZoneId.systemDefault());

                updateMetrics(mtr,ts,uid);

                recCount +=1;
                if (recCount % 50000 == 0){
                    String msg = String.format("With processed %d records the average number of unique users: %.3f"
                              + " per min  over %d minutes seen.",recCount,computeAverage(mtr),mtr.size());
                    logger.info(msg);
                }
            }
        }
        logger.info("Exiting... Seen " + recCount  + " records.");
    }
}
