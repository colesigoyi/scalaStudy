package cn.qf.scala.day01.studybymyself

import scala.io.StdIn

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月21日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object GetLoopYear {
  def main(args: Array[String]): Unit = {
    print("请输入年份:")
    val year = StdIn.readInt()
    if (year % 3200 == 0) println(s"${year}不是闰年")
    else if(year % 400 == 0) println(s"${year}是闰年")
    else if(year % 100 == 0) println(s"${year}不是闰年")
    else if(year % 4 == 0) println(s"${year}是闰年")
    else println(s"${year}不是闰年")
  }
}
