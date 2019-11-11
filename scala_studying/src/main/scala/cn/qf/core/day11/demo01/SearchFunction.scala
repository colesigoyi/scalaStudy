package cn.qf.core.day11.demo01

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年11月02日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
/**
 * 定义了一个类,这里的有一个query的参数并进行序列化
 * @param query
 */
class SearchFunction(val query: String) extends java.io.Serializable{
  /**
   * 第一个方法是判断输入的字符串是否存在query,存在返回true,不存在返回false
   * @param s
   * @return
   */
  def isMath(s: String) = {
    s.contains(query)
  }

  /**
   * 问题:isMatch表示this.isMatch,因此我们要传递整个this
   * @param rdd
   * @return
   */
  def getMatchesFunctionReference(rdd: org.apache.spark.rdd.RDD[String]):org.apache.spark.rdd.RDD[String] = {
    rdd.filter(isMath)
  }

  /**
   * 问题:query表示this.query,因此我们要传递整个this
   * @param rdd
   * @return
   */
  def getMatchesFileReference(rdd:org.apache.spark.rdd.RDD[String]) = {
    rdd.filter(x => x.contains(query))
  }

  /**
   * 安全:只把我们需要的字段拿出来放入局部变量中
   * @param rdd
   * @return
   */
  def getMachesNoRefernece(rdd:org.apache.spark.rdd.RDD[String]) = {
    val query_ = this.query
    rdd.filter(x => x.contains(query_))
  }
}
