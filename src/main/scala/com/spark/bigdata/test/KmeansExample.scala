package com.spark.bigdata.test

import org.apache.spark.mllib.clustering.KMeans
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.sql.SparkSession

object KmeansExample {

  def main(args: Array[String]): Unit = {
    // val spark = SparkSession.builder().master("spark://master:7077").appName("test").getOrCreate()
    val spark = SparkSession.builder().master("local[*]").appName("test").getOrCreate()
    val sc = spark.sparkContext
    val kData = sc.makeRDD(Seq(
      (50, 50, 9),
      (28, 9, 4),
      (17, 15, 3),
      (25, 40, 5),
      (28, 40, 2),
      (50, 50, 1),
      (50, 40, 9),
      (50, 40, 9),
      (40, 40, 5),
      (50, 50, 9),
      (50, 50, 5),
      (50, 50, 9),
      (40, 40, 9),
      (40, 32, 1),
      (50, 50, 9)))
    val allData = kData.map(x => Vectors.dense(x._1.toDouble, x._2.toDouble, x._3.toDouble))
    allData.cache()
    //kMeansModel 里面含参数 数据，K，maxIterations
    val kMeansModel = KMeans.train(allData, 3, 10)
    // kMeansModel.save(sc,"")
    kMeansModel.clusterCenters.foreach(x => println(x.toString.mkString(",")))
  }
}
