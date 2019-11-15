//package cn.qf.streaming.day05.receiver0_8
//
//import cn.qf.util.SparkUtil
//import kafka.common.TopicAndPartition
//import kafka.message.MessageAndMetadata
//import kafka.serializer.StringDecoder
//import kafka.utils.{ZKGroupTopicDirs, ZKStringSerializer, ZkUtils}
//import org.apache.spark.SparkConf
//import org.apache.spark.streaming.{Durations, StreamingContext}
//import org.I0Itec.zkclient.ZkClient
//import org.apache.spark.streaming.dstream.InputDStream
//
//import org.apache.spark.streaming.kafka010.{HasOffsetRanges, KafkaUtils, OffsetRange}
//
///**
// * Description：直连<br/>
// * Copyright (c) ,2019 , Xuefengtao <br/>
// * This program is protected by copyright laws. <br/>
// * Date： 2019年11月15日
// *
// * @author 陶雪峰
// * @version : 1.0
// */
//object Direct0_8Demo {
//  def main(args: Array[String]): Unit = {
//    val conf: SparkConf = SparkUtil.getSparkConf()
//    val ssc: StreamingContext = new StreamingContext(conf, Durations.seconds(5))
//    ssc.checkpoint("file:////Users/taoxuefeng/Documents/02_StudyCoding/" +
//      "09_scala/scala_studying/checkpoint/20191115_direct0_8/")
//
//    val groupId = "directdemo"
//    val topics = Set("test1115")
//    val topic = "test1115"
//    val brokerList = "192.168.8.101:9092,192.168.8.102:9092,192.168.8.103:9092"
//    val zkQuorum = "192.168.8.101:2181,192.168.8.102:2181,192.168.8.103:2181"
//    val kafkaConf = Map(
//      "metadata.broker.list" -> brokerList,
//      "group.id" -> groupId,
//      //当offset无法找到或者offset超出范围时,用什么方式消费
//      "auto.offset.reset" -> kafka.api.OffsetRequest.EarliestTime
//    )
//    //准备用于请求zk的工作
//    //1.拿到zkClient
//    //2.获取zk存的offset偏移量
//    //3.通过offset消费数据
//    //4.处理数据,然后更新偏移量offset
//
//    //获取消费者组消费的topic对应的offset的Dir
//    val topicDirs: ZKGroupTopicDirs = new ZKGroupTopicDirs(groupId, topic)
//    val zkTopicPath: String = topicDirs.consumerOffsetDir
//
//    //拿到zkClient,用于请求zk
//    val zkClient: ZkClient = new ZkClient(zkQuorum, 1000 ,10, ZKStringSerializer)
//    val client: Int = zkClient.countChildren(zkTopicPath)
//
//    //创建输入流,用于存放消费到的数据
//    val kafkaStream:InputDStream[ (String, String)] = null
//    //创建一个存放topic和partition和offset的变量
//    var fromOffset:Map[TopicAndPartition, Long] = Map()
//    //因为不确定是否是初次消费某topic的数据,所以不确定zk中是否有维护offset,必须有如下判断
//    if (client > 0) {
//      //维护过偏移量
//      //获取每个分区offset
//      for (i <- 0 until client) {
//        //取offset
//        val partitionOffsets: Nothing = zkClient.readData(s"${zkTopicPath}/${i}")
//        //加载不同分区的offset
//        val topicAndPartition: TopicAndPartition = TopicAndPartition(topic, i)
//        //将topic和partition和offset加载fromOffset
//        fromOffset += (topicAndPartition -> partitionOffsets.toString.toLong)
//      }
//      val messageHandler: MessageAndMetadata[String, String] => (String, String)
//      = (mmd: MessageAndMetadata[String, String]) => {
//        (mmd.key(), mmd.message())
//      }
//      //获取Kafka中topick的数据,其中的泛型指: [K, v, KD <: Decoder[K], VD <: Decoder[V], R]
//      kafkaStream = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder, (String, String)](
//        ssc,
//        kafkaConf,
//        fromOffset,
//        messageHandler
//      )
//    }else {
//      kafkaStream = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](ssc, kafkaConf, topics)
//    }
//    //offset的范围
//    var offsetRange = Array[OffsetRange]()
//
//    kafkaStream.foreachRDD(rdd => {
//      //首先获取offset,每消费一条数据,就更新一下offset,然后将最终的offset保存在zk
//      offsetRange = rdd.asInstanceOf[HasOffsetRanges].offsetRanges
//      println("---------" + offsetRange.mkString(","))
//      //
//      println(rdd.map(_._2).collect.toBuffer)
//      for (o <- offsetRange) {
//        val zkPath = s"${topicDirs.consumerOffsetDir}/${o.partition}"
//        println("zkPath-------" + zkPath)
//        ZkUtils.updatePersistentPath(zkClient, zkPath, o.untilOffset.toString)
//      }
//    })
//    SparkUtil.getStart(ssc)
//  }
//}
