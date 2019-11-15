package cn.qf.streaming.day01.test

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年11月11日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */

class SearchFunction(val query: String) extends Serializable {
  //第一个方法是判断输入的字符串是否存在query 存在返回true,不存在返回false
  def isMatch(s: String): Boolean = {
    s.contains(query)
  }
  // 问题:"isMatch"表示"this.isMatch"，因此我们要传递整个"this"
  def getMatchFunctionReference(rdd: RDD[String]): RDD[String] = rdd.filter(x => this.isMatch(x))// 等价于：rdd.filter(isMatch)
  // 问题:"query"表示"this.query"，因此我们要传递整个"this"
  def getMatchesFieldReference(rdd: RDD[String]): RDD[String] = rdd.filter(x => x.contains(this.query))
  // 安全:只把我们需要的字段拿出来放入局部变量中
  def getMatchesNoReference(rdd: RDD[String]): RDD[String] = {
    val _query = this.query
    rdd.filter(x => x.contains(_query))
  }
}
object SearchFunctions {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName(SearchFunctions.getClass.getName).setMaster("local[2]")
    val sc = new SparkContext(conf)
    val rdd = sc.parallelize(List("hello java", "hello scala hello", "hello hello"))
    val sf = new SearchFunction("hello")
    sf.getMatchFunctionReference(rdd).foreach(println)
    sf.getMatchesFieldReference(rdd).foreach(println)
    sf.getMatchesNoReference(rdd).foreach(println)
    sc.stop()
  }
}