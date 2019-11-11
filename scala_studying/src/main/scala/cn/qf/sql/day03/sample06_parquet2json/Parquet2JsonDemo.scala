package cn.qf.sql.day03.sample06_parquet2json

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
object Parquet2JsonDemo {
  def main(args: Array[String]): Unit = {
    val spark = SparkUtil.getSparkSession(
      Parquet2JsonDemo.getClass.getName,
      "local[*]")

    spark.read.parquet("file:////Users/taoxuefeng/Documents/02_StudyCoding/" +
      "09_scala/scala_studying/emp/output")
        .createOrReplaceTempView("tb_emp")

    val df = spark.sql(
      """
        |select
        |id,
        |name,
        |age+1 `age`,
        |salary+1000 `salary`,
        |concat('亚洲',address) `address`
        |from
        |tb_emp
        |""".stripMargin)

    df.show()
    /*
    +---+-----+---+------+------------+
    | id| name|age|salary|  address   |
    +---+-----+---+------+------------+
    |  1|杰克逊| 20| 19500| 亚洲中国北京|
    |  2| Andy| 21| 29500| 亚洲中国香港|
    |  3|marry| 18| 20500| 亚洲中国香港|
    +---+-----+---+------+------------+
     */

    df.write
      .mode(SaveMode.Overwrite)
      .format("json")
      .save("file:////Users/taoxuefeng/Documents/02_StudyCoding/09_scala/scala_studying/emp/output2")

    spark.stop()
  }
}
