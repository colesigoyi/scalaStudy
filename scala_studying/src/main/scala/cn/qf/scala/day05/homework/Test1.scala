package cn.qf.scala.day05.homework

import java.util

import scala.collection.mutable.ListBuffer

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月25日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object Test1 {
  def main(args: Array[String]): Unit = {
    val lst1 = ListBuffer[Int]()
    for (perEle <- 1 to 100) {
      lst1 += perEle
    }
    println(s"存放1~100的整数的list1:$lst1")
    val lst2 = ListBuffer[Int]()
    for (perEle <- lst1 if perEle % 2 == 0) {
      lst2 += perEle
    }
    println(s"筛选出偶数后放入list2:$lst2")
  }
}
