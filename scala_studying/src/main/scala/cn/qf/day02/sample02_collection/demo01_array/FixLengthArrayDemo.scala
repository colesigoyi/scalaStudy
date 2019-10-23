package cn.qf.day02.sample02_collection.demo01_array

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月22日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */

object FixLengthArrayDemo {
  def main(args: Array[String]): Unit = {
    //设计一个Int类型的长度是8的定长数组,每个元素默认值是0
    println("--------Int类型的长度是8的定长数组,每个元素默认值是0-----")
    val nums = new Array[Int](8)
    println(s"数组中的元素是:${nums.toBuffer}")
    println("--------Int类型的长度是8的定长数组,每个元素默认值是0-----")
    //设计一个Int类型的长度是8的定长数组,每个元素默认值是0
    val nums2 = Array[Int](8) //底层调用的是Array类对应的伴生对象中的apply方法
    nums2(0) = 1
    println(s"数组中的元素是:${nums2.toBuffer}")
    println("---------------修改数组中的最后一个元素为100------------")
    //修改数组中的最后一个元素为100
    //注意:定长数组仅仅指长度是固定的,其中元素可以删改
    nums(nums.length-1) = 100
    println(s"数组中的元素是:${nums.toBuffer}")

//    nums(nums.length) = 100 //java.lang.ArrayIndexOutOfBoundsException
//    println(s"数组中的元素是:${nums.toBuffer}")
    println("--------------遍历数组(放入新集合)----------")
    val stu = Array[Any]("张无忌",true,198.1)
    for (perEle <- stu)
      println(perEle)
    println("--------------遍历数组(使用下标)----------")
    for (index <- stu.indices)
      println(stu(index))
  }
}
