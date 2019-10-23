package cn.qf.day03.sample01_collection.demo03_exercise

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月23日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object CollectionExerciseDemo2 {
  def main(args: Array[String]): Unit = {
    //需求:将集合中所有的单词出现的总次数进行统计,并根据次数降序输出

    //前提:准备容器,其中存放一些单词
    val words = Seq("hello hello are  hi hi hi you  you", "hi  fine good are are  good",
      "hello", "hi  fine good are are  good","hello", "hi  fine good are are  good","hello")
    println("-------------合并版-----------------")
    //合并
    val sortedList2 = words.flatMap(_.split("\\s+"))
      .map((_, 1))
      .groupBy(_._1)
      .mapValues(_.size)
      .toList
      .sortWith(_._2 > _._2)
      .foreach(perEle => println(perEle._1 + "\t" + perEle._2))
  }
}
