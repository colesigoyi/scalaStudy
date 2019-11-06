package cn.qf.day12.demo03_ad.sample02_perprovinceperhourtop3

import cn.qf.dao.impl.PerHourProvinceTop3Impl
import cn.qf.entity.PerHourProvinceTop3
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
object PerProvincePerHourTop3ADDemo3 {
  def main(args: Array[String]): Unit = {
    val spark = SparkUtil.getSparkSession(PerProvincePerHourTop3ADDemo3.getClass.getName,
      "local[*]")
    val sc = spark.sparkContext

//    if(args.length != 1) {
//      println(
//        """
//          |请传入N
//          |""".stripMargin)
//      sys.exit(-1)
//    }
//    val topN = args(0).toInt
//    //封装到广播变量中
//    val bcTopN = sc.broadcast[Int](topN)
    //->testFile
    //->map:将rdd中的每个元素变形为对偶元组,形如(key=(省份,小时,广告ID), value=1)
    //->reduceByKey:统计出不同省份不同小时不同广告点击的总次数
    //->groupByKey:key=(省份,小时),value=同省份不同省份不同小时,各个广告的点击总次数的集合
    //->mapValues :分析rdd中每个元素的值(集合).对集合中的元素根据次数求top3

    sc.textFile("file:////Users/taoxuefeng/Documents/02_StudyCoding/09_scala/scala_studying/ad/ad.txt")
      .map(perEle => {
        val arr = perEle.split("\\s+")
        val province = arr(1).trim
        val hour = getTimeByMillion(arr(0).toLong)
        val adID = arr(4).trim
        ((province, hour, adID), 1)
      })
      .reduceByKey(_+_)
      .map(perEle => {
        ((perEle._1._1,perEle._1._2),(perEle._1._3,perEle._2))
      })
      .groupByKey()
      .mapValues(perEle => getTop3(perEle.toList.sortBy(_._2).reverse))
      .foreach(perEle => {
        val province = perEle._1._1
        val time = perEle._1._2
        val lst = perEle._2
        val dao = new PerHourProvinceTop3Impl
        lst.foreach(tuple => {
          val adId = tuple._1
          val cnt = tuple._2
          val entity = new PerHourProvinceTop3(province, time, adId.toInt, cnt)
          dao.save(entity)
        })

      })

    spark.stop()
  }

  /**
   * 获得指定天中的某个小时
   * @param millions
   * @return
   */
  def getTimeByMillion(millions:Long) = {
    val hour = new DateTime(millions).getHourOfDay
    val year = new DateTime(millions).getYear
    val day = new DateTime(millions).getDayOfMonth
    val month = new DateTime(millions).getMonthOfYear
    year + "年" + month + "月" + day + "日" + hour + "时"
  }
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