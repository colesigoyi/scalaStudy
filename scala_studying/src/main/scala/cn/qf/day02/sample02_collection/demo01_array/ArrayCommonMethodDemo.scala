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
object ArrayCommonMethodDemo {
  def main(args: Array[String]): Unit = {
    val nums = Array(2,4,3,1,7,9,8,5,6)
    //max
    println(s"数组中最大的元素是:${nums.max}")
    //min
    println(s"数组中最小的元素是:${nums.min}")
    //count
    println(s"数组中所有元素和是:${nums.sum}")
    //sorted,reverse
    println(s"数组中所有偶数元素个数和是:${nums.count(_%2==0)}")
    //sortWith
    println(s"数组所有元素升序排列:${nums.sorted.toBuffer}")
    println(s"数组所有元素降序排列:${nums.sorted.reverse.toBuffer}")
    println(s"数组所有元素升序排列:${nums.sortWith(_<_).toBuffer}")
    println(s"数组所有元素降序排列:${nums.sortWith(_>_).toBuffer}")
    println(s"数组所有元素降序排列:${nums.sortWith((x, y) => x < y).toBuffer}")
  }
}
