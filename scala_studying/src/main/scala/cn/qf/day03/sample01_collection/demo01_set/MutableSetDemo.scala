package cn.qf.day03.sample01_collection.demo01_set

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月23日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object MutableSetDemo {
  def main(args: Array[String]): Unit = {
    //准备一个可变的Set集合的实例
    val container = mutable.Set[Int]()
    println("------------------------------")
    //增
    container.add(1)
    container += 2
    container ++= Set(3,4,5,7,6,9)
    println(s"添加元素后可变的Set集合中的元素是:${container}")
    println("------------------------------")
    //删
    container -= 9
    container.remove(1)
    container --= Set(3,4)
    println(s"移除元素后可变的Set集合中的元素是:${container}")
    println("------------------------------")
    //改
    container.update(8,included = true) //不存在就添加,存在就不变
    container.update(2,included = false)  //存在就删除,不存在就不变

    //val newList:ArrayBuffer[Int] = container.toBuffer.asInstanceOf[ArrayBuffer[Int]]
    val newList:mutable.Buffer[Int] = container.toBuffer
    newList(newList.size - 1) = 200

    println(s"修改元素后可变的Set集合中的元素是:${container}")
    println(s"修改元素后可变的Set集合中的元素是:${newList}")
    println("------------------------------")
    //查
    for (vals <- container)
      println(s"Set集合中的元素是:$vals")

    //inkedHashSet
    val lhs = mutable.LinkedHashSet[Int]()
    lhs += 1
    lhs ++= Set(2,5,0,6,4)
    println(s"linkedHashSet的有序元素是:$lhs")
  }
}
