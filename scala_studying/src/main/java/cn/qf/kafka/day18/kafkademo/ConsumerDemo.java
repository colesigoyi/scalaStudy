package cn.qf.kafka.day18.kafkademo;




import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Collections;
import java.util.Iterator;
import java.util.Properties;

/**
 * Description：实现consumer,用于消费kafka的数据并打印<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年11月13日
 *
 * @author 陶雪峰
 * @version : 1.0
 */


public class ConsumerDemo {
    public static void main(String[] args) {
        Properties prop = new Properties();
        //指定消费kafka的集群列表
        prop.put("bootstrap.servers","192.168.8.101:9092,192.168.8.102:9092,192.168.8.103:9092");
        //指定组名
        prop.put("group.id", "ConsumerTest");
        //如果zookeeper没有offset或者offset的值超出范围,name就给个初试offset
        //earliest, Lartest
        prop.put("auto.offset.reset", "earliest");
        //指定key的序列化类
        prop.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        //指定value的序列化类
        prop.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        //实例化对象
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(prop);

        //需要先订阅topic,可以消费多个topic的数据
        consumer.subscribe(Collections.singletonList("test"));
        //消费数据
        while (true) {
            //超时时间
            ConsumerRecords<String, String> poll = consumer.poll(1000);
            Iterator<ConsumerRecord<String, String>> it = poll.iterator();
            while (it.hasNext()) {
                ConsumerRecord<String ,String > msg = it.next();
                System.out.println("topic:" + msg.topic());
                System.out.println("offset:" + msg.offset());
                System.out.println("partition:" + msg.partition());
                System.out.println("topic:" + msg.value());
            }
        }
    }
}
