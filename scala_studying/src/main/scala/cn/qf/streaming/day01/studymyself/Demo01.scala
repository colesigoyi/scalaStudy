package cn.qf.streaming.day01.studymyself


import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.{Seconds, StreamingContext}




/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年11月09日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object Demo01 {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setMaster("local[2]")
      .setAppName(Demo01.getClass.getSimpleName)

    val sc = new StreamingContext(conf, Seconds(5))
    val lines = sc.socketTextStream("192.168.8.101", 6666)
//    val lines:DStream[(String, Int)] = ssc.textFileStream("hdfs://ns1/spark-streaming/myself/")
        .flatMap(_.split("\\s+"))
        .map((_,1))
        .reduceByKey(_ + _)

//    val errorLines = lines.filter(_.contains("error"))
//    errorLines.print()
//    lines.print()
    lines.print()
//    lines.saveAsTextFiles("file:////Users/taoxuefeng/Documents/02_StudyCoding/" +
//      "09_scala/scala_studying/streamingfile/output/",
//      ".csv",
//      classOf[Text],
//      classOf[IntWritable],
//      classOf[TextOutputFormat[Text, IntWritable]]
//    )
    sc.start()
    sc.awaitTermination()

  }
}
