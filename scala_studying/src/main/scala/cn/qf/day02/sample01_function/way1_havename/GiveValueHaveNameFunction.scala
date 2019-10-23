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
object GiveValueHaveNameFunction {
  def main(args: Array[String]): Unit = {
    println(s"调用[赋值式的有名函数的一般写法],结果是${fun1(1,2)}")
    println(s"调用[赋值式的有名函数的简化写法],结果是${fun2(1,2)}")
    println(s"调用[赋值式的有名函数利用下划线简化写法],结果是${fun3(1,2)}")
    println(s"调用[赋值式的有名函数的复杂函数体的写法],结果是${fun4(1,2)}")
    println(s"调用[赋值式的有名函数的复杂函数体的写法],结果是${fun4(1,2)}")
  }

  /**
   * 赋值式的有名函数的一般写法
   * (a:Int, b:Int) -> 函数的形参列表
   * a + b -> 函数体
   * => -> 将函数的声明与函数体分隔开
   */
  val fun1 = ((a:Int, b:Int) => a + b)
  /**
   * 赋值式的有名函数的简化写法
   */
  val fun2 = (a:Int, b:Int) => a + b
  /**
   * 赋值式的有名函数利用下划线简化写法
   */
  val fun3 = (_:Int) + (_:Int)
  /**
   * 赋值式的有名函数的复杂函数体的写法
   */
  val fun4 = (a:Int, b:Int) => {
    println("------------------")
    a + b
  }
}
