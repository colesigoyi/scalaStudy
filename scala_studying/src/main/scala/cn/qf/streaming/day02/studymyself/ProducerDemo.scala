package cn.qf.streaming.day02.studymyself

import java.util.Properties

import kafka.producer.{KeyedMessage, Partitioner, Producer, ProducerConfig}
import kafka.utils.VerifiableProperties

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年11月13日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object ProducerDemo {
  def main(args: Array[String]): Unit = {
    // 定义topic，把数据传到该topic
    val topic = "KafkaSimple"

    // 创建一个配置文件信息类
    val props: Properties = new Properties()
    // 数据在序列化时的编码类型
    props.put("serializer.class", "kafka.serializer.StringEncoder")
    // kafka集群列表
    props.put("metadata.broker.list", "192.168.8.101:9092,192.168.8.102:9092,192.168.8.103:9092")
    // 设置发送数据后是否需要服务端的反馈：0，1，-1
    props.put("request.required.acks", "1")
    // 调用分区器
    props.put("partitioner.class", "cn.qf.streaming.day02.studymyself.CustomPartitioner")
    //    props.put("partitioner.class", "kafka.producer.DefaultPartitioner")

    val config = new ProducerConfig(props)

    // 创建一个生产者实例
    val producer: Producer[String, String] = new Producer(config)

    // 模拟生产一些数据
    for (i <- 1 to 10000){
      val msg = s"$i: Producer send data"
      producer.send(new KeyedMessage[String, String](topic, msg))
    }


  }
}
class CustomPartitioner(props: VerifiableProperties) extends Partitioner{
  override def partition(key: Any, numPartitions: Int): Int = {
    key.hashCode() % numPartitions
  }
}