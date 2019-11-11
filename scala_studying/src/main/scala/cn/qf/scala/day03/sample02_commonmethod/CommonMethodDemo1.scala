package cn.qf.scala.day03.sample02_commonmethod

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月23日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object CommonMethodDemo1 {
  def main(args: Array[String]): Unit = {
    val nums = Seq(1,2,3,4,5,6,7,8,9)
    //sum
    println(s"Seq中所有的元素和:${nums.sum}")
    //max
    println(s"Seq中所有的元素中最大的元素是:${nums.max}")
    //min
    println(s"Seq中所有的元素中最xiao的元素是:${nums.min}")
    //count
    println(s"Seq中偶数元素个数是:${nums.count(_ % 2 == 0)}")
  }
}

