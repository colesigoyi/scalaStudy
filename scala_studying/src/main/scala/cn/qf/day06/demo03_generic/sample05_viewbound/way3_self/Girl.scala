package cn.qf.day06.demo03_generic.sample05_viewbound.way3_self

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月28日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */

case class Girl(name:String, faceValue:Double, age:Int, height:Double) extends Ordered[Girl]{
  /**
   * 重写compare方法时,有规律:
   * 若是升序:this至于前,
   * 若是降序:that置于前
   * @param that
   * @return
   */
  override def compare(that: Girl): Int = {
    val faceValueComp = (this.faceValue - that.faceValue).toInt
    if (faceValueComp == 0) {
      val ageComp = (that.age - this.age).toInt
      if(ageComp ==0) {
        return (this.height - that.height).toInt
      }else {
        return ageComp
      }
    }else {
      return faceValueComp
    }
  }
}
