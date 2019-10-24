package cn.qf.day04.sample03_object.demo02_apply

/**
 * Description：伴生对象,伴生类<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月24日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object TestApply extends App {
  val boy1 = Boy("jack")
  println(boy1)

  val boy2 = Boy("marry", 18)
  println(boy2)
  /**
   * 上面的写法是下面写法的简化形式
   */
  val boy3 = Boy.apply("lucy")
  println(boy3)

  println("----------------------")
  val arr = Array(1,2,3)  //本质是调用用的是Array伴生对象中的apply方法

  Girl(Array("跑步","健身"))
  Girl.apply(Array("跑步","健身"))

}
