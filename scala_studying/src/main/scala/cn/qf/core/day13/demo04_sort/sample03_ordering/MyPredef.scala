package cn.qf.core.day13.demo04_sort.sample03_ordering

/**
 * Description：隐式的实例<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年11月05日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object MyPredef {
  implicit val girlCompInstance = new Ordering[Girl] {

    /**
     * 比较规则
     * 规律:
     *    降序:  第二个参数书写在前
     *    升序:  第一个参数书写在前
     *      颜值:  降序
     *      age: 升序
     *      height:  降序
     *      weight:  升序
     * @param
     * @param
     * @return
     */
    override def compare(x: Girl, y: Girl): Int = {
      val faceComp = (y.faceValue * 100 - x.faceValue * 100).toInt
      if (faceComp == 0) {
        val ageComp = x.age - y.age
        if (ageComp == 0) {
          val heightCom = (y.height * 100 - x.height * 100).toInt
          if (heightCom == 0 ) {
            (x.weight * 100 - y.weight * 100).toInt
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
}
