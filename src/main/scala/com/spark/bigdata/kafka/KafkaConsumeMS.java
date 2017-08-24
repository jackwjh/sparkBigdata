package com.spark.bigdata.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.TopicPartition;

import java.util.ArrayList;
import java.util.List;

public class KafkaConsumeMS {

    private static KafkaConsumer<String, String> kafkaConsumer=KafkaUtil.getConsumer();
    final static  int minBatchSize = 200;
    public static void KafaConuseMS(List<String> topics){

        kafkaConsumer.subscribe(topics);
        kafkaConsumer.seekToBeginning(new ArrayList<TopicPartition>());
        List<ConsumerRecord<String, String>> buffer = new ArrayList<ConsumerRecord<String, String>>();
        while (true) {
            //超时
            ConsumerRecords<String, String> records = kafkaConsumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                System.out.println(record.topic());
                buffer.add(record);
            }
            /**
             *
             */
            if (buffer.size() >= minBatchSize) {

                kafkaConsumer.commitSync();
                buffer.clear();
            }
        }
    }


}
