package sample02_testdemo

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月21日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object ScalaTestDemo1 {
  def main(args: Array[String]): Unit = {
    var age1 = 18
    var age2:Int = 19
    val age3 = 20
    //val是常量,一旦确定,就固化,只能赋值一次
    //age3 = 200
    age1 = 188
    //程序执行到此处,在内存中没有开辟空间进行存储,访问的时候,才会开辟内存空间进行存储
    lazy val distance = 2900
    print(s"distance = ${distance}km")

  }
}
