package cn.qf.day04.sample02_singleobj

/**
 * Description：单例类<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月24日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object SingleObjectDemo {
  def main(args: Array[String]): Unit = {
    //测试单例类
    //测试:单例性
    //val instance1 = new President
    val instance1 = President
    val instance2 = President

    println(s"实例是否相同? ${instance1 == instance2}")//true
    println("---------------------------")
    //测试2:验证单例类的成员是否使用static修饰
    println(s"总统的名字是:${President.name}")

    println("---------------------------")
    President.show
    instance1.show
    println("---------------------------")
    new PrimePresident
  }
}
