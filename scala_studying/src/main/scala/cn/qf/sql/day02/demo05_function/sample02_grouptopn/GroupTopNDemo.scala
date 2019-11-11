package cn.qf.sql.day02.demo05_function.sample02_grouptopn

import org.apache.spark.sql.types.{DoubleType, IntegerType, StringType, StructField}
import org.apache.spark.sql.{Row, SparkSession, types}

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年11月07日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object GroupTopNDemo {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .master("local[*]")
      .appName(GroupTopNDemo.getClass.getSimpleName)
      .getOrCreate()

    val rdd = spark.sparkContext
      .textFile("file:////Users/taoxuefeng/Documents/02_StudyCoding/" +
      "09_scala/scala_studying/topN")
      .map(perLine => {
        val arr = perLine.split("\\s+")
        Row(arr(0).trim,arr(1).trim.toInt)
      })

    val structType = types.StructType(Seq(
      StructField("className",StringType, false),
      StructField("score",IntegerType, false)
    ))
    spark.createDataFrame(rdd,structType)
      .createOrReplaceTempView("tb_score")

    spark.sql(
      """
         |select
         |*
         |from
         |(
             |select
             |s.classname,
             |s.score,
             |row_number() over(partition by s.className order by s.score desc) rank
             |from (
                 |select
                 |*
                 |from
                 |tb_score
             |) s
         |) sc
         |where rank <= 3
        |""".stripMargin).show()
        /*
        +---------+-----+----+
        |classname|score|rank|
        +---------+-----+----+
        |   class2|   96|   1|
        |   class2|   88|   2|
        |   class2|   88|   2|
        |   class2|   77|   3|
        |   class2|   67|   4|
        |   class1|   98|   1|
        |   class1|   96|   2|
        |   class1|   95|   3|
        |   class1|   87|   4|
        +---------+-----+----+
         */
    spark.stop()
  }
}
