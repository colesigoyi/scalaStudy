package cn.qf.core.day13.demo01_subject

import java.net.URL

import cn.qf.util.SparkUtil


/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年11月05日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object SubjectDemo {
  def main(args: Array[String]): Unit = {
    val spark = SparkUtil.getSparkSession(SubjectDemo.getClass.getName,"local[*]")
    val sc = spark.sparkContext

    /*
    	-> textFile:加载到内存
      -> RDD:每个元素就是一条日志信息
      -> map:将每个元素变形为(url, 1)
      -> reduceByKey:不同学科方向不同模块访问的次数
      -> map:将每个元素变成(学科名,(模块名,访问总次数))
      -> groupByKey:根据学科名进行分组,将相同学科方向的置于一组
                    RDD中每个元素:key=学科名,value=集合
      -> mapValues:求出每个学科方向最受欢迎的前三个模块
     */
    sc.textFile("file:////Users/taoxuefeng/Documents/" +
      "02_StudyCoding/09_scala/scala_studying/access/access.txt", 1)
      .map(perLine => {
        val arr = perLine.split("\\s+")
        val url = arr(1).trim
        (url, 1)
      }).reduceByKey(_ + _)
      .cache()
      .map(tuple => {
        val url = tuple._1
        val cnt = tuple._2
        val (hostName,moduleName) = getHostNameAndModuleByUrl(url)
        (hostName, (moduleName,cnt))
      }).groupByKey()
        //.foreach(println)
        /*
        (android.learn.com,CompactBuffer((video,5)))
        (ui.learn.com,CompactBuffer((course,26), (teacher,23), (video,37)))
        (java.learn.com,CompactBuffer((teacher,25), (javaee,13), (video,23)))
        (h5.learn.com,CompactBuffer((course,47), (video,11), (teacher,17)))
        (bigdata.learn.com,CompactBuffer((teacher,46), (course,25), (video,47)))
         */
        .mapValues(_.toList.sortWith(_._2 > _._2).take(2))
        .foreach(println)
        /*
        (android.learn.com,List((video,5)))
        (ui.learn.com,List((video,37), (course,26), (teacher,23)))
        (java.learn.com,List((teacher,25), (video,23), (javaee,13)))
        (h5.learn.com,List((course,47), (teacher,17), (video,11)))
        (bigdata.learn.com,List((video,47), (teacher,46), (course,25)))
         */
    spark.stop()
  }
  /**
   * 根据url获得主机名
   * @param url
   */
  def getHostNameAndModuleByUrl(url:String) = {
    val hostName = new URL(url).getHost
    //截字符串
    val beginIndex = url.lastIndexOf('/')+1
    val endIndex = url.lastIndexOf('.')
    val moduleName = url.substring(beginIndex, endIndex)
    (hostName, moduleName)
  }
}
