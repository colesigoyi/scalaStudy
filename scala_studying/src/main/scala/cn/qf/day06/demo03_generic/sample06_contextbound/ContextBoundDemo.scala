package cn.qf.day06.demo03_generic.sample06_contextbound

/**
 * Description：上下文界定<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月28日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object ContextBoundDemo {
  def main(args: Array[String]): Unit = {
    //情形1:使用String测试,用String代替泛型参数T
    val smallerStr = getSamaller("hadoop", "spark")
    println(s"更小的字符是:$smallerStr")
    //说明:上述能够正常运行,说明存在着Ordering[String的隐式值]
    println("-----------------------------")
    //情形2:使用Int测试,用Int代替泛型参数T
    val smallerInt = getSamaller(11, 22)
    println(s"更小的数字是:$smallerInt")
  }
  def getSamaller[T : Ordering](first:T, second:T)(implicit od:Ordering[T]) = {
    if (od.compare(first, second) < 0) first else second
  }
}
