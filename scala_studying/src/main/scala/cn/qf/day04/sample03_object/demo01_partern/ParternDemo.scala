package cn.qf.day04.sample03_object.demo01_partern

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月24日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object ParternDemo {
  def main(args: Array[String]): Unit = {
    //获得Person实例
    //方式1:使用半生类
    val p1 = new Person
    p1.show

    println("-----------------------")
    //方式2:使用伴生对象
    val p2 = Person
    //p2.show伴生对象和伴生类中的方法彼此之间不能直接通用
    p2.doSomething(p1)
  }
}
