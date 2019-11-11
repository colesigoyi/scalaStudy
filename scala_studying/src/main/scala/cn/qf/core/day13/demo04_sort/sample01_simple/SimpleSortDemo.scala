package cn.qf.core.day13.demo04_sort.sample01_simple

import cn.qf.util.SparkUtil

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年11月05日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object SimpleSortDemo {
  def main(args: Array[String]): Unit = {
    val spark = SparkUtil.getSparkSession(SimpleSortDemo.getClass.getName,"local[*]")
    val sc = spark.sparkContext

    sc.parallelize(Seq(Girl("marry",80.8),Girl("lucycat",98.9),Girl("lilydo",79.9)))
        .sortBy(_.faceValue, false, 1)
        .foreach(println)
        /*
        Girl(lucycat,98.9)
        Girl(marry,80.8)
        Girl(lilydo,79.9)
         */

    spark.stop()
  }
}
