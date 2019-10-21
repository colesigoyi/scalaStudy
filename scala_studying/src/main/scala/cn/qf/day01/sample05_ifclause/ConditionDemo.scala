package cn.qf.day01.sample05_ifclause

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月21日
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object IfClauseDemo {
  def main(args: Array[String]): Unit = {
    var x = 1
    val y = if (x > 2) "你好" else x
    println(y)

    println("---------------------")

    val z = if (x > 0) "success" else -1
    println(z)

    println("---------------------")

    //下述的result若指定类型,只能是Any
    //既不能是String,也不能是Boolean
    //上述的类型共同的父类时Any
    var result: Any = if (x > 2) {
      println("hello")
      "你好"
    } else
      true
    println(s"结果是:$result")

    println("---------------------")

    //if多分支语句
    var result2: Any = if (x > 2) {
      println("hello")
      "你好"
    } else if (x == 2) {
      3
    } else
    true
    println(s"结果是:$result2")
  }
}
