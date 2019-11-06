package cn.qf.day12.demo03_ad.sample02_perprovinceperhourtop3

import cn.qf.util.SparkUtil
import org.joda.time.DateTime

import scala.collection.mutable.ListBuffer

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年11月04日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object PerProvincePerHourTop3ADDemo {
  def main(args: Array[String]): Unit = {
    val spark = SparkUtil.getSparkSession(PerProvincePerHourTop3ADDemo.getClass.getName,
      "local[*]")
    val sc = spark.sparkContext

    //->testFile
    //->map:将rdd中的每个元素变形为对偶元组,形如(key=(省份,小时,广告ID), value=1)
    //->reduceByKey:统计出不同省份不同小时不同广告点击的总次数
    //->groupByKey:key=(省份,小时),value=同省份不同省份不同小时,各个广告的点击总次数的集合
    //->mapValues :分析rdd中每个元素的值(集合).对集合中的元素根据次数求top3

    sc.textFile("file:////Users/taoxuefeng/Documents/02_StudyCoding/09_scala/scala_studying/ad/ad.txt")
        .map(perEle => {
          val arr = perEle.split("\\s+")
          val province = arr(1).trim
          val hour = getHourByMillion(arr(0).toLong)
          val adID = arr(4).trim
          ((province, hour, adID), 1)
        }).reduceByKey(_+_)
        //.foreach(println)
        /*
        ((浙江省,4,5),2)
        ((广东省,22,3),2)
        ((广东省,10,2),5)
        ((广东省,9,2),1)
        ((浙江省,15,7),2)
        ((广东省,15,5),1)
        ((广东省,14,4),4)
        ((浙江省,4,7),2)
        ((广东省,15,2),1)
        ((浙江省,1,5),2)
        ((广东省,9,4),1)
        ((浙江省,15,10),3)
        ((浙江省,15,6),1)
        ((广东省,14,3),1)
        ((浙江省,3,5),2)
        ((浙江省,15,8),2)
         */
        .map(perEle => {
          ((perEle._1._1,perEle._1._2),(perEle._1._3,perEle._2))
        })
        .groupByKey()
        //.foreach(println)
        /*
        ((广东省,14),CompactBuffer((3,1), (4,4)))
        ((广东省,9),CompactBuffer((2,1), (4,1)))
        ((浙江省,1),CompactBuffer((5,2)))
        ((广东省,10),CompactBuffer((2,5)))
        ((浙江省,4),CompactBuffer((7,2), (5,2)))
        ((广东省,15),CompactBuffer((5,1), (2,1)))
        ((浙江省,3),CompactBuffer((5,2)))
        ((浙江省,15),CompactBuffer((10,3), (8,2), (7,2), (6,1)))
        ((广东省,22),CompactBuffer((3,2)))
         */
        .mapValues(perEle => getTop3(perEle.toList.sortBy(_._2).reverse))
        .foreach(println)
        /*
        ((广东省,9),List((4,1), (2,1)))
        ((广东省,14),List((4,4), (3,1)))
        ((浙江省,4),List((5,2), (7,2)))
        ((浙江省,1),List((5,2)))
        ((浙江省,3),List((5,2)))
        ((广东省,10),List((2,5)))
        ((浙江省,15),List((10,3), (7,2), (8,2), (6,1)))
        ((广东省,15),List((2,1), (5,1)))
        ((广东省,22),List((3,2)))
         */
    spark.stop()
  }

  /**
   * 获得指定天中的某个小时
   * @param millions
   * @return
   */
  def getHourByMillion(millions:Long) = new DateTime(millions).getHourOfDay

  /**
   * 根据传入的排序之后的结果求top3,要求:排名相同的也显示
   * @param container
   */
  def getTop3(container:List[(String, Int)]) = {
    val newContainer = ListBuffer[(String, Int)]()
    val cntConitiner = ListBuffer[Int]()
    cntConitiner ++= container.map(_._2).distinct.take(3)
    //然后在遍历剩余元素,若次数相同就添加进去
    for (index <- 0 until container.size) {
      val newEle = container(index)
      if (cntConitiner.contains(newEle._2)) {
        newContainer.append(newEle)
      }
    }
    newContainer.sortWith(_._2 > _._2).toList
  }
}
