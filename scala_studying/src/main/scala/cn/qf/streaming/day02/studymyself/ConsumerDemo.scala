package cn.qf.streaming.day02.studymyself

import java.util.Properties

import kafka.consumer.{Consumer, ConsumerConfig, ConsumerConnector, ConsumerIterator, KafkaStream}
import kafka.message.MessageAndMetadata

import scala.actors.threadpool.{ExecutorService, Executors}
import scala.collection.mutable

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年11月13日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
class ConsumerDemo(val consumer:String, val stream: KafkaStream[Array[Byte], Array[Byte]])
  extends Runnable{
  override def run(): Unit = {
    val it: ConsumerIterator[Array[Byte], Array[Byte]] = stream.iterator()
    while(it.hasNext()) {
      val data: MessageAndMetadata[Array[Byte], Array[Byte]] = it.next()
      val topic: String = data.topic
      val partition: Int = data.partition
      val offset: Long = data.offset
      val msg: String = new String(data.message())
      println(s"Consumer: $consumer, Topic: $topic, Partition: $partition, Offset: $offset, msg: $msg")
    }
  }
}
object KafkaConsumerTest{
  def main(args: Array[String]): Unit = {
    // 定义用来读取数据的topic
    val topic = "KafkaSimple"

    // 用来存储多个topic
    val topics = new mutable.HashMap[String, Int]()
    topics.put(topic, 2)

    // 配置文件信息
    val props = new Properties()
    // ConsumerGroup id
    props.put("group.id", "group4")
    // 指定zookeeper的地址列表, 注意：value里不要有空格
    props.put("zookeeper.connect", "192.168.8.101:2181,192.168.8.102:2181,192.168.8.103:2181")
    // 如果zookeeper没有offset值或者offset值超出范围，需要指定一个初始的offset
    props.put("auto.offset.reset", "smallest")

    // 把配置信息封装到ConsumerConfig对象里
    val config = new ConsumerConfig(props)

    // 创建Consumer实例，如果没有数据，会一直线程等待
    val consumer: ConsumerConnector = Consumer.create(config)

    // 根据所传的topics来获取数据,得到一个stream流
    val streams: collection.Map[String, List[KafkaStream[Array[Byte], Array[Byte]]]] =
      consumer.createMessageStreams(topics)

    // 获取指定topic的数据
    val stream: Option[List[KafkaStream[Array[Byte], Array[Byte]]]] = streams.get(topic)

    // 创建一个固定大小的线程池
    val pool: ExecutorService = Executors.newFixedThreadPool(3)

    for (i <- 0 until stream.size){
      pool.execute(new ConsumerDemo(s"Consumer: $i", stream.get(i)))
    }

  }
}