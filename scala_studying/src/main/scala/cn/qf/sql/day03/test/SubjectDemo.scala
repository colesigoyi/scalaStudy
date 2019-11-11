package cn.qf.sql.day03.test

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
    val spark = SparkUtil.getSparkSession(
      SubjectDemo.getClass.getName,
      "local[*]")

    val sc = spark.sparkContext

    import spark.implicits._

    sc.textFile("file:////Users/taoxuefeng/Documents/02_StudyCoding/" +
      "09_scala/scala_studying/access/access.txt")
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
        (hostName, moduleName,cnt)
      })

      .toDF("subject","module","cnt")
      .createOrReplaceTempView("tb_subject")

    spark.sqlContext.cacheTable("tb_subject")

    spark.sql(
      """
        |select * from (
            |select
            |subject.subject,
            |module,
            |cnt,
            |row_number() over(partition by subject.subject order by subject.cnt desc) rank
            |from (
                |select
                |*
                |from
                |tb_subject
            |) subject
        |) t where t.rank <=2
        |""".stripMargin).show


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
