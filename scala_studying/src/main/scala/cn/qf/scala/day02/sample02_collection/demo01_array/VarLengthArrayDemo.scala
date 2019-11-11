package cn.qf.scala.day02.sample02_collection.demo01_array

import scala.collection.mutable.ArrayBuffer

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月22日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */

object VarLengthArrayDemo {
  def main(args: Array[String]): Unit = {
    //设计一个Int类型的长度是8的定长数组,每个元素默认值是0
    println("--------变长数组-----")
    val container = new ArrayBuffer[Int]()
    println(s"数组中的元素是:${container.toBuffer}")

    println("--------增加元素-----")
    container += 1
    container += (2,3,4,5,6,7)
    container ++= Array(8,9,10,12)
    container.insert(container.length-1,11)
    println(s"数组中的元素是:${container}")

    println("--------删除元素-----")
    container.remove(container.length-2,2)
    container.trimEnd(2)
    container.trimStart(1)
    println(s"数组中的元素是:${container}")

    println("--------修改元素-----")
    container(container.length-1) = 100
    container.update(0,200)
    println(s"数组中的元素是:${container}")

    println("--------查询元素-----")
    println(s"数组中最后一位的元素是:${container(container.length-1)}")

    println("--------遍历元素-----")
    for (index <- container.indices)
      println(s"数组中下标为${index}的值为:${container(index)}")
    println("--------foreach遍历元素-----")
    container.foreach(println)
    println("-----------------------------")
    container.foreach(perEle => println(perEle))
    println("-----------------------------")
    container.foreach(perEle => print(perEle + "\t"))
  }
}
