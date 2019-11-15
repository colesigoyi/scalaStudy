package cn.qf.kafka.day18.kafkademo;


import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * Description：模拟生产者,将数据不断发送到kafka<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年11月13日
 *
 * @author 陶雪峰
 * @version : 1.0
 */


public class ProducerDemo {
    public static void main(String[] args) {
        Properties prop = new Properties();
        //指定kafka的集群列表
        prop.put("bootstrap.servers","192.168.8.101:9092,192.168.8.102:9092,192.168.8.103:9092");
        //对producer的响应方式
        prop.put("request.required.acks", 1);
        //key的序列化类
        prop.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        //对value的序列化类
        prop.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        String topic = "test";

        //实例化对象
        //key:存储偏移量offset,可以为空
        //value:存储数据
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(prop);

        int i = 0;
        int max = 10000;
        while (i <= max) {
            String msg = "hello, " + i;
            producer.send(new ProducerRecord<String, String>(topic, msg));
            i++;
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
