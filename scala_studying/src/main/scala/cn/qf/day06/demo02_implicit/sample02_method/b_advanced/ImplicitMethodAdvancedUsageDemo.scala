package cn.qf.day06.demo02_implicit.sample02_method.b_advanced

/**
 * Description：隐式方法在真实项目中使用方式演示<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月28日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object ImplicitMethodAdvancedUsageDemo {
  def main(args: Array[String]): Unit = {
    //导入单例类中的一些隐式方法
    //import MyPredef.double2int
    import MyPredef._
    val result:Int = 89.88
    println(result)
  }
}
