package cn.qf.core.day12.demo01_action

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
object CommonActionDemo {
  def main(args: Array[String]): Unit = {
    val spark = SparkUtil.getSparkSession(CommonActionDemo.getClass.getName,"local[*]")
    val sc = spark.sparkContext
    //
    val rdd = sc.parallelize(Seq(3, 4, 5, 1, 0, 9))

    //count():返回RDD的元素个数
    println(s"元素的个数是:${rdd.count()}")
    //first():返回RDD的第一个元素（类似于take(1))
    println(s"元素的第一个元素是是:${rdd.first()}")
    //take(n):返回一个由数据集的前n个元素组成的数组
    println(s"rdd中取出3个元素后,新的集合中的元素是:${rdd.take(3).toBuffer}")
    //
    //top(n):降序排序后,取出元素
    println(s"rdd中的元素降序排列后,取出前三个元素,结果是::${rdd.top(3).toBuffer}")
    //takeOrdered(n, [ordering]):takeOrdered和top类似，只不过以和top相反的顺序返回元素
    println(s"rdd中的元素升序排列后,取出前三个元素,结果是::${rdd.takeOrdered(3).toBuffer}")
    /*
    元素的个数是:6
    元素的第一个元素是是:3
    rdd中取出3个元素后,新的集合中的元素是:ArrayBuffer(3, 4, 5)
    rdd中的元素升序排列后,取出前三个元素,结果是::ArrayBuffer(9, 5, 4)
    rdd中的元素排列后,取出前三个元素,结果是::ArrayBuffer(0, 1, 3)
     */
    spark.stop()
  }
}
