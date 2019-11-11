package cn.qf.scala.day06.demo03_generic.sample05_viewbound.way3_self

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月28日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object SelfViewBoundDemo {
  def main(args: Array[String]): Unit = {
    val girl = getMoreBeautiful(Girl("貂蝉",99,19,177),Girl("西施",99,19,179))
    println(s"更美的是:$girl")
  }
  def getMoreBeautiful[T <% Ordered[T]](first:T, second:T): T = {
    if(first > second) first else second
  }
}
