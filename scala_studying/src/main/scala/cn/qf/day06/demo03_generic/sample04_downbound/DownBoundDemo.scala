package cn.qf.day06.demo03_generic.sample04_downbound

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月28日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object DownBoundDemo {
  def main(args: Array[String]): Unit = {
    //情形1:传入Pet的父类型或者是Cat类型
    introducePet[Cat](Cat("大花猫"))
    println("---------------------------")
    //情形2:传入Pet的父类型或者是Cat类型
    introducePet[Pet](new Pet("小宠物"))
    println("---------------------------")
    //情形4:传入非Pet的父类型或者是非Cat类型
    //报错:Lion不是Cat的父类型
    //introducePet[Lion](Lion("大狮子"))
    /*
    Error:(18, 17) type arguments [cn.qf.day06.demo03_generic.sample04_downbound.Lion]
     do not conform to method introducePet's type parameter bounds
     [T >: cn.qf.day06.demo03_generic.sample04_downbound.Cat]
    introducePet[Lion](Lion("大狮子"))
     */
  }
  def introducePet[T >: Cat](t:T) = println(s"Pet的信息是:$t")
}
