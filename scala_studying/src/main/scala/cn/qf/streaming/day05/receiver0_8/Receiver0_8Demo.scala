//package cn.qf.streaming.day05.receiver0_8
//
//import cn.qf.util.SparkUtil
//import org.apache.spark.SparkConf
//import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
//import org.apache.spark.streaming.kafka.KafkaUtils
//import org.apache.spark.streaming.{Durations, StreamingContext}
//
///**
// * Description：<br/>
// * Copyright (c) ,2019 , Xuefengtao <br/>
// * This program is protected by copyright laws. <br/>
// * Date： 2019年11月15日
// *
// * @author 陶雪峰
// * @version : 1.0
// */
//object Receiver0_8Demo {
//  def main(args: Array[String]): Unit = {
//    val conf: SparkConf = SparkUtil.getSparkConf()
//    val ssc: StreamingContext = new StreamingContext(conf, Durations.seconds(5))
//    ssc.checkpoint("hdfs://ns1/spark-streaming/myself/checkpoint/20191115_receiver0_8/")
//    //用于消费kafka的参数
//    val zkQueue = "192.168.8.101:2181,192.168.8.102:2181,192.168.8.103:2181"
//    val groupId = "receiverdemo"
//    //key为topic, value为消费该topic用到的线程数(消费者数量)
//    val topics = Map("test1115" -> 2)
//    //Receiver消费kafka,数据为key,value为数据,key=offset, value=data
//    val msgs: ReceiverInputDStream[(String, String)] = KafkaUtils.createStream(ssc, zkQueue, groupId, topics)
//
//    //操作数据
//    val tups: DStream[(String, Int)] = msgs.flatMap(_._2.split("\\s+")).map((_, 1)).reduceByKey(_+_)
//    val value: DStream[(String, Int)] = tups.updateStateByKey(func)
//    value.print()
//
//    SparkUtil.getStart(ssc)
//  }
//  def func = (values: Seq[Int], state: Option[Int]) => {
//    //当前批次的相同key的累加和
//    val currentKey = values.sum
//    //历史批次的相同key的值
//    val historyKey = state.getOrElse(0)
//    Some(currentKey + historyKey)
//  }
//}
