package cn.qf.day06.demo02_implicit.sample01_class.b_advanced

import java.io.File
//import MyPredef._
/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月28日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object AdvancedUsageDemo {
  def main(args: Array[String]): Unit = {
    //导入隐式类
    //方式1:全局导入,在顶级类上面导入

    //方式2:局部导入,在方法体或是函数体单独导入
    //隐式类定义在单例类中的导入方式
    //导入方式A:
    import MyPredef.RichFile
    //隐式列定义在普通类中的导入用法
    //导入方式B:
    //val instance = new MyPredef
    //import instance.RichFile
    val fileContent = new File("").read
    println(fileContent)
  }
}
