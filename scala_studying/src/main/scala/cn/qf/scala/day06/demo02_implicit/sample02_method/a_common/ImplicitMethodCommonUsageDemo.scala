package cn.qf.scala.day06.demo02_implicit.sample02_method.a_common

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月28日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object ImplicitMethodCommonUsageDemo {
  /**
   * 将Double转换成Int
   * @param num
   * @return
   */
  implicit def double2int(num:Double) = num.toInt

  def main(args: Array[String]): Unit = {

    //需求:使用隐式方法,将Double自定转换成Int
    //编译不报错原因:
    //1.待转换值的类型和隐式方法的参数类型吻合
    //2.转换后的值的类型和隐式方法的返回值类型一致
    val num:Int = 3.5
    println(num)
  }
}
