package cn.qf.day04.sample04_typeoperation

/**
 * Description：类型检查和转换<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月24日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object TypeOperationDemo {
  def main(args: Array[String]): Unit = {
    val cat = new Cat
    val lion = new Lion

    println(s"cat是Cat的实例吗?${cat.isInstanceOf[Cat]}")//true
    println(s"cat是Pet的实例吗?${cat.isInstanceOf[Pet]}")//true
    println(s"lion是Pet的实例吗?${lion.isInstanceOf[Pet]}")//false

    if (lion.isInstanceOf[Pet]) {
      val pet = lion.asInstanceOf[Pet]
      println(pet)//false
    }else {
      println("警告,lion不是Pet,不能转换")
    }

    println(s"Lion类对应的Class的实例是:${classOf[Lion]}")
    println(s"使用java的api,Lion类对应的Class的实例是:${lion.getClass}")
    println(s"使用java的api,Lion类对应的Class的实例是:${Class.forName("cn.qf.day04.sample04_typeoperation.Lion")}")

  }
}
