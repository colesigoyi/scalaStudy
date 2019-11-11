package cn.qf.scala.day05.demo09_methodpolysim

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月25日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object MethodPolySimDemo {
  def main(args: Array[String]): Unit = {
    println(s"初始化一个长度为5,初试值为ok的列表:${getSpecialList("ok", 5)}")
  }

  /**
   * 使用泛型方法,初试一个指定长度的列表
   */
  def getSpecialList[T](x:T,length:Int):List[T] = {
    if (length < 1)
      Nil
    else
      x :: getSpecialList(x, length - 1)
  }
}
