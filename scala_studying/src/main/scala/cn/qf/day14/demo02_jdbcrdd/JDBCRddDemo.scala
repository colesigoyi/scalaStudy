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
      sc,
      () => DBCPUtil.getConnection,
      "select * from tb_per_hour_province_top3 where id >= ? and id <= ?",
      2,
      14,
      2,
      rs => {
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
