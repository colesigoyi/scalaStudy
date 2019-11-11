package cn.qf.scala.day06.demo03_generic.sample03_upbound

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月28日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object UpBoundDemo {
  def main(args: Array[String]): Unit = {
    //情形1:传入Pet的子类型
    introducePet[Cat](Cat("大花猫"))
    println("---------------------------")
    //情形2:传入非Pet的类型
    //下述的代码深度编译时报错,因为Lion不是Pet的子类型
    //introducePet[Lion](Lion("大狮子"))
    /*
    Error:(18, 17) type arguments [cn.qf.scala.day06.demo03_generic.sample03_upbound.Lion]
    do not conform to method introducePet's type parameter bounds
    [T <: cn.qf.scala.day06.demo03_generic.sample03_upbound.Pet]
    introducePet[Lion](Lion("大狮子"))
     */
  }

  /**
   * 介绍Pet
   * @param t
   * @tparam T  T <: Pet,类型T的父类型是Pet
   */
  def introducePet[T <: Pet](t:T) = println(s"Pet的信息是:$t")
}
