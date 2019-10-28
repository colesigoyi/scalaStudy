package cn.qf.day06.demo03_generic.sample05_viewbound.way1_comparable

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月28日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object ViewBoundDemo {
  def main(args: Array[String]): Unit = {
    //情形1:T若是String
    val bigger = getBigger("hadoop", "spark")
    println(s"较大的值是:$bigger")//String可以自动转化为Comparable[String]类型,因为String实现了接口
    println("------------------------------")
    //情形2:T若是Int
    val bigger2 = getBigger(23, 24)
    println(s"较大的值是:$bigger2")//Scala中的Int可以自动转化为java的Integer,而java中的Integer又实现了Comparable<Integer>接口
    println("------------------------------")
  }
  /**
   *
   * @param firse
   * @param second
   * @tparam T  T <% Comparable[T] 使用了泛型之视界
   * @return
   */
  def getBigger[T <% Comparable[T]](firse:T, second:T) = {
    if(firse.compareTo(second) > 0) firse else second
  }
}
