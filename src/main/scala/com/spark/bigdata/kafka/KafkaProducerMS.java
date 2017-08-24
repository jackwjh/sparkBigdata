package com.spark.bigdata.kafka;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class KafkaProducerMS {

    private static  KafkaProducer<String, String> kafkaProducer=KafkaUtil.getProducer();

    private static ExecutorService executors=Executors.newFixedThreadPool(10);

    /**
     *
     * @param topic
     * @param key
     * @param values
     */
    public static void KafakSendMSG(String topic,String key,String values){

        try {
            ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic,key,values);
            kafkaProducer.send(record, new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    if(e!=null) {
                        int partition = recordMetadata.partition();
                        long offset = recordMetadata.offset();
                        System.out.println("the msg partition is :" + partition + "offset:" + offset);
                    }
                }
            });
        }catch (Exception e){

            e.printStackTrace();
        }

    }
}
