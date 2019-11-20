package cn.qf.streaming.day05.test

import java.text.SimpleDateFormat
import java.util.Date

import cn.qf.streaming.day05.test.UpdateStateHDFS.formatDate
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

    sc.textFile("file:////Users/taoxuefeng/Documents/02_StudyCoding/09_scala/scala_studying/ad/Advert.log")
      .map(perLine => {
        val arr = perLine.split("\\t")
        val time = formatDate(arr(0).toLong)
        val city = arr(1)
        val province = arr(2)
        val userID = arr(3)
        val adID = arr(4)
        ((time,province, city, adID),1)
      }).reduceByKey(_+_)
//      .map(perEle => {
//        ("日期:" + perEle._1._1+ " 省份:" +perEle._1._2+ " 城市:" +perEle._1._3+ "广告ID" +perEle._1._4,"点击量:" + perEle._2)
//      })
//      .groupByKey()
//      .mapValues(perEle => getTop3(perEle.toList.sortBy(_._2).reverse))
        .foreach(println)
        /*
        (20190703,(Luoyang,Henan,1104))
(20190703,(Zhangjiakou,Hebei,1089))
(20190703,(Nanjing,Jiangsu,1053))
(20190703,(Luoyang,Henan,1081))
(20190703,(Xiangtan,Hunan,1082))
(20190703,(Shijiazhuang,Hebei,1104))
(20190703,(Shijiazhuang,Hebei,1046))
(20190703,(Luoyang,Henan,1117))
(20190703,(Jingzhou,Hubei,1041))
(20190703,(Wuhan,Hubei,1138))

((20190703,Shijiazhuang,Hebei,9),1132)
((20190703,Xiangtan,Hunan,2),1074)
((20190703,Shijiazhuang,Hebei,1),1112)
((20190703,Changsha,Hunan,4),1102)
((20190703,Zhangjiakou,Hebei,4),1096)
((20190703,Zhengzhou,Henan,9),1063)
((20190703,Wuhan,Hubei,8),1139)
((20190703,Shijiazhuang,Hebei,6),1108)
((20190703,Zhangjiakou,Hebei,0),1106)
((20190703,Zhangjiakou,Hebei,9),1065)
((20190703,Shijiazhuang,Hebei,3),1091)
((20190703,Nanjing,Jiangsu,1),1109)
((20190703,Changsha,Hunan,0),1034)
((20190703,Luoyang,Henan,3),1079)
((20190703,Xiangtan,Hunan,6),1056)
((20190703,Zhangjiakou,Hebei,6),1072)
((20190703,Suzhou,Jiangsu,6),1067)
         */

//        .groupByKey()
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
//        .mapValues(perEle => getTop3(perEle.toList.sortBy(_._2).reverse))
//        .foreach(println)
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
  def formatDate(timestamp: Long) = new SimpleDateFormat("yyyyMMdd").format(new Date(timestamp))

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
