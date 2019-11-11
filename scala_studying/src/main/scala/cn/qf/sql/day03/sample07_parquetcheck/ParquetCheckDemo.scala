package cn.qf.sql.day03.sample07_parquetcheck

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
object ParquetCheckDemo {
  def main(args: Array[String]): Unit = {
    val spark = SparkUtil.getSparkSession(
      ParquetCheckDemo.getClass.getName,
      "local[*]")

    spark.read
        .load("file:////Users/taoxuefeng/Documents/02_StudyCoding/09_scala/scala_studying/emp/output")
        .createOrReplaceTempView("tb_emp")

    val df = spark.sql(
      """
        |select
        |*
        |from tb_emp
        |""".stripMargin)

    df.show()
    //读取parquet类型的文件成功,表示读取以parquet为默认格式
    /*
    +---+-----+---+------+-------+
    | id| name|age|salary|address|
    +---+-----+---+------+-------+
    |  1|杰克逊| 19| 18500|   中国北京|
    |  2| Andy| 20| 28500|   中国香港|
    |  3|marry| 17| 19500|   中国香港|
    +---+-----+---+------+-------+
     */
    df.write
        .mode(SaveMode.Overwrite)
        .save("file:////Users/taoxuefeng/Documents/02_StudyCoding/09_scala/scala_studying/emp/output3")
    //保存的文件时以snappy.parquet保存,所以保存的默认格式是parquet

    spark.stop()
  }
}
