package cn.qf.day05.demo04_interpolation

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月25日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object StringInterpolationDemo {
  def main(args: Array[String]): Unit = {
    val zs,(name, age, height) = ("张学友",38,175.8)
    println(s"名字是:$name,年龄:$age,身高:$height")//名字是:张学友,年龄:38,身高:175.8
    println(s"名字是:${zs._1},年龄:${zs._2},身高:${zs._3}")//名字是:张学友,年龄:38,身高:175.8
    println(f"名字是:$name%5s,年龄:$age%4d,身高:$height%4.2f")//名字是:  张学友,年龄:  38,身高:175.80
    println(raw"名字是:$name\t,年龄:$age\t,身高:$height\n")//名字是:张学友\t,年龄:38\t,身高:175.8\n
  }
}
