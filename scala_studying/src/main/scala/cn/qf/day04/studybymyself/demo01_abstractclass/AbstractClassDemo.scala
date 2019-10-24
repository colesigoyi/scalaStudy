package cn.qf.day04.studybymyself.demo01_abstractclass

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月23日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */

class AbstractClassDemo extends Person {
  override var name: String = "jack"
  override var age: Int = 19
}
object AbstractClassTest {
  def main(args: Array[String]): Unit = {
    val d1 = new AbstractClassDemo
    println(s"姓名:${d1.name}")
    println(s"年龄:${d1.age}")
  }
}