package cn.qf.day09.demo03_transformation

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession
/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月31日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object RDDTransformationDemo {
  def main(args: Array[String]): Unit = {
    //准备SparkContext的实例(Spark >= 2.0 以后,推荐使用SparkSession
    val spark:SparkSession = SparkSession
      .builder()
      .master("local[*]")
      .appName(RDDTransformationDemo.getClass.getName)
      .getOrCreate()

    val sc:SparkContext = spark.sparkContext

    //常用的Transformation逐个演示
    val rdd:RDD[Int] = sc.parallelize(Seq(1,2,3,4))

    //转换算子的惰性演示
//    rdd.filter(perEle => {
//      println(s"-----执行:当前线程名是:${Thread.currentThread().getName}-----")
//      perEle % 2 == 0
//    }).foreach(println)

    //map:参数指定的函数依次作用域rdd中每个元素,将函数执行后的结果置于全新的rdd中存储起来
    val resultRDD = rdd.map(_ * 2)
    println(s"每个元素乘以2后新的RDD中的元素信息是:${resultRDD.collect().toBuffer}")
    println("------------------------------")

    //filter:将旧的rdd内容中的元素根据指定的规则过滤筛选后置于新的rdd中
    println(s"筛选奇数后新的RDD中的元素信息是:${rdd.filter(_ % 2 != 0).collect().toBuffer}")
    println("------------------------------")

    //flatMap:map + flattern:rdd中待分析的元素是集合类型
    val rddTmp:RDD[String] = sc.parallelize(Seq("张无忌 18", "张三丰 89"))
    rddTmp.flatMap(_.split("\\s+")).foreach(println)
    println("------------------------------")
    //sample随机采样
    sc.parallelize(1 to 10)
      .sample(withReplacement = false, 0.5)//false表示不放回,新的rdd中有新的元素就不放回
      .collect()
      .toBuffer
        .foreach(println)
    //资源释放
    spark.stop()
  }
}
