package cn.qf.day01.studybymyself

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月21日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object CforTestDemo {
  def main(args: Array[String]): Unit = {

    val t1 = new Test
    var num = t1.ff(2)
    println(num)
    println(m1(f1))
    println(m1(f2))
    println(f1)//方法名是方法调用，而函数名只是代表函数对象本身
    println(m2(3, 3))
    println(m1(f2))

  }

  def m1(f:(Int, Int) => Int) : Int = {
    f(2, 3)
  }
  val f1 = (x: Int, y: Int) => x + y

  val f2 = (m: Int, n: Int) => n * m

  def m2(x:Int, y:Int) = x * y
}
class Test {
  def m(x: Int) = x + 3
  val ff = (x: Int) => x + 3
  val multiplier = (i:Int) => i * 10
}