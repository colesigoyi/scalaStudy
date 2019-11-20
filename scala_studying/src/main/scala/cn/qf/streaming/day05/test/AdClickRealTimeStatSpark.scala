package cn.qf.streaming.day05.test


import java.text.SimpleDateFormat
import java.util.Date

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, TaskContext}
import org.apache.spark.sql.types.{DataTypes, StructField, StructType}
import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.streaming.dstream.{DStream, InputDStream}
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, HasOffsetRanges, KafkaUtils, LocationStrategies, OffsetRange}
import org.apache.spark.streaming.{Durations, Seconds, StreamingContext}

import scala.collection.mutable.ListBuffer

/**
 * 广告点击流量实时统计
 * 1、使用updateStateByKey或其他操作，实时计算每天各省各城市各广告的点击量，并更新到数据库
 * 2、使用transform结合SparkSQL统计每天各省份top3热门广告（开窗函数）
 */
/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年11月15日
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object AdClickRealTimeStatSpark extends Serializable {
  def main(args: Array[String]): Unit = { // 模板代码
    val conf = new SparkConf()
      .setAppName(this.getClass.getName)
      .setMaster("local[2]")
    val ssc = new StreamingContext(conf, Seconds(5))

    ssc.checkpoint("file:////Users/taoxuefeng/Documents/02_StudyCoding/" +
      "09_scala/scala_studying/checkpoint/test1115-2")

    // 指定请求kafka的配置信息
    val kafkaParam = Map[String, Object](
      "bootstrap.servers" -> "192.168.8.101:9092,192.168.8.102:9092,192.168.8.103:9092",
      // 指定key的反序列化方式
      "key.deserializer" -> classOf[StringDeserializer],
      // 指定value的反序列化方式
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> "group1",
      // 指定消费位置
      "auto.offset.reset" -> "latest",
      // 如果value合法，自动提交offset
      "enable.auto.commit" -> (true: java.lang.Boolean)
    )

    // 指定topic
    val topics = Array("AdRealTimeLognew")

    // 消费数据
    val massage: InputDStream[ConsumerRecord[String, String]] =
      KafkaUtils.createDirectStream(
        ssc,
        LocationStrategies.PreferConsistent,
        ConsumerStrategies.Subscribe(topics, kafkaParam)
      )

    val result1: DStream[((String,String,String,String), Int)] = massage.map(perLine => {
      val arr = perLine.value().split("\\s+")
      val time:String = formatDate(arr(0).toLong)
      val city = arr(1)
      val province = arr(2)
      val userID = arr(3)
      val adID = arr(4)
      ((time,province, city, adID),1)
    })
      .reduceByKey(_+_)

    val value = result1.updateStateByKey(func)
    value.print()
    /*
    -------------------------------------------
    Time: 1573821660000 ms
    -------------------------------------------
    ((20191115,Changsha,Hunan,5),1)
    ((20191115,Shijiazhuang,Hebei,6),1)
    ((20191115,Zhengzhou,Henan,9),6)
    ((20191115,Nanjing,Jiangsu,2),6)
    ((20191115,Luoyang,Henan,4),1)
    ((20191115,Xiangtan,Hunan,1),3)
    ((20191115,Shijiazhuang,Hebei,9),1)
    ((20191115,Xiangtan,Hunan,0),1)
    ((20191115,Wuhan,Hubei,6),3)
    ((20191115,Suzhou,Jiangsu,5),1)
    ...

    -------------------------------------------
    Time: 1573821665000 ms
    -------------------------------------------
    ((20191115,Jingzhou,Hubei,9),5)
    ((20191115,Shijiazhuang,Hebei,6),1)
    ((20191115,Zhengzhou,Henan,9),6)
    ((20191115,Nanjing,Jiangsu,2),6)
    ((20191115,Luoyang,Henan,4),1)
    ((20191115,Changsha,Hunan,3),1)
    ((20191115,Xiangtan,Hunan,1),3)
    ((20191115,Shijiazhuang,Hebei,9),1)
    ((20191115,Xiangtan,Hunan,0),1)
    ((20191115,Wuhan,Hubei,6),3)
     */

    val resulttop3: DStream[((String,String), List[(String, Int)])] = massage.map(perLine => {
      val arr = perLine.value().split("\\s+")
      val time:String = formatDate(arr(0).toLong)
      val city = arr(1)
      val province = arr(2)
      val userID = arr(3)
      val adID = arr(4)
      ((time,province, city, adID),1)
    })
      .reduceByKey(_+_)
      .updateStateByKey(func)
      .map(perEle => {
        ((perEle._1._1,perEle._1._3),(perEle._1._4,perEle._2))
      })
      .groupByKey()
      .mapValues(perEle => getTop3(perEle.toList.sortBy(_._2).reverse))

    resulttop3.print()
    /*
    -------------------------------------------
    Time: 1573822830000 ms
    -------------------------------------------
    ((20191115,Hunan),List((3,13), (5,8), (3,8), (4,7), (0,7), (6,7), (7,7), (9,7), (0,7)))
    ((20191115,Hubei),List((5,10), (1,10), (5,9), (3,8), (7,8), (8,8), (8,8)))
    ((20191115,Henan),List((6,10), (4,10), (9,9), (2,9), (6,8), (1,8), (8,8), (4,8), (9,8)))
    ((20191115,Jiangsu),List((3,14), (8,13), (1,12)))
    ((20191115,Hebei),List((4,15), (1,14), (4,11)))

    -------------------------------------------
    Time: 1573822835000 ms
    -------------------------------------------
    ((20191115,Hunan),List((3,13), (5,8), (3,8), (4,7), (0,7), (6,7), (9,7), (7,7), (9,7), (0,7)))
    ((20191115,Hubei),List((5,10), (1,10), (8,9), (5,9), (3,8), (7,8), (8,8)))
    ((20191115,Henan),List((6,10), (4,10), (1,9), (9,9), (2,9), (6,8), (8,8), (4,8), (9,8)))
    ((20191115,Jiangsu),List((3,14), (8,14), (1,13), (6,11)))
    ((20191115,Hebei),List((4,15), (1,14), (4,12)))
     */


    ssc.start()
    ssc.awaitTermination()
  }
  def getTop3(container:List[(String, Int)]) = {
    val newContainer = ListBuffer[(String, Int)]()
    val cntConitiner = ListBuffer[Int]()
    cntConitiner ++= container.map(_._2).distinct.take(3)
    //然后在遍历剩余元素,若次数相同就添加进去
    for (index <- 0 until container.size) {
      val newEle = container(index)
      if (cntConitiner.contains(newEle._2)) {
        newContainer.append(newEle)
      }
    }
    newContainer.sortWith(_._2 > _._2).toList
  }
  def func(values: Seq[Int], state: Option[Int]): Some[Int] = {
    //当前批次的相同key的累加和
    val currentKey = values.sum
    //历史批次的相同key的值
    val historyKey = state.getOrElse(0)
    Some(currentKey + historyKey)
  }

  /**
   * 通过时间戳来获取yyyyMMdd格式的日期
   */
  def formatDate(timestamp: Long) = new SimpleDateFormat("yyyyMMdd").format(new Date(timestamp))

}
