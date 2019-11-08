package cn.qf.sql.day02.demo05_function.sample01_commonfunction

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
object CommonFunctionDemo {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .master("local[*]")
      .appName(CommonFunctionDemo.getClass.getSimpleName)
      .getOrCreate()

    //需求:求出公司员工中最高工资,最低工资,平均工资,员工总人数以及工资总数
    //读取json文件到临时表,并映射成虚拟表tb_emp
    spark.read.json("file:////Users/taoxuefeng/Documents/02_StudyCoding/" +
      "09_scala/scala_studying/emp/employees.json")
        .createOrReplaceTempView("tb_emp")

    //使用sql分析
    spark.sql(
      """
        |select
        |max(salary) `最高薪水`,
        |min(salary) `最低薪水`,
        |avg(salary) `平均薪水`,
        |count(id) `员工人数`,
        |sum(salary) `薪水总额`
        |from tb_emp
        |""".stripMargin).show()
    /*
    +-----+-----+------------------+----+-----+
    | 最高薪水| 最低薪水| 平均薪水|员工人数| 薪水总额|
    +-----+-----+------------------+----+-----+
    |28000|18000|21666.666666666668|   3|65000|
    +-----+-----+------------------+----+-----+
     */

    spark.stop()
  }
}
