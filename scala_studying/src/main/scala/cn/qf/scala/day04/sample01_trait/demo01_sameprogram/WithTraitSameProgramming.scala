package cn.qf.scala.day04.sample01_trait.demo01_sameprogram

import scala.collection.mutable.ArrayBuffer

/**
 * Description：使用特质统一编程 <br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月24日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */

object WithTraitSameProgramming {
  def main(args: Array[String]): Unit = {
    //使用集合演示
    val zoo = ArrayBuffer.empty[Pet]
    val cat = new Cat("小花猫")
    val dog = new Dog("小豆")
    zoo.append(cat)
    zoo.append(dog)
    println(s"动物园中有:\n${zoo}")
    introduce(cat)
    introduce(dog)

    /**
     * 介绍宠物
     * @param pet
     */
    def introduce(pet:Pet) = {
      println(pet)
    }
  }
}
