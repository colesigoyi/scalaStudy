package cn.qf.streaming.day05.test

import java.text.SimpleDateFormat
import java.util.Date

import cn.qf.util.SparkUtil
import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.{Seconds, StreamingContext}

import scala.collection.mutable.ListBuffer

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年11月14日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object UpdateStateHDFS {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf =  new SparkConf().setMaster("local[2]").setAppName(this.getClass.getSimpleName)
    val ssc: StreamingContext = new StreamingContext(conf, Seconds(5))

    ssc.checkpoint("file:////Users/taoxuefeng/Documents/02_StudyCoding/" +
      "09_scala/scala_studying/checkpoint/test1115")

    /**
     * 广告点击流量实时统计
     * 1、使用updateStateByKey或其他操作，实时计算每天各省各城市各广告的点击量，并更新到数据库
     * 2、使用transform结合SparkSQL统计每天各省份top3热门广告（开窗函数）
     */
    val tups1: DStream[(String, Int)] =
      ssc.textFileStream("hdfs://ns1/spark-streaming/myself/")
        .map(perLine => {
          val arr = perLine.split("\\s+")
          val time = formatDate(arr(0).toLong)
          val city = arr(1)
          val province = arr(2)
          val userID = arr(3)
          val adID = arr(4)
          ((time,province, city, adID),1)
        }).reduceByKey(_+_)
        .map(perEle => {
          ("日期:" + perEle._1._1+ " 省份:" +perEle._1._2+ " 城市:" +perEle._1._3+ "广告ID" +perEle._1._4,perEle._2)
        })

    val value = tups1.updateStateByKey(func)
    value.print()
    /*
      ...
  (日期:20190703 省份:Luoyang 城市:Henan广告ID6,1104)
  (日期:20190703 省份:Zhangjiakou 城市:Hebei广告ID7,1089)
  (日期:20190703 省份:Nanjing 城市:Jiangsu广告ID2,1053)
  (日期:20190703 省份:Luoyang 城市:Henan广告ID0,1081)
  (日期:20190703 省份:Jingzhou 城市:Hubei广告ID8,1150)
  (日期:20190703 省份:Xiangtan 城市:Hunan广告ID5,1082)
  (日期:20190703 省份:Zhangjiakou 城市:Hebei广告ID2,1086)
  (日期:20190703 省份:Shijiazhuang 城市:Hebei广告ID0,1104)
      ...
     */

    val tupsTop3: DStream[((String,String),List[(String, Int)])] =
      ssc.textFileStream("hdfs://ns1/spark-streaming/myself/")
        .map(perLine => {
          val arr = perLine.split("\\t")
          val time = formatDate(arr(0).toLong)
          val city = arr(1)
          val province = arr(2)
          val userID = arr(3)
          val adID = arr(4)
          ((time,province, city, adID),1)
        }).reduceByKey(_+_)
        .updateStateByKey(func)
        .map(perEle => {

          ((perEle._1._1,perEle._1._3),(perEle._1._4,perEle._2))
        })
        .groupByKey()
        .mapValues(perEle => getTop3(perEle.toList.sortBy(_._2).reverse))

    tupsTop3.print()
    /*
      ...
    ((20190703,Hunan),List((5,1191), (1,1134), (0,1128)))
    ((20190703,Jiangsu),List((7,1143), (3,1139), (6,1125)))
    ((20190703,Hubei),List((6,1153), (8,1150), (8,1139)))
    ((20190703,Hebei),List((7,1161), (3,1143), (9,1132)))
    ((20190703,Henan),List((6,1183), (0,1156), (4,1126)))
       ...
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
