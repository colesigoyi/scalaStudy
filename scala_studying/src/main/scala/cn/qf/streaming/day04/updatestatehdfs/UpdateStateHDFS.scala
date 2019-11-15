package cn.qf.streaming.day04.updatestatehdfs

import cn.qf.util.SparkUtil
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

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
    val conf: SparkConf = SparkUtil.getSparkConf()
    val sc: StreamingContext = new StreamingContext(conf, Seconds(5))

    sc.checkpoint("hdfs://ns1/spark-streaming/myself/checkpoint/20191114_updateStateHDFS/")

    val tups: DStream[(String, Int)] = sc.textFileStream("hdfs://ns1/spark-streaming/myself/")
      .flatMap(_.split("\\s+"))
      .map((_, 1))
      .reduceByKey(_+_)

    val value = tups.updateStateByKey(func)

    value.print()
    /*
    [root@CentOS-01 /opt/testfile]# hadoop fs -put teststreaming.txt /spark-streaming/myself/20191114_4.txt
    [root@CentOS-01 /opt/testfile]# hadoop fs -put teststreaming.txt /spark-streaming/myself/20191114_5.txt

    -------------------------------------------
    Time: 1573734930000 ms
    -------------------------------------------
    (hehe,9)
    (girl,3)
    (hello,9)
    (boy,3)
    (friend,3)
    (world,6)
    (good,3)

    -------------------------------------------
    Time: 1573734935000 ms
    -------------------------------------------
    (hehe,12)
    (girl,4)
    (hello,12)
    (boy,4)
    (friend,4)
    (world,8)
    (good,4)
     */

    SparkUtil.getStart(sc)
  }
  def func(values: Seq[Int], state: Option[Int]): Some[Int] = {
    //当前批次的相同key的累加和
    val currentKey = values.sum
    //历史批次的相同key的值
    val historyKey = state.getOrElse(0)
    Some(currentKey + historyKey)
  }
}
