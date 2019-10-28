package cn.qf.day06.demo02_implicit.sample03_paremandobject.b_advanced

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月28日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object ImplicitParamAdvancedUsageDemo {
  def main(args: Array[String]): Unit = {
    //调用：
    //引入隐式实例
    //import MyPredef._
    import MyPredef.instance
    val result = calculateSum(2, 9)
    println(s"结果是：$result")

    println("\n____________________________________________\n")

    //下述的写法，没有使用到隐式参数的特性，不建议使用
    val result2 = calculateSum2(1.0, 9.0)(new Cal[Double] {
      /**
       * 求和的一个抽象方法
       *
       * @param x
       * @param y
       * @return
       */
      override def add(x: Double, y: Double): Double = x + y

    })
    println(s"结果是：$result2")
  }

  /**
   * 带隐式参数的方法
   *
   * @param x
   * @param y
   * @param c
   * @return
   */
  def calculateSum(x: Int, y: Int)(implicit c: Cal[Int]) = c.add(x, y)


  /**
   * 带隐式参数的方法
   *
   * @param x
   * @param y
   * @param c
   * @return
   */
  def calculateSum2[T](x: T, y: T)(implicit c: Cal[T]) = c.add(x, y)
}