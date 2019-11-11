package cn.qf.core.day13.demo04_sort.sample03_ordering

import cn.qf.util.SparkUtil

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年11月05日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object OrderingDemo {
  def main(args: Array[String]): Unit = {
    val spark = SparkUtil.getSparkSession(OrderingDemo.getClass.getName,"local[*]")
    val sc = spark.sparkContext
    //导入隐式实例
    import MyPredef.girlCompInstance

    sc.parallelize(Seq(Girl("marry", 80.9, 18, 174.6, 50.3),
      Girl("lucycat",98.9, 20, 179.1, 48.1),
      Girl("Angle",80.9, 21, 172.1, 56.8),
      Girl("kate",80.9, 21, 172.1, 56.1),
      Girl("lilydo", 80.9, 21, 176.2, 51.5)))
      .sortBy(G => G,ascending = true, 1)
      .foreach(println)
      /*
      Girl(lucycat,98.9,20,179.1,48.1)
      Girl(marry,80.9,18,174.6,50.3)
      Girl(lilydo,80.9,21,176.2,51.5)
      Girl(kate,80.9,21,172.1,56.1)
      Girl(Angle,80.9,21,172.1,56.8)
       */
    /*
    sortBy第二个参数:
        true->根据定制的规则进行排列(降序)
        false→升序，恰好与规则相反(升序)
    注意点:
    第二个参数列表的第一个参数是隐式参数,类型是:Ordering[T]
    若是自定义排序之Ordering方式,需要导入一个隐式实例,该隐式实例是Ordering[T]特质的类型,重写了其中的Compare,在其中定制了比较规则
    该规则给方法sortBy用,将容器中的每个元素两两取出,调用该方法进行比较
     */
    spark.stop()
  }
}
