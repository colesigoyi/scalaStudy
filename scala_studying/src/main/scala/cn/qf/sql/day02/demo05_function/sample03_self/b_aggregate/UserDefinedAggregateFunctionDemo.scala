package cn.qf.sql.day02.demo05_function.sample03_self.b_aggregate

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
object UserDefinedAggregateFunctionDemo {
  def main(args: Array[String]): Unit = {
    //需求:都区员工信息,求出所有员工工资
    val spark = SparkSession.builder()
      .master("local[*]")
      .appName(UserDefinedAggregateFunctionDemo.getClass.getSimpleName)
      .getOrCreate()
    spark.read.json("file:////Users/taoxuefeng/Documents/02_StudyCoding/" +
      "09_scala/scala_studying/emp/employees.json")
      .createOrReplaceTempView("tb_emp")

    val getLen2 = spark.udf.register("myselfAvg",new MyAvg)

    spark.sql(
      """
        |select
        |count(id) `员工总数`,
        |avg(salary) `内置->平均薪水`,
        |myselfAvg(salary) `自定义->平均薪水`
        |from tb_emp
        |""".stripMargin).show()
    /*
    +-------+------------------+------------------+
    |员工总数|   内置->平均薪水  |  自定义->平均薪水  |
    +-------+------------------+------------------+
    |   3   |21666.666666666668|21666.666666666668|
    +-------+------------------+------------------+
     */
  }
}
