package cn.qf.sql.day02.homework

import cn.qf.util.getBirth
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
object HomeWork {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .master("local")
      .appName(HomeWork.getClass.getSimpleName)
      .getOrCreate()
    val sc = spark.sparkContext

    import spark.implicits._

    sc.textFile("file:////Users/taoxuefeng/Documents/02_StudyCoding/" +
      "09_scala/scala_studying/homeworktest/birthday.txt")
      .map(perLine => {
        val arr = perLine.split("\\s+")
        val id = arr(0).trim
        val name = arr(1).trim
        val birthday = arr(2).trim
        val birth = arr(2).split("-")
        val year = birth(0).trim.toInt
        val month = birth(1).trim.toInt
        val day = birth(2).trim.toInt
        (id,name,birthday,year,month,day)
      })
      //.foreach(println)
      /*
      (1,jack,1991,10,21)
      (2,marry,1993,11,21)
      (3,LucyCat,1992,8,20)
      (4,Angle,1998,12,13)
      (5,lily,1997,11,27)
      (6,tom,1989,4,11)
      (7,lion,1990,1,8)
       */
        .toDF("id","name","birthday","year","month","day")
        .createOrReplaceTempView("tb_bir")

    spark.sqlContext.cacheTable("tb_bir")


    val getYear = spark.udf.register("getYear",(x:Int) => getBirth.getYear(x))
    val getConstellation = spark.udf.register("getConstellation",(x:Int,y:Int) => getBirth.getConstellation(x, y))

    spark.sql(
      """
        |select
        |id `编号`,
        |name `名字`,
        |birthday `生日`,
        |getYear(year) `生肖`,
        |getConstellation(month,day) `星座`
        |from
        |tb_bir
        |""".stripMargin).show
      /**
      +---+-------+----------+-----+-----+
      |编号|  名字|     生日   | 生肖| 星座|
      +---+-------+----------+-----+-----+
      |  1|   jack|1991-10-21|  羊 |天秤座|
      |  2|  marry|1993-11-21|  鸡 |天蝎座|
      |  3|Lucycat|1992-08-20|  猴 |狮子座|
      |  4|  Angle|1998-12-13|  虎 |射手座|
      |  5|   lily|1997-11-27|  牛 |射手座|
      |  6|    tom|1989-04-11|  蛇 |白羊座|
      |  7|   lion|1990-01-08|  马 |摩羯座|
      +---+-------+----------+-----+-----+
       */

    spark.stop()
  }
}
