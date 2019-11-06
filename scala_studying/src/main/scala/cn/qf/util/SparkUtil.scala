package cn.qf.util

import org.apache.spark.sql.SparkSession

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年11月01日
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object SparkUtil {
  def getSparkSession(appName:String, master:String): SparkSession = {
    SparkSession.builder()
      .appName(appName)
      .master(master)
      .getOrCreate()
  }
}
