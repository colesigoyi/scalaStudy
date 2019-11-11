package cn.qf.scala.day04.sample03_object.demo03_unapply

/**
 * Description：提取器<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月24日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object UnapplyDemo {
  def main(args: Array[String]): Unit = {
    val empororInfo:String = Emporor("康熙","清朝")
    empororInfo match{
      case Emporor(name) => println(name) //调用unapply方法
      case _ => println("没有匹配上")
    }
  }
}
