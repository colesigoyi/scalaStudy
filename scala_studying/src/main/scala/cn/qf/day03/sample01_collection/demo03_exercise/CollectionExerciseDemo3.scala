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
object CollectionExerciseDemo3 {
  def main(args: Array[String]): Unit = {
    val words = Seq("hello hello are  hi hi hi you  you", "hi  fine good are are  good",
      "hello", "hi  fine good are are  good","hello", "hi  fine good are are  good","hello")
    println("------------------------")
    val sortedList2 = words.flatMap(_.split("\\s+"))
      .map((_,1))
      .groupBy(_._1)
      .mapValues(_.foldLeft(0)(_ + _._2))//第一个下划线表示累加过程中的结果,第二个下划线表示元组中的次数
      .toList
//      .sortWith(_._2 > _._2)
//      .sorted//根据单词字典顺序
      .sortBy(_._2)//调用sorted,默认根据每个对偶元组的key的字典顺序进行升序排列
      .reverse  //反转
      .foreach(perEle => println(perEle._1 + "\t" + perEle._2))
  }
}
