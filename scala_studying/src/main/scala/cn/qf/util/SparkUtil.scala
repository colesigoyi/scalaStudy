package cn.qf.util

import org.apache.spark.SparkConf
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
  def getSparkConf: SparkConf = {
    new SparkConf()
      .setMaster("local[2]")
      .setAppName(this.getClass.getSimpleName)
  }
  def getSparkSession: SparkSession = {
    SparkSession.builder()
      .appName(this.getClass.getName)
      .master("local[2]")
      .getOrCreate()
  }
  def getSparkSession(appName:String, master:String): SparkSession = {
    SparkSession.builder()
      .appName(appName)
      .master(master)
      .getOrCreate()
  }
  def getSparkSession(appName:String, master:String,isHive:Boolean): SparkSession = {
    if(isHive){
      SparkSession.builder()
        .appName(appName)
        .master(master)
        .enableHiveSupport()
        .getOrCreate()
    }
    else {
      getSparkSession(appName,master)
    }
  }
}
