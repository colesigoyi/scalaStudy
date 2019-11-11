package cn.qf.sql.day03.sample05_df2parquet

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
object DF2ParquetDemo {
  def main(args: Array[String]): Unit = {
    val spark = SparkUtil.getSparkSession(
      DF2ParquetDemo.getClass.getName,
      "local[*]"
    )
    spark.read.json("file:////Users/taoxuefeng/Documents/02_StudyCoding/" +
      "09_scala/scala_studying/emp/employees.json")
        .createOrReplaceTempView("tb_emp")

    val df = spark.sql(
      """
        |select
        |id,
        |name,
        |age+1 `age`,
        |salary+500 `salary`,
        |concat('中国',address) `address`
        |from
        |tb_emp
        |""".stripMargin)

    //读取资源,可以指定源的格式,如果不指定,默认就是Parquet
    spark.read.format("parquet").load("")
    //落地结果时,目的资源也是parquet,也可以format指定目的资源的格式
    df.write.format("json").save()

    //本地测试
    df.show()
    /*
    +---+-----+---+------+-------+
    | id| name|age|salary|address|
    +---+-----+---+------+-------+
    |  1|杰克逊| 19| 18500|中国北京|
    |  2|Andy | 20| 28500|中国香港|
    |  3|marry| 17| 19500|中国香港|
    +---+-----+---+------+-------+
     */
    df.write.mode(SaveMode.Overwrite).parquet("file:////Users/taoxuefeng/Documents/02_StudyCoding/" +
      "09_scala/scala_studying/emp/output")
    //.part-00000-e2c22847-564d-4790-94d2-cea30e7e60c1-c000.snappy.parquet.crc
    //snappy:压缩算法
    spark.stop()
  }
}
