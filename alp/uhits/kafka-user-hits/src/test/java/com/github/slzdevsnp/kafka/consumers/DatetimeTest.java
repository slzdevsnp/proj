package com.github.slzdevsnp.kafka.consumers;


import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static org.assertj.core.api.Assertions.*;

public class DatetimeTest {


    @Test
    void testEpochMinutes(){
        long et1 =  1468244403;
        long et1m = 1468244400;
        long et1_t = et1 / 60 * 60;

        LocalDateTime et1_dt =  LocalDateTime.ofInstant(Instant.ofEpochSecond(et1),ZoneId.systemDefault());
        LocalDateTime et1_t_dt =  LocalDateTime.ofInstant(Instant.ofEpochSecond(et1_t),ZoneId.systemDefault());
        System.out.println("edt1: " + et1_dt + " et1_t: "+et1_t_dt);
        assertThat(new Long(et1_t)).isEqualTo(new Long(et1m));

    }

    @Test
    void testHashMap(){
        HashMap<Long, Set<String>> m = new LinkedHashMap<Long,Set<String>>();

        List<Long> tkeys = Arrays.asList(new Long(1468244400),
                                            new Long(1468244400),
                                            new Long(1468244460));
        List<String> users = Arrays.asList("xxxx","yyyy","xxxx");

        Set<String>  cset;

        for (int i = 0; i < tkeys.size();i++) {

            if (m.containsKey(tkeys.get(i))) {
                cset = m.get(tkeys.get(i));
                cset.add(users.get(i));
            } else {
                cset = new HashSet<String>();
                cset.add(users.get(i));
                m.put(tkeys.get(i), cset);
            }
        }
        System.out.println("map: " + m);

        //compute stat
        int ncnt = 0;
        for (Long k : m.keySet()){
            ncnt += m.get(k).size();
        }
        double avg_stat = 1.0* ncnt / m.size();
        System.out.println("metric is: " + avg_stat);

        assertThat(m.size()).isEqualTo(2);

    }

}
