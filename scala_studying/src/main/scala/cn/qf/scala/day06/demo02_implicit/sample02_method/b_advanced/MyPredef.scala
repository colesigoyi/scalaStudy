package cn.qf.scala.day06.demo02_implicit.sample02_method.b_advanced

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月28日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object MyPredef {
  /**
   * 将Double转换成Int
   * @param num
   * @return
   */
  implicit def double2int(num:Double) = num.toInt
}
