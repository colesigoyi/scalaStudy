package cn.qf.day05.demo08_highlevelfunction

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月25日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object HighLevelFunctionDemo {
  def main(args: Array[String]): Unit = {
    //传入的参数值为函数
    //    参数:
    //        - 有名函数
    val f : (Int, Int) => Int = (x, y) => x + y
    val f2 = (x:Int, y:Int) => x + y
    println(s"传入声明式的有名函数,结果是:${add(1,3,f)}")
    println(s"传入赋值式的有名函数,结果是:${add(1,3,f2)}")
    //        - 匿名函数

    println(s"传入匿名函数,结果是:${add(1,3,(x, y) => x + y)}")
    println(s"传入匿名函数,结果是:${add(1,3,_ + _)}")
    //        - 方法(会自动将方法转换为函数,也可以手动将方法转换为函数)
    println(s"传入方法,自动转换,结果是:${add(1,3,myMethod)}")
    println(s"传入方法,手动转换,结果是:${add(1,3,myMethod _)}")
  }
  def add(x:Int, y:Int, f:(Int, Int)=>Int) = f(x,y)

  /**
   * 方法
   * @param x
   * @param y
   * @return
   */
  def myMethod(x:Int, y:Int) = x + y
}
