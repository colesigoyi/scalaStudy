package cn.qf.sql.day02.demo04_sparkonhive

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
object SparkOnHiveDemo {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .master("local")
      .appName(SparkOnHiveDemo.getClass.getSimpleName)
      .enableHiveSupport()
      .getOrCreate()
    //读取hive表数据
    //db名:mtbap_dm
    //表明:dm_user_visit
    spark.sql(
      """
        |select
        |user_id,
        |latest_pc_visit_date,
        |latest_pc_visit_session,
        |latest_pc_cookies,
        |latest_pc_pv,
        |latest_pc_browser_name
        |from
        |mtbap_dm.dm_user_visit
        |where dt=20190924
        |limit 10
        |""".stripMargin).show
    /*
    +-------+--------------------+-----------------------+
    |user_id|latest_pc_visit_date|latest_pc_visit_session|
    +-------+--------------------+-----------------------+
    |      1| 2019-07-13 06:18:31|              session_5|
    |      2| 2019-05-21 16:03:30|              session_4|
    |      3| 2019-06-26 15:43:03|              session_4|
    |      4| 2019-06-21 14:45:33|              session_1|
    |      5| 2019-01-17 12:50:16|              session_1|
    +-------+--------------------+-----------------------+
     */

    spark.stop()
  }
}
