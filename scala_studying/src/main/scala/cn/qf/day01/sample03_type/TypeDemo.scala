package cn.qf.day01.sample03_type

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月21日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object TypeDemo {
  def main(args: Array[String]): Unit = {
    //类型会自动提升
    //Byte,Short,Char -> Int -> Long -> Float -> Double
    val distance:Long = 789999  //Int -> Long
    val distanceFloat:Float = distance //Long -> Float
    println(s"Int类型 = ${distance},Float类型= ${distanceFloat}")

    val smileFace:Char = '☺'
    val smileInt:Int = smileFace  //Char -> Int
    println(s"字符${smileFace}在计算机底层二进制对应的十进制整数是: ${smileInt}")
  }
}
