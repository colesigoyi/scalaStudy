package cn.qf.scala.day01.sample02_varandval

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月21日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object VarAndValDemo2 {
  def main(args: Array[String]): Unit = {
    //构建Pet的实例
    val pet = new Pet(5)
    //pet = new Pet(6)  //pet是常量,只能赋值一次
    //pet.animal = new Animal(3)  //pet实例的属性animal是常量,只能赋值一次
    pet.animal.globalAge = 8  //变量可以多次赋值
  }
}

class Animal(age : Int){
  var globalAge = age
}
class Pet(age : Int){
  val animal = new Animal(age)
}