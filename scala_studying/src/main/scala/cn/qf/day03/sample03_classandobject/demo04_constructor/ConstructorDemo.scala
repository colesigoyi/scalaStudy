package cn.qf.day03.sample03_classandobject.demo04_constructor

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月23日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object ConstructorDemo extends App{
  val stu1 = new Student("zhangsan",19)
  val stu2 = new Student(18,"lisi")
  val stu3 = new Student("wangwu")
  val stu4 = new Student()
  println(stu1)
  println(stu2)
  println(stu3)
  println(stu4)

}
