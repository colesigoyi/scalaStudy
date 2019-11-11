package cn.qf.scala.day02.sample01_function.demo03_funtruth

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月22日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object FunctionTruthDemo {
  def main(args: Array[String]): Unit = {
    val r1 = fun(1,2)
    val r2 = fun2(1,2)
    val r3 = fun2.apply(1,2)
    println(s"$r1 $r2 $r3")
  }

  /**
   * 赋值式有名函数
   */
  val fun = (a:Int, b:Int) => a + b
  /**
   * 上述赋值式有名函数等价于下述写法(内幕)
   */
  val fun2 = new Function2[Int, Int, Int]() { //表面上new接口,本质上new的是接口匿名的实现类的实例
    /**
     * 重写特质中的抽象方法apply
     * @param v1
     * @param v2
     * @return
     */
    override def apply(v1:Int, v2:Int):Int = v1 + v2
  }
}
