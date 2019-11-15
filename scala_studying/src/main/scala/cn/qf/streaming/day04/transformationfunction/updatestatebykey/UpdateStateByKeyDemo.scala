package cn.qf.streaming.day04.transformationfunction.updatestatebykey

import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.{HashPartitioner, SparkConf, SparkContext}
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年11月14日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object UpdateStateByKeyDemo {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setMaster("local[2]")
      .setAppName(UpdateStateByKeyDemo.getClass.getSimpleName)
    val context = new SparkContext(conf)
    val sc = new StreamingContext(context, Seconds(5))

    sc.checkpoint("hdfs://ns1/spark-streaming/myself/checkpoint/")

    //获取数据
    val lines = sc.socketTextStream("NODE01", 6666)
    //处理数据
    val tups: DStream[(String, Int)] = lines.flatMap(_.split("\\s+")).map((_, 1))
    //(Iterator[(K, Seq[V], Option[S])]) => Iterator[(K, S)]
//    val value = tups.updateStateByKey(
//      func1, new HashPartitioner(sc.sparkContext.defaultParallelism),rememberPartitioner = true)

    //调用updateStateByKey另一个重载
    val value = tups.updateStateByKey(func2)

    value.print()

    sc.start()
    sc.awaitTermination()
  }

  /**
   * (Iterator[(K, Seq[V], Option[S])]) => Iterator[(K, S)]
   * 该函数传入的参数是一个迭代对象,数据类型都是泛型
   * 参数1:指key的类型,当前需求是指一个个单词的类型
   * 参数2:当前批次中相同key对应的value,Seq(1,1,1,1,1....)
   * 参数3:指历史批次中,相同key对应的value,利用Option封装,可以有值也可以没有值,比如:Option[5]
   * 注意:为什么用Option封装历史批次,因为不确定历史批次是否有值
   * @return
   */
  def func1 = (it: Iterator[(String, Seq[Int], Option[Int])]) => {
    it.map(tup => {
      val key = tup._1
      //当前批次的相同key的累加和
      val currentKey = tup._2.sum
      //历史批次的相同key的值
      val historyKey = tup._3.getOrElse(0)
      (key, currentKey + historyKey)
    })
  }
  /**
   * (Seq[V], Option[S]) => Option[s]
   * 参数1:当前批次中相同key对应的value,Seq(1,1,1,1,1....)
   * 参数2:指历史批次中,相同key对应的value,利用Option封装,可以有值也可以没有值,比如:Option[5]
   * 注意:为什么用Option封装历史批次,因为不确定历史批次是否有值
   * 因为返回要求是Option,所以返回可以封装到some里
   * @return
   */
  def func2 = (values: Seq[Int], state: Option[Int]) => {
    //当前批次的相同key的累加和
    val currentKey = values.sum
    //历史批次的相同key的值
    val historyKey = state.getOrElse(0)
    Some(currentKey + historyKey)
  }
}
