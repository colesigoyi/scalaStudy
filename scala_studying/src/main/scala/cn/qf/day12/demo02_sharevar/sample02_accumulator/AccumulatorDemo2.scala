package cn.qf.day12.demo02_sharevar.sample02_accumulator

import cn.qf.util.SparkUtil

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年11月04日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object AccumulatorDemo2 {
  def main(args: Array[String]): Unit = {
    val spark = SparkUtil.getSparkSession(AccumulatorDemo2.getClass.getName,"local[*]")
    val sc = spark.sparkContext
    //需求:使用自定义累加器,将RDD中所有元素合并起来,每个元素前面加上☆,后面加上😁
    val rdd = sc.parallelize(List("hello","my","friend"))
    val myAccumulator = new MySelfAccumulator
    sc.register(myAccumulator)
    rdd.foreach(perEle => {
      myAccumulator.add("☆" + perEle + "😁")
    })

    println(s"使用自定义累加器合并RDD中所有元素后,结果是:${myAccumulator.value}")

    //自定义累加器用法(增强)
    //统计页面用户访问次数
    //1_2=20|2_3=20|3_4=30...
    spark.stop()
  }
}
