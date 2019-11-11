package cn.qf.scala.day02.sample01_function.way2_anonymousfun

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月22日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object AnonymousFunctionDemo {
  def main(args: Array[String]): Unit = {
    val x = 3
    val y = 5
    println(s"$x + $y = ${calculate(x, y, (x, y) => x + y)}")
    println(s"$x x $y = ${calculate(x, y, (x, y) => x * y)}")
    println(s"$x - $y = ${calculate(x, y, (x, y) => x - y)}")
    println(s"$x / $y = ${calculate(x, y, (x, y) => x / y)}")
    val result = calculate(x, y, fun1)
    println(s"$x * $y = $result")
    println(add(2,2))

  }
  val fun1 = (_:Int) * (_:Int)

  /**
   *
   * @param x
   * @param y
   * @param fun:fun:(Int, Int) => Int = (x, y) => x + y,fun(x, y0由调用点来决定
   * @return
   */
  def calculate(x:Int, y:Int, fun:(Int, Int) => Int) = fun(x, y)//被调用的函数体
  def add(x1:Int, y1:Int) = {
    def add2(x2:Int, y2:Int) = x2 + y2
      add2(4, 4) + x1 + y1
  }
}
