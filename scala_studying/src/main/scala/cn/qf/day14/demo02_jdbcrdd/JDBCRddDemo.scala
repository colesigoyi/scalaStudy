package cn.qf.day14.demo02_jdbcrdd

import cn.qf.util.{DBCPUtil, SparkUtil}
import org.apache.spark.rdd.JdbcRDD

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年11月06日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object JDBCRddDemo {
  def main(args: Array[String]): Unit = {
    val spark = SparkUtil.getSparkSession(JDBCRddDemo.getClass.getName,"local[*]")
    val sc = spark.sparkContext

    val rdd = new JdbcRDD(
      sc,//Spark上下文实例
      () => DBCPUtil.getConnection,//db连接的实例
      "select * from tb_per_hour_province_top3 where id >= ? and id <= ?",
      2,
      14,
      2,//分区数
      rs => { //函数类型,函数的参数类型是resultSet,封装了查询后的结果
        val id = rs.getInt("id")
        val province = rs.getString("province")
        val time = rs.getString("hour")
        val adId = rs.getInt("adId")
        val cnt = rs.getInt("cnt")
        (id, province, time, adId, cnt)
      }
    )
    rdd.foreach(println)

    spark.stop()
  }
}
