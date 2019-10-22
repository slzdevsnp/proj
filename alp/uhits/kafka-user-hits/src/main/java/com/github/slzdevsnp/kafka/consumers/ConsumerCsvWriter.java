package com.github.slzdevsnp.kafka.consumers;

import com.github.slzdevsnp.kafka.sinks.UserFootPrintsCsvWriter;
import com.google.gson.JsonParser;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.*;
import java.util.Arrays;
import java.util.Properties;

public class ConsumerCsvWriter {


    private static KafkaConsumer<String,String> mkConsumer(String bootstrapServers,
                                                    String groupId,
                                                    String topic ){

        // create consumer configs
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

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

    public static void main(String[] args) {

        final Logger logger = LoggerFactory.getLogger(ConsumerCsvWriter.class);


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
        String groupId = "users_csv_app";
        */


        int EXIT_EMPTY_COUNTS=5;

        final String csvFile = "/tmp/stream_prd.csv";
        UserFootPrintsCsvWriter fwriter = new UserFootPrintsCsvWriter(csvFile);

        KafkaConsumer<String,String> consumer = mkConsumer(bootstrapServers,groupId,topic);


        int emptyCounts = 0;
        int recCount = 0;
        //poll data indefinitely untill no data is incoming for 3 seconds
        while(true){
            ConsumerRecords<String, String> records =
                    consumer.poll(Duration.ofMillis(100));
            if (records.count() == 0){
                emptyCounts += 1;
                if (emptyCounts >= EXIT_EMPTY_COUNTS){ break;}
                //thread sleep for 1 sec
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for(ConsumerRecord<String,String> record: records){
                String uid  = extractUidFromFrame(record.value());
                Long ts =  extractTsFromFrame(record.value());
                LocalDateTime datetime =  LocalDateTime.ofInstant(Instant.ofEpochSecond(ts),
                        ZoneId.systemDefault());

                if (recCount % 10000 == 0 ){
                    logger.info("current record: uid: " + uid + " epoch: " + ts + " datetime: " + datetime );
                    logger.info("processed " + recCount + " records..");
                }
                fwriter.appendDataLine(uid,ts);
                recCount +=1;
            }
        }
        logger.info("Exiting... Seen " + recCount  + " records.");
    }
}
