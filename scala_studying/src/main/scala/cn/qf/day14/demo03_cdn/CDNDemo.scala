package cn.qf.day14.demo03_cdn

import cn.qf.util.SparkUtil

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年11月06日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object CDNDemo {
  def main(args: Array[String]): Unit = {
    val spark = SparkUtil.getSparkSession(CDNDemo.getClass.getName,"local[*]")
    val sc = spark.sparkContext



    spark.stop()
  }
}
