package cn.qf.day10.demo01_transformation

import cn.qf.util.SparkUtil
import org.apache.spark.{HashPartitioner, SparkContext}
import org.apache.spark.rdd.RDD

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年11月01日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object CommonTransformationDemo {

  def main(args: Array[String]): Unit = {
    val spark = SparkUtil.getSparkSession(CommonTransformationDemo.getClass.getName,"local[*]")
    val sc = spark.sparkContext

    println("----------------repartition--------------")
    //repartition:RDD经过filter操作后,各个分区的数据量可能相差较大,容易引起数据倾斜,此时,
    // 可以filter之后进行repartion,可以让各个分区的数据大体保持一致
    repartitionDO(sc)//repartion演示

    println("----------------coalesce--------------")
    //coalesce:被repartition调用,与repartition的通一致
    coalesceDO(sc)

    println("----------------partitionBy-------------")
    //partitionBy:用来定制分区器的,分区器:就是用来执行元素到底存储在rdd的哪个分区中,用来定制规则
    partitionByDO(sc)


    println("----------------repartitionAndSortWithinPartitions-------------")
    //repartitionAndSortWithinPartitions
    repartitionAndSortWithinPartitionsDO(sc)

    println("----------------reduceByKey-------------")
    //reduceByKey:内部计算时分成两部分,先进性局部聚合,在进行全局聚合,与groupByKey效率更高
    sc.parallelize(Seq(("jack",1),("marry",2),("lucycat",18),("lucycat",19)))
        .reduceByKey(_ + _)
        .foreach(println)
    /*
    (jack,1)
    (lucycat,37)
    (marry,2)
     */
    println("----------------aggregateByKey-------------")
    //aggregateByKey:
    // 有两个参数
    //第一个参数列表中有一个参数,用来存储初始值
    //第二个参数列表中有两个参数,第一个参数用于进行相应的分区中的局部操作,第二个参数用于全局操作(基于第一个参数的结果进行计算)
    //初始值:参与第二个参数列表中第一个参数针对特定分区计算后的结果,再根据计算规则(max和_)进行计算
          //                  不参与第二个形参列表中第二个参数的运算
    aggregateByKeyDO(sc)


    //资源释放
    spark.stop()
  }


  private def coalesceDO(sc: SparkContext): Unit = {
    val rdd = sc.parallelize(Seq(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16), 3)
    val beforRDD = rdd.filter(_ % 2 == 0)
    println("----------------coalesce过滤之前--------------")
    //推导:各个元素所在的分区编号变化的情形
    showRDDMemberDetailInfo(beforRDD)
    /*
    当前的元素是:12 所在的分区编号是:2
    当前的元素是:6 所在的分区编号是:1
    当前的元素是:14 所在的分区编号是:2
    当前的元素是:8 所在的分区编号是:1
    当前的元素是:16 所在的分区编号是:2
    当前的元素是:10 所在的分区编号是:1
    当前的元素是:2 所在的分区编号是:0
    当前的元素是:4 所在的分区编号是:0
     */

    println("----------------coalesce过滤之后--------------")
    val afterRDD = rdd.filter(_ % 2 == 0)
      .coalesce(2,shuffle = true)
    showRDDMemberDetailInfo(afterRDD)
    /*
    当前的元素是:2 所在的分区编号是:0
    当前的元素是:6 所在的分区编号是:0
    当前的元素是:10 所在的分区编号是:0
    当前的元素是:12 所在的分区编号是:0
    当前的元素是:4 所在的分区编号是:1
    当前的元素是:16 所在的分区编号是:0
    当前的元素是:8 所在的分区编号是:1
    当前的元素是:14 所在的分区编号是:1
     */
  }

  private def repartitionDO(sc: SparkContext): Unit = {
    val rdd = sc.parallelize(Seq(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16), 3)
    val beforRDD = rdd.filter(_ % 2 == 0)
    println("----------------repartition过滤之前--------------")
    //推导:各个元素所在的分区编号变化的情形
    showRDDMemberDetailInfo(beforRDD)
    /*
    当前的元素是:2 所在的分区编号是:0
    当前的元素是:12 所在的分区编号是:2
    当前的元素是:6 所在的分区编号是:1
    当前的元素是:14 所在的分区编号是:2
    当前的元素是:4 所在的分区编号是:0
    当前的元素是:16 所在的分区编号是:2
    当前的元素是:8 所在的分区编号是:1
    当前的元素是:10 所在的分区编号是:1
     */

    println("----------------repartition过滤之后--------------")
    val afterRDD = rdd.filter(_ % 2 == 0)
      .repartition(2)
    showRDDMemberDetailInfo(afterRDD)
    /*
    当前的元素是:4 所在的分区编号是:1
    当前的元素是:2 所在的分区编号是:0
    当前的元素是:8 所在的分区编号是:1
    当前的元素是:6 所在的分区编号是:0
    当前的元素是:10 所在的分区编号是:0
    当前的元素是:14 所在的分区编号是:1
    当前的元素是:12 所在的分区编号是:0
    当前的元素是:16 所在的分区编号是:0
     */
  }

  private def partitionByDO(sc: SparkContext) = {
    val rdd = sc.parallelize(Seq(("jack",17),("marry",18),("lucy",20)), 3)
    println("----------------定制分区器之前.rdd元素存储详情--------------")
    val beforeRDD = rdd
    showRDDMemberDetailInfo2(beforeRDD)
    /*
    当前的元素是:(lucy,20) 所在的分区编号是:2
    当前的元素是:(jack,17) 所在的分区编号是:0
    当前的元素是:(marry,18) 所在的分区编号是:1
     */
    println("----------------定制分区器之后.rdd元素存储详情--------------")
    val afterRDD = rdd.partitionBy(new HashPartitioner(2))
    showRDDMemberDetailInfo2(afterRDD)
    /*
    当前的元素是:(jack,17) 所在的分区编号是:1
    当前的元素是:(marry,18) 所在的分区编号是:1
    当前的元素是:(lucy,20) 所在的分区编号是:1
     */
  }
  private def repartitionAndSortWithinPartitionsDO(sc: SparkContext): Unit = {
    val rdd: Unit = sc.parallelize(Seq((1,"jack"),(2,"marry"),(2,"lucy")), 3)
      .repartitionAndSortWithinPartitions(new HashPartitioner(1))
      .foreach(println)
    /*
    (1,jack)
    (2,marry)
    (2,lucy)
     */
  }
  private def aggregateByKeyDO(sc: SparkContext): Unit = {
    val rdd = sc.parallelize(List(("cat",2),("cat", 6),("dog",3),("dog", 4),("cat", 4),("pig",8)), 2)
    val result = rdd.aggregateByKey(5)(math.max,_ + _)
      result.foreach(println)

    //显示rdd中每个元素的所属的分区
    showRDDMemberDetailInfo2(rdd)
    println("--------结果所在分区-------")
    showRDDMemberDetailInfo2(result)

    //初始值是0
    /*
    当前的元素是:(cat,2) 所在的分区编号是:0
    当前的元素是:(cat,6) 所在的分区编号是:0
    当前的元素是:(dog,3) 所在的分区编号是:0 调用math.max(_,_) 第一个分区中的结果是:(cat,6)(dog,3)
    当前的元素是:(dog,4) 所在的分区编号是:1
    当前的元素是:(cat,4) 所在的分区编号是:1
    当前的元素是:(pig,8) 所在的分区编号是:1 调用math.max(_,_) 第二个分区中的结果是:(cat,4)(dog,4),(pig,8)
     */

    //调用math.max(_,_) 第一个分区中的结果是:(cat,6)(dog,3)
    //调用math.max(_,_) 第二个分区中的结果是:(cat,4)(dog,4),(pig,8)

    //_ + _:将相同的key相加
    //    (dog,7)
    //    (pig,8)
    //    (cat,10)

    //最终结果
    /*
    (dog,7)
    (pig,8)
    (cat,10)
     */

    //初始值是5
    /*
    当前的元素是:(cat,2) 所在的分区编号是:0
    当前的元素是:(cat,6) 所在的分区编号是:0
    当前的元素是:(dog,3) 所在的分区编号是:0 调用math.max(_,_) 第一个分区中的结果是:(cat,6)(dog,5)
    当前的元素是:(pig,8) 所在的分区编号是:1
    当前的元素是:(dog,4) 所在的分区编号是:1
    当前的元素是:(cat,4) 所在的分区编号是:1 调用math.max(_,_) 第二个分区中的结果是:(cat,5)(dog,5),(pig,8)

     */

    //调用math.max(_,_) 第一个分区中的结果是:(cat,6)(dog,5)
    //调用math.max(_,_) 第二个分区中的结果是:(cat,5)(dog,5),(pig,8)

    //_ + _:将相同的key相加
//    (dog,10)
//    (pig,8)
//    (cat,11)

    //最终结果
    /*
    (dog,10)
    (pig,8)
    (cat,11)
     */
  }

  private def showRDDMemberDetailInfo(rdd: RDD[Int]): Unit = {
    rdd.mapPartitionsWithIndex((index, itr) => itr.map(perEle => s"当前的元素是:$perEle 所在的分区编号是:$index"))
      .foreach(println)
  }
  private def showRDDMemberDetailInfo2(rdd: RDD[(String, Int)]): Unit = {
    rdd.mapPartitionsWithIndex((index, itr) => itr.map(perEle => s"当前的元素是:$perEle 所在的分区编号是:$index"))
      .foreach(println)
  }

}
