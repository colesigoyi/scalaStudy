package cn.qf.streaming.day04.transformationfunction.windowoperations

import cn.qf.util.SparkUtil
import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Durations, Seconds, StreamingContext}

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年11月14日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object WindowsOperationsDemo {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = SparkUtil.getSparkConf
    val sc = new StreamingContext(conf, Seconds(2))

    val lines: ReceiverInputDStream[String] = sc.socketTextStream("NODE01",6666)
    val tups: DStream[(String, Int)] = lines.flatMap(_.split("\\s+")).map((_, 1))
    //窗口操作
    tups.reduceByKeyAndWindow(
      (x:Int,y:Int) => x + y,
      Durations.seconds(10),
      Durations.seconds(10))
        .print()

    SparkUtil.getStart(sc)
  }
}
