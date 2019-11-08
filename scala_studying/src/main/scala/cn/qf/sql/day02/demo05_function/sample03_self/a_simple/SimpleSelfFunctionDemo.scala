package cn.qf.sql.day02.demo05_function.sample03_self.a_simple

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
object SimpleSelfFunctionDemo {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .master("local[*]")
      .appName(SimpleSelfFunctionDemo.getClass.getSimpleName)
      .getOrCreate()
    spark.read.json("file:////Users/taoxuefeng/Documents/02_StudyCoding/" +
      "09_scala/scala_studying/emp/employees.json")
      .createOrReplaceTempView("tb_emp")
    //函数体简单:
    val getLen = spark.udf.register("getLen",(x:String) => x.length)
    //函数体复杂书写方式:分离出方法体,封装方法
    val getLen2 = spark.udf.register("getLen2",(x:String) => getAnyLength(x))

    spark.sql(
      """
        |select
        |name `姓名`,
        |getLen2(name) `长度`
        |from tb_emp
        |""".stripMargin).show()
    /*
    +-----+----+
    | 姓名| 长度|
    +-----+----+
    |杰克逊|   3|
    |Andy |   4|
    |marry|   5|
    +-----+----+
     */
    spark.stop()
  }
  def getAnyLength(name:String) = {
    name.length
  }
}
