package cn.qf.streaming.day05.direct0_10

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.dstream.InputDStream
import org.apache.spark.{SparkConf, TaskContext}
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, HasOffsetRanges, KafkaUtils, LocationStrategies, OffsetRange}
import org.apache.spark.streaming.{Durations, StreamingContext}

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年11月15日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object Direct0_10 {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setAppName("DirectDemo10").setMaster("local[2]")
    val ssc: StreamingContext = new StreamingContext(conf, Durations.seconds(5))

    // 准备请求kafka的参数
    val kafkaConf: Map[String, Object] = Map[String, Object](
      "bootstrap.servers" -> "192.168.8.101:9092,192.168.8.102:9092,192.168.8.103:9092",
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> "directdemo10",
      "auto.offset.reset" -> "latest",
      "enable.auto.commit" -> (true: java.lang.Boolean) // 如果数据合法，自动提交offset
    )

    val topics: Array[String] = Array("test1115")

    // 消费数据
    val logs: InputDStream[ConsumerRecord[String, String]] = KafkaUtils.createDirectStream(
      ssc,

      /**
       * 1. LocationStrategies.PreferBrokers()
       * 仅仅在你 spark 的 executor 在相同的节点上，优先分配到存在  kafka broker 的机器上；
       * 2. LocationStrategies.PreferConsistent();
       * 大多数情况下使用，一致性的方式分配分区所有 executor 上。（主要是为了分布均匀）
       * 3. LocationStrategies.PreferFixed(hostMap: collection.Map[TopicPartition, String])
       * 4. LocationStrategies.PreferFixed(hostMap: ju.Map[TopicPartition, String])
       * 如果你的负载不均衡，可以通过这两种方式来手动指定分配方式，其他没有在 map 中指定的，均采用 preferConsistent() 的方式分配；
       */
      LocationStrategies.PreferConsistent,
      ConsumerStrategies.Subscribe[String, String](topics, kafkaConf)
    )
    // 查看消费信息
    logs.foreachRDD((rdd: RDD[ConsumerRecord[String, String]]) => {
      val o: Array[OffsetRange] = rdd.asInstanceOf[HasOffsetRanges].offsetRanges
      rdd.foreachPartition((prat: Iterator[ConsumerRecord[String, String]]) => {
        prat.foreach((line: ConsumerRecord[String, String]) => {
          val offset: OffsetRange = o(TaskContext.get().partitionId())
          println(offset.topicPartition())
          println(line)
        })
      })
    })

    ssc.start()
    ssc.awaitTermination()
  }
}
