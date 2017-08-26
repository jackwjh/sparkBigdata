package com.spark.bigdata.test

import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.sql.SparkSession

object LogisticRegressionExample {
  def main(args: Array[String]): Unit = {
    // val spark = SparkSession.builder().master("spark://master:7077").appName("test").getOrCreate()
    val spark = SparkSession.builder().master("local[3]").appName("test").getOrCreate()
    import spark.implicits._
    val sqlDF = List(("")).toDF("")
    val colArray2 = Array("gender", "age", "yearsmarried", "children", "religiousness", "education", "occupation", "rating")
    val vecDF = new VectorAssembler().setInputCols(colArray2).setOutputCol("features").transform(sqlDF)
    //测试数据和训练数据分开
    val Array(trainingDF, testDF) = vecDF.randomSplit(Array(0.9, 0.1), seed = 12345)
    val lrModel = new LogisticRegression().setLabelCol("affairs").setFeaturesCol("features").fit(trainingDF)
    lrModel.transform(testDF).show()
    // 输出逻辑回归的系数和截距
    println(s"Coefficients: ${lrModel.coefficients} Intercept: ${lrModel.intercept}")
    lrModel.summary
    val weights = Seq(0.308688148697453, -0.04150802586369178, 0.08771801000466706, 0.6896853841812993, -0.3425440049065515, 0.008629892776596084, 0.0458687806620022, -0.46268114569065383)
    spark.udf.register("preducation", (feature: Seq[Double]) => {
      feature.size match {
        case 8 => 1 / 1 + math.exp(
          feature(0) * weights(0)
            + feature(1) * weights(1)
            + feature(2) * weights(2)
            + feature(3) * weights(3)
            + feature(4) * weights(4)
            + feature(5) * weights(5)
            + feature(6) * weights(6)
            + feature(7) * weights(7)
        )
        case _ => None
      }
    })
    val test = Seq(1.0, 57.0, 15.0, 1.0, 2.0, 14.0, 7.0, 2.0)
    val logtiPrediction = for (i <- weights; y <- test) yield (i, y, i * y)
    val w = 1.263200227888706 + 0.308688148697453 * test(0) + (-0.04150802586369178 * test(1)) + (0.08771801000466706 * test(2)) + (0.6896853841812993 * test(3)) + (-0.3425440049065515 * test(4)) + (0.008629892776596084 * test(5)) + (0.0458687806620022 * test(6)) + (-0.46268114569065383 * test(7))
    println(1 / (1 + math.exp(w)))
    spark.stop()
  }
}


