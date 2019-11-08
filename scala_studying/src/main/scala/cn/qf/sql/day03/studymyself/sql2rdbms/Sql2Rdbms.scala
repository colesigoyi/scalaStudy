package cn.qf.sql.day03.studymyself.sql2rdbms

import java.util.Properties

import org.apache.spark.sql.SparkSession

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年11月07日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object Sql2Rdbms {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .master("local")
      .appName(Sql2Rdbms.getClass.getSimpleName)
      .getOrCreate()

    val propertied = new Properties
    propertied.put("driver","com.mysql.jdbc.Driver")

    //spark.read.jdbc()
  }
}
