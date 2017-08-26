package com.spark.bigdata.test

import org.apache.spark.mllib.tree.DecisionTree
import org.apache.spark.mllib.util.MLUtils
import org.apache.spark.sql.SparkSession

object DecisionTreeExample {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().master("local[3]").appName("a").getOrCreate()
    val data = MLUtils.loadLibSVMFile(spark.sparkContext, "")
    val splits = data.randomSplit(Array(0.7, 0.3))
    val (traingData, testData) = (splits(0), splits(1))
    val numClasses = 5 //***********************分类数目
    val categoricalFeaturesInfo = Map[Int, Int]()
    //设定输入数据的格式
    val impurity = "gini" //设定信息增益计算方式，这里采用gini不纯度
    val maxDepth = 5 //设定树的高度
    val maxBins = 32 //设定分裂数据集
    val mode = DecisionTree.trainClassifier(traingData, numClasses, categoricalFeaturesInfo, impurity, maxBins, maxBins)
    val testAndPre = testData.map(point => {
      val pre = mode.predict(point.features)
      (point.label, pre)
    })
    //預測錯誤率
    val testErr = testAndPre.filter(x => x._1 != x._2).count().toDouble / testData.count().toDouble
    println(s"the error is ${testErr}")
    spark.stop()
  }
}
