package cn.qf.sql.day03.sample04_df2rdbms

import java.util.Properties

import cn.qf.util.SparkUtil
import org.apache.spark.sql.SaveMode

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年11月08日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object DF2RDBMSDemo {
  def main(args: Array[String]): Unit = {
    val spark = SparkUtil.getSparkSession(
      DF2RDBMSDemo.getClass.getName,
      "local[*]"
    )
    val properties = new Properties()
    properties.put("driver", "com.mysql.jdbc.Driver")
    properties.put("user", "root")
    properties.put("password", "88888888")
    //读取db之study中的表
    spark.read.jdbc("jdbc:mysql://192.168.8.103:3306/dy?useUnicode=true&characterEncoding=utf-8",
      "tb_ad_result", properties)
        .createOrReplaceTempView("tb_ad")
    spark.sqlContext.cacheTable("tb_ad")

    //计算(Saprk集群,或是hadoop集群)并落地
    spark.sql(
      """
        |select
        |concat('编号->',id) id,
        |concat('省份名->',province) province,
        |concat('时间->',hour) hour,
        |concat('广告编号->',adId) adId,
        |concat('点击次数->',cnt) cnt
        |from tb_ad
        |""".stripMargin)
        .write
        .mode(SaveMode.Append)
        .jdbc("jdbc:mysql://192.168.8.103:3306/dy?useUnicode=true&characterEncoding=utf-8",
          "tb_new_ad_result", properties)


    //	stop
    spark.stop()
  }
}
