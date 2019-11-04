package cn.qf.day12.demo01_action

import cn.qf.day10.demo01_transformation.SparkUtil

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年11月04日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object CommonActionDemo3 {
  def main(args: Array[String]): Unit = {
    val spark = SparkUtil.getSparkSession(CommonActionDemo.getClass.getName,"local[*]")
    val sc = spark.sparkContext
    //
    val rdd = sc.parallelize(Seq(("jack", 5),("marry", 6),("jack", 5),("tom", 3)))
    //countByKey():针对(K,V)类型的RDD，返回一个(K,Int)的map，表示每一个key对应的元素个数。
    println(s"不同key对应的元素的个数是:${rdd.countByKey()}")
    //不同key对应的元素的个数是:Map(tom -> 1, marry -> 1, jack -> 2)

    //countByValue:返回的是map集合Map[(String,Int),Int],将源集合中相同的元素个数进行累加
    println(s"不同key对应的元素的个数是:${rdd.countByValue()}")
    //不同key对应的元素的个数是:Map((jack,5) -> 2, (marry,6) -> 1, (tom,3) -> 1)
    spark.stop()
  }
}
