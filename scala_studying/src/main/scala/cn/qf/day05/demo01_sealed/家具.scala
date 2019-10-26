package cn.qf.day05.demo01_sealed

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月25日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */

sealed abstract class 家具 {

}
case class 椅子() extends 家具
case class 沙发() extends 家具