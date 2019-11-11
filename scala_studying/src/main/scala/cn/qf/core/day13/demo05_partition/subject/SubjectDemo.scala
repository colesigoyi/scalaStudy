package cn.qf.core.day13.demo05_partition.subject

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
    //不同学科方向,不同模块访问的总次数
    val rdd = sc.textFile("file:////Users/taoxuefeng/Documents/" +
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
      }).cache()

    //构建自定义分区实例
    val myPartitioner = new MyPartitioner(rdd.keys.distinct().collect())
    rdd.partitionBy(myPartitioner)
      .mapPartitions(itr => {
        itr.toList.sortWith(_._2._2 > _._2._2).take(3).toIterator
        })
      .saveAsTextFile("file:////Users/taoxuefeng/Documents/02_StudyCoding/09_scala/scala_studying/access/output")

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
