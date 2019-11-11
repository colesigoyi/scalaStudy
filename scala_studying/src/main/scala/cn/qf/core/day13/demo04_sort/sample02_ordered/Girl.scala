package cn.qf.core.day13.demo04_sort.sample02_ordered

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年11月05日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */

case class Girl(name: String, faceValue: Double,age:Int, height:Double, weight:Double) extends Ordered[Girl] {
  /**
   * 比较规则
   * 规律:
   *    降序:  that-this
   *    升序:  this-that
   *      颜值:  降序
   *      age: 升序
   *      height:  降序
   *      weight:  升序
   * @param that
   * @return
   */
  override def compare(that: Girl): Int = {
    val faceComp = (that.faceValue * 100 - this.faceValue * 100).toInt
    if (faceComp == 0) {
      val ageComp = this.age - that.age
      if (ageComp == 0) {
        val heightCom = (that.height * 100 - this.height * 100).toInt
        if (heightCom == 0 ) {
          (this.weight * 100 - that.weight * 100).toInt
        }else {
          heightCom
        }
      }else {
        ageComp
      }
    }else {
      faceComp
    }
  }
}
