package cn.qf.day10.sample01_loc

import cn.qf.util.SparkUtil


/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年11月01日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object LocationExerciseDemo {
  def main(args: Array[String]): Unit = {
    //SparkSession
    val sc = SparkUtil.getSparkSession(LocationExerciseDemo.getClass.getName, "local[*]").sparkContext



    val phoneRDD = sc.textFile("file:////Users/taoxuefeng/Documents/02_StudyCoding/09_scala/scala_studying/" +
      "loc/mobilelocation/log")
      .map(perLine => {
        val arr = perLine.split(",")
        val phoneNum = arr(0).trim
        val tmpTime = arr(1).trim.toLong
        val lacId = arr(2).trim
        val flag = arr(3).trim.toInt
        val time = if (flag == 1) -tmpTime else tmpTime
        //根据手机号和基站id进行分组,将其作为分组依据,若据此进行聚合,结果即为:用户在各个基站停留的时间
        ((phoneNum, lacId), time)
      }).reduceByKey(_ + _)
      //.foreach(println)

//        ((18688888888,9F36407EAD8829FC166F14DDE7970F68),51200)
//        ((18101056888,9F36407EAD8829FC166F14DDE7970F68),54000)
//        ((18101056888,CC0710CC94ECC657A8561DE549D940E0),1900)
//        ((18688888888,CC0710CC94ECC657A8561DE549D940E0),1300)
//        ((18688888888,16030401EAFB68F1E3CDF819735E1C66),87600)
//        ((18101056888,16030401EAFB68F1E3CDF819735E1C66),97500)

      .groupBy(_._1._1)
      //.foreach(println)

//        (18688888888,CompactBuffer(((18688888888,CC0710CC94ECC657A8561DE549D940E0),1300), ((18688888888,16030401EAFB68F1E3CDF819735E1C66),87600), ((18688888888,9F36407EAD8829FC166F14DDE7970F68),51200)))
//        (18101056888,CompactBuffer(((18101056888,9F36407EAD8829FC166F14DDE7970F68),54000), ((18101056888,16030401EAFB68F1E3CDF819735E1C66),97500), ((18101056888,CC0710CC94ECC657A8561DE549D940E0),1900)))

      .mapValues(_.toList.sortBy(_._2).reverse.take(2))
      //.foreach(println)

//        (18688888888,List(((18688888888,16030401EAFB68F1E3CDF819735E1C66),87600), ((18688888888,9F36407EAD8829FC166F14DDE7970F68),51200)))
//        (18101056888,List(((18101056888,16030401EAFB68F1E3CDF819735E1C66),97500), ((18101056888,9F36407EAD8829FC166F14DDE7970F68),54000)))

      .map(_._2)
      .flatMap(perEle => perEle)
      //.foreach(println)

//     * ((18101056888,CC0710CC94ECC657A8561DE549D940E0),1900)
//     * ((18688888888,CC0710CC94ECC657A8561DE549D940E0),1300)
//     * ((18101056888,9F36407EAD8829FC166F14DDE7970F68),54000)
//     * ((18688888888,9F36407EAD8829FC166F14DDE7970F68),51200)

      .map(perEle => ((perEle._1._2), ((perEle._1._1), (perEle._2))))

      //.foreach(println)



    val lacRDD = sc.textFile("file:////Users/taoxuefeng/Documents/02_StudyCoding/09_scala/scala_studying/" +
      "loc/mobilelocation/lac_info.txt")
      .map(perLine => {
        val arr = perLine.split(",")
        val lacId = arr(0).trim
        val W = arr(1).trim
        val N = arr(2).trim
        (lacId,(W, N))
      })
    //.foreach(println)

//        (9F36407EAD8829FC166F14DDE7970F68,(116.304864,40.050645))
//        (CC0710CC94ECC657A8561DE549D940E0,(116.303955,40.041935))
//        (16030401EAFB68F1E3CDF819735E1C66,(116.296302,40.032296))

      //.map(perEle => (perEle._1, (perEle._2._1, perEle._2._2)))

    phoneRDD.join(lacRDD)
      .map(per=>(per._2._1._1,per._2._2,per._2._1._2))
      .foreach(perEle=>println(s"手机号码: ${perEle._1},经纬度: ${perEle._2},停留时长: ${perEle._3}"))
    /*
     * 手机号码: 18101056888,经纬度: (116.296302,40.032296),停留时长: 97500
     * 手机号码: 18688888888,经纬度: (116.296302,40.032296),停留时长: 87600
     * 手机号码: 18101056888,经纬度: (116.304864,40.050645),停留时长: 54000
     * 手机号码: 18688888888,经纬度: (116.304864,40.050645),停留时长: 51200
     */

    sc.stop()
  }
}
