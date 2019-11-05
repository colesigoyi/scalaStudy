package cn.qf.day13.demo02_cache

import java.net.URL

import cn.qf.day10.demo01_transformation.SparkUtil

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年11月05日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object CacheDemo {
  def main(args: Array[String]): Unit = {
    val spark = SparkUtil.getSparkSession(CacheDemo.getClass.getName,"local[*]")
    val sc = spark.sparkContext

    //1.通过容器模拟从DB中读取学科数据
    val container = Array("http://java.learn.com","http://ui.learn.com/","http://bigdata.learn.com","http://android.learn.com/","http://h5.learn.com/")
    //2.求出不同学科方向不同模块的访问总次数
    val rdd = sc.textFile("file:////Users/taoxuefeng/Documents/" +
      "02_StudyCoding/09_scala/scala_studying/access/access.txt", 1)
      .map(perLine => {
        val arr = perLine.split("\\s+")
        val url = arr(1).trim
        (url, 1)
      }).reduceByKey(_ + _).cache()
    //3.借助1,从2中返回的RDD中筛选出当前学科方向各个模块的点击top3
    for (subject <- container){
      //记录开始时间:
      val startTime = System.currentTimeMillis()
      //下述过滤后的rdd中存储的就是当前所有方向所有模块的点击总次数
      val filterRDD = rdd.filter(_._1.startsWith(subject))
      //action算子触发缓存,下述依靠内存中的缓存进行计算
      val sortedArr = filterRDD.sortBy(_._2, ascending = false, 1)
        .take(3)//行动算子,触发
          .map(perEle => {
            val (subjectname, moduleName) = getHostNameAndModuleByUrl(perEle._1)
            (moduleName, perEle._2)
          })
      println(s"学科方向${subject},最受欢迎的前三个模块信息是${sortedArr.toBuffer}")
      //记录结束时间
      val endTime = System.currentTimeMillis()
      println(s"本次计算共耗时:${endTime - startTime}ms")
      /*
      学科方向http://java.learn.com,最受欢迎的前三个模块信息是ArrayBuffer((teacher,25), (video,23), (javaee,13))
      本次计算共耗时:479ms
      学科方向http://ui.learn.com/,最受欢迎的前三个模块信息是ArrayBuffer((video,37), (course,26), (teacher,23))
      本次计算共耗时:72ms
      学科方向http://bigdata.learn.com,最受欢迎的前三个模块信息是ArrayBuffer((video,47), (teacher,46), (course,25))
      本次计算共耗时:34ms
      学科方向http://android.learn.com/,最受欢迎的前三个模块信息是ArrayBuffer((video,5))
      本次计算共耗时:36ms
      学科方向http://h5.learn.com/,最受欢迎的前三个模块信息是ArrayBuffer((course,47), (teacher,17), (video,11))
      本次计算共耗时:31ms
       */
      }
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
