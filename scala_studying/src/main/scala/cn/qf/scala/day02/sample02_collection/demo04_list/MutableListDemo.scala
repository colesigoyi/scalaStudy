package cn.qf.scala.day02.sample02_collection.demo04_list

import scala.collection.mutable.ListBuffer

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月22日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object MutableListDemo extends App {
  val lst1 = ListBuffer(1,2)
  val lst2 = ListBuffer(7,8,9)

  //新增元素
  lst1 += 3
  lst1.append(4,5,6)
  println(s"添加完新元素后集合lst1中的元素是:$lst1")

  //移除元素
  lst1 -= 3
  lst1.remove(lst1.size-2, 2)
  println(s"移除元素后集合lst1中的元素是:$lst1")

  //修改元素
  lst1(1) = 5
  lst1.update(lst1.size-1,22)
  println(s"更新元素后集合lst1中的元素是:$lst1")

  //++合成
  val newLst:ListBuffer[Int] = lst1 ++ lst2
  println(s"lst1中的元素是:$lst1")
  println(s"lst2中的元素是:$lst2")
  println(s"合并集合后新集合newLst中的元素是:$newLst")

  //:+将新的元素添加到当前的ListBuffer之后,并赋值给新的ListBuffer
  val newLst2 = lst1 :+ 33
  println(s"lst1中的元素是:$lst1")
  println(s"合并集合后新集合newLst中的元素是:$newLst2")
}
