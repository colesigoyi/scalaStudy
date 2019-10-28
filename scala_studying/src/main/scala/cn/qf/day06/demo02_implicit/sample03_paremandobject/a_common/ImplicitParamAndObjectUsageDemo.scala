package cn.qf.day06.demo02_implicit.sample03_paremandobject.a_common

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月28日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object ImplicitParamAndObjectUsageDemo {
  def main(args: Array[String]): Unit = {
    //步骤
    //1.准备与隐式参数同类型的隐式实例
    implicit val instance = new Cal[Int] {
      override def add(x: Int, y: Int): Int = x + y
    }
    //2.调用包含了隐式参数的方法
    //下述代码会自动搜寻有没有同类型的隐式实例,有的话会自动注入,没有的话编译报错
    val result1 = calculateSum(4, 6)

    //3.输出结果
    println(result1)

  }

  /**
   * 带隐式参数的方法
   * @param x
   * @param y
   * @param c 隐式参数
   * @return
   */
  def calculateSum(x:Int, y:Int)(implicit c:Cal[Int]) = c.add(x, y)

}

/**
 * 特质
 * @tparam T
 */
trait Cal[T]{
  def add(x:Int, y:Int):T
}