package com.github.slzdevsnp.kafka.consumers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class UserFootPrintsCsvWriter {

        private String filename;
        final private Character delim = ',';
        final Logger logger = LoggerFactory.getLogger(ConsumerCsvWriter.class);

        public UserFootPrintsCsvWriter(String filename){
            this.filename = filename;
            writeHeader();
        }
        void writeHeader(){
            File file = new File(this.filename);
            if (file.exists()){
                file.delete();
            }
            String headerLine="uid"+this.delim+"epoch"+"\r\n";
            try{

                file.createNewFile();
                Files.write(Paths.get(this.filename),headerLine.getBytes(), StandardOpenOption.APPEND);
            }catch (IOException e){
                logger.error(e.toString());
            }
        }
        String fmtCsvLine(String uid, Long ts){
            return String.format("%s,%d",uid,ts)+"\r\n";
        }
        void appendDataLine(String uid, Long ts){
            try{
                Files.write(Paths.get(this.filename),fmtCsvLine(uid,ts).getBytes(),StandardOpenOption.APPEND);
            }catch(IOException e){
                logger.warn(e.toString());
            }
        }

}
