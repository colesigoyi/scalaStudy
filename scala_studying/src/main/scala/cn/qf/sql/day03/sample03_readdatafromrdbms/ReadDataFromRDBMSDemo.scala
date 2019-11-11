package cn.qf.sql.day03.sample03_readdatafromrdbms

import java.util.Properties
import cn.qf.util.SparkUtil


/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年11月08日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object ReadDataFromRDBMSDemo {
  def main(args: Array[String]): Unit = {
    val spark = SparkUtil.getSparkSession(
      ReadDataFromRDBMSDemo.getClass.getName,
      "local[*]"
    )
    val properties = new Properties()
    properties.put("driver", "com.mysql.jdbc.Driver")
    properties.put("user", "root")
    properties.put("password", "88888888")
    //读取db之study中的表
    spark.read.jdbc("jdbc:mysql://192.168.8.103:3306/dy?useUnicode=true&characterEncoding=utf-8",
      "tb_ad_result", properties)
      .show()
    /*
    +---+--------+----+----+---+
    | id|province|hour|adId|cnt|
    +---+--------+----+----+---+
    |  1|     浙江省|   4|   5|  2|
    |  2|     广东省|  10|   2|  5|
    |  3|     广东省|   9|   4|  1|
    |  4|     浙江省|   4|   7|  2|
    |  5|     广东省|   9|   2|  1|
    |  6|     浙江省|  15|   7|  2|
    |  7|     广东省|  14|   4|  4|
    |  8|     浙江省|  15|   8|  1|
    |  9|     广东省|  14|   3|  1|
    | 10|     浙江省|  15|   6|  1|
    | 11|     广东省|  22|   3|  2|
    | 12|     浙江省|   3|   5|  2|
    | 13|     浙江省|   1|   5|  2|
    | 14|     广东省|  15|   5|  1|
    | 15|     广东省|  15|   2|  1|
    +---+--------+----+----+---+
     */

    //	stop
    spark.stop()
  }
}
