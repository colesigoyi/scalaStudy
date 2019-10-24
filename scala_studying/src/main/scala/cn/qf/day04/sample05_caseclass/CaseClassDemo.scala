package cn.qf.day04.sample05_caseclass

/**
 * Description：样例类<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月24日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object CaseClassDemo extends App{
  val cat = new Cat("小猫咪")
  val cat2 = Cat("大猫咪") //多例样例类编译后,编译器会为其准备一个伴生对象以及伴生对象中的apply方法
  println(cat)
  println(cat2)

  val lion = Lion
  println(lion)
}
