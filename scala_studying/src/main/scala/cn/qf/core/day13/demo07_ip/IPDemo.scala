package cn.qf.core.day13.demo07_ip

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
object IPDemo {
  def main(args: Array[String]): Unit = {
    val spark = SparkUtil.getSparkSession(IPDemo.getClass.getName,"local[*]")
    val sc = spark.sparkContext


    spark.stop()
  }
}
