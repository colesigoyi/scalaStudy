package cn.qf.day06.demo03_generic.sample01_xiebian


/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月28日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object XieBianDemo {
  def main(args: Array[String]): Unit = {
    val cats:List[Cat] = List(Cat("小花猫"),Cat("小白猫"))
    introduce(cats)
  }
  def introduce(animal:List[Animal]) = println(animal)
}
