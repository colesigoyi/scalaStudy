package cn.qf.day02.sample01_function.demo04_method2fun

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月22日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object Method2FunctionDemo {
    def main(args: Array[String]): Unit = {
      val result = cal(1, 2, add) //调用时,内部已经将方法add隐式转换成函数
      println(s"result = $result")
      println(s"result = ${cal(1, 2, add _)}")//调用时,利用下划线显式的将方法add隐式转换成函数
  }
    def add(x:Int, y:Double) = x + y

    def cal(x:Int, y:Double, z:(Int, Double) => Double) = z(x, y)
}
