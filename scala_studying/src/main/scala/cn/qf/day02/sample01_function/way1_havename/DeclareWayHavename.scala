package cn.qf.day02.sample01_function.way1_havename

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月22日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object DeclareWayHavename {
  def main(args: Array[String]): Unit = {
    println(s"调用[声明式的有名函数的一般写法],结果是${fun1(1,2)}")
    println(s"调用[声明式的有名函数的简单写法],结果是${fun2(1,2)}")
    println(s"调用[声明式的有名函数的利用下划线进行简化写法],结果是${fun3(1,2)}")
    val v1 = fun4(2,3)
    val v2 = fun4(3,3)
    println(s"$v1")
    println(s"$v2")
  }
  /**
  * 声明式的有名函数的一般写法
  *  (a:Int, b:Int) -> 函数的形参列表
  * a + b -> 函数体
  * => -> 将函数的声明与函数体分隔开
  */
  val fun1 : ((Int, Int) => Int) = {(x, y) => x + y}
  /**
   * 声明式的有名函数的简单写法
   */
  val fun2 : (Int, Int) => Int = (x, y) => x + y
  /**
   * 声明式的有名函数的利用下划线进行简化写法
   */
  val fun3 : (Int, Int) => Int = (_ + _)
  val fun4 : (Int, Int) => Int = {
    println("##################") //声明的时候进行调用,只调用一次
    _ + _
  }
}
