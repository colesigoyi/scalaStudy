package cn.qf.day06.demo02_implicit.sample01_class.a_common

import java.io.File

import scala.io.Source


/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月28日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object CommonUsageDemo {
  //隐式类
  implicit class RichFile(f:File) {
    /**
     * 读取特定文件的内容,并以字符串的形式返回
     * @return
     */
    def read = Source.fromFile(f).mkString
  }
//  implicit def read2 = Source.fromFile(f:File).mkString

  def main(args: Array[String]): Unit = {
    //上述的隐式类RichFile的主构造器需要一个File类型的参数
    //恰好此处准备了一个File类型的实例,此时就会自动将下述的File饮食转换成RichFile
    val fileContent = new File("log/info.txt").read
    println(fileContent)
//    val fileContent2 = new File("log/info.txt").read2
//    println(fileContent2)
  }
}
