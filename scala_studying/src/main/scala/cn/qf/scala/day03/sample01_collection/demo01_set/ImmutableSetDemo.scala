package cn.qf.scala.day03.sample01_collection.demo01_set

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月23日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object ImmutableSetDemo extends App {
  //Set集合的实例
  val nums = Set[Int](1,2,0,3,-1)
  //增:会生成一个全新的Set集合,与原来的没有关系
  val newNums = nums + 4
  println(s"旧的Set集合是:$nums")
  println(s"新的Set集合是:$newNums")

  println("------------------------------")
  val newNums2 = nums ++ Set(1,2,3,4,5,6)
  println(s"旧的Set集合是:$nums")
  println(s"新的Set集合是:$newNums2")
}
