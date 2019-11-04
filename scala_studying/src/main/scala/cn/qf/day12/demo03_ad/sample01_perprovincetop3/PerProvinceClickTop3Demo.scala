package cn.qf.day12.demo03_ad.sample01_perprovincetop3

import cn.qf.day10.demo01_transformation.SparkUtil

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年11月04日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object PerProvinceClickTop3Demo {
  def main(args: Array[String]): Unit = {
    val spark = SparkUtil.getSparkSession(PerProvinceClickTop3Demo.getClass.getName,
      "local[*]")
    val sc = spark.sparkContext

    //实现
    /*
    ->textFile
    ->RDD,每个元素表示:用户点击某则广告在后台留下的日志记录
    ->map变形:key = (省份,广告) value = 1:RDD中每个元素形如:map((省份,广告),1)
    ->reduceByKey,统计的是每个省份不同广告的点击总次数
    ->groupBy:根据省份信息进行分组,分组后的结果是:省份中所有的不同的广告的点击总次数
    ->mapvalues:求出不同省份最受欢迎的top3广告ID
    ->输出
     */
    sc.textFile("file:////Users/taoxuefeng/Documents/02_StudyCoding/09_scala/scala_studying/ad/ad.txt").map(perEle => {
      val arr = perEle.split("\\s+")
      val province = arr(1).trim
      val adId = arr(4).trim
      ((province, adId), 1)
    }).reduceByKey(_ + _)
        //.foreach(println)
        /*
        ((浙江省,10),1)
        ((广东省,2),7)
        ((浙江省,5),6)
        ((广东省,4),5)
        ((浙江省,8),1)
        ((广东省,3),3)
        ((浙江省,7),4)
        ((广东省,5),1)
        ((浙江省,6),1)
         */
        .map(perEle => {
          (perEle._1._1, perEle._1._2, perEle._2)
        }).groupBy(_._1)
        //.foreach(println)
        /*
        (广东省,CompactBuffer((广东省,4,5), (广东省,2,7), (广东省,3,3), (广东省,5,1)))
        (浙江省,CompactBuffer((浙江省,8,1), (浙江省,10,1), (浙江省,5,6), (浙江省,7,4), (浙江省,6,1)))
         */
        .mapValues(_.toList.sortWith(_._3 > _._3).take(3))
        //.foreach(println)
        /*
        (广东省,List((广东省,2,7), (广东省,4,5), (广东省,3,3)))
        (浙江省,List((浙江省,5,6), (浙江省,7,4), (浙江省,8,1)))
         */
        .flatMap(_._2)
        .foreach(println)
        /*
        (广东省,2,7)
        (浙江省,5,6)
        (广东省,4,5)
        (浙江省,7,4)
        (广东省,3,3)
        (浙江省,8,1)
         */
    spark.stop()
  }
}
