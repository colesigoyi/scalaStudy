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

object ArrayCommonChangeDemo {
  def main(args: Array[String]): Unit = {
    val arr = Array(1,2,3,4,5,6,7,8,9)

    //for循环的推导式
    val newArr = for (perEle <- arr) yield perEle * 2
    println(s"旧的定长数组中的元素是:${arr.toBuffer}")
    println(s"新的定长数组中的元素是:${newArr.toBuffer}")
    println("--------------------------------------")
    //带守卫的for循环推导式
    val newArr2 = for (perEle <- arr if perEle % 2 == 0) yield perEle * 2
    println(s"旧的定长数组中的元素是:${arr.toBuffer}")
    println(s"新的定长数组中的元素是:${newArr2.toBuffer}")
    println("--------------------------------------")
    //使用filter,map方法实现上述案例
    val newArr3 = arr.filter(_ % 2 == 0).map(_ * 2)
    val newArr4 = arr.filter(perEle => perEle % 2 == 0).map(perEle => perEle * 2)
    println(s"旧的定长数组中的元素是:${arr.toBuffer}")
    println(s"简化前新的定长数组中的元素是:${newArr4.toBuffer}")
    println(s"简化后新的定长数组中的元素是:${newArr3.toBuffer}")
  }
}
