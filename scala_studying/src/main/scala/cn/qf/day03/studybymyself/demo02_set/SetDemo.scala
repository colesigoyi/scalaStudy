package cn.qf.day03.studybymyself.demo02_set

import scala.collection.immutable.HashSet
import scala.collection.mutable


/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月22日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */

object ImmutSetDemo extends App {

  //不可变set
  val set1 = new HashSet[Int]()
  val set2 = set1 + 4
  println(s"set1的元素是:$set1")
  println(s"set2的元素是:$set2")

  val set3 = set1 ++ Set(1,2,3,4)
  val set4 = set3 ++ set2
  println(s"set3的元素是:$set3")
  println(s"set4的元素是:$set4")  //
  println(set4.getClass)  //scala.collection.immutable.HashSet$HashTrieSet


}
object MutSetDemno extends App {
  val set1 = new mutable.HashSet[Int]()
  set1.add(1)
  set1 += 2
  set1 ++= Set(3,4,5)
  println(set1)
  set1 -= 5
  set1.remove(2)
  println(set1)

}