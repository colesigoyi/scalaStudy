package cn.qf.streaming.day04.queue

import cn.qf.util.SparkUtil
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.{Seconds, StreamingContext}

import scala.collection.mutable

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年11月14日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object RDDQueueStreamDemo {
  def main(args: Array[String]): Unit = {
    val conf = SparkUtil.getSparkConf()
    val sc = new StreamingContext(conf, Seconds(20))

    //创建RDD队列
    val rddQueue = new mutable.Queue[RDD[Int]]()
    /**
     * 读取队列的数据
     * 参数oneAtATime:是否在该间隔内从队列中使用一个rdd,true为是,false为不是
     */
    val logs = sc.queueStream(rddQueue, oneAtATime = false)
    //分析
    val aggred = logs.map((_, 1)).reduceByKey(_ + _)

    aggred.print()
    /*
    -------------------------------------------
    Time: 1573733820000 ms
    -------------------------------------------
    (34,10)
    (52,10)
    (96,10)
    (4,10)
    (16,10)
    (82,10)
    (66,10)
    (28,10)
    (54,10)
    (80,10)
    ...
     */

    sc.start()

    for (i <- 1 to 10) {
      rddQueue += sc.sparkContext.makeRDD(1 to 100)
      Thread.sleep(1000)
    }
    sc.awaitTermination()
  }
}
