package com.spark.bigdata.test

import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.catalyst.expressions.Round
import scala.math._

object logistic {


  def main(args: Array[String]): Unit = {


    val spark = SparkSession.builder().master("spark://master:7077").appName("test").getOrCreate()
    /*  import spark.implicits._
    val sqlDF=List(("")).toDF("")
    val colArray2 = Array("gender", "age", "yearsmarried", "children", "religiousness", "education", "occupation", "rating")
    val vecDF = new VectorAssembler().setInputCols(colArray2).setOutputCol("features").transform(sqlDF)
    //测试数据和训练数据分开
    val Array(trainingDF, testDF) = vecDF.randomSplit(Array(0.9, 0.1), seed = 12345)
    val lrModel = new LogisticRegression().setLabelCol("affairs").setFeaturesCol("features").fit(trainingDF)
    lrModel.transform(testDF).show()
    // 输出逻辑回归的系数和截距
    println(s"Coefficients: ${lrModel.coefficients} Intercept: ${lrModel.intercept}")*/

    val weights = Seq(0.308688148697453, -0.04150802586369178, 0.08771801000466706, 0.6896853841812993, -0.3425440049065515, 0.008629892776596084, 0.0458687806620022, -0.46268114569065383)

    spark.udf.register("preducation", (feature: Seq[Double]) => {
      feature.size match {
        case 8 => math.exp(
          feature(0) * weights(0)
            + feature(1) * weights(0)
            + feature(2) * weights(1)
            + feature(3) * weights(2)
            + feature(4) * weights(3)
            + feature(5) * weights(4)
            + feature(6) * weights(5)
            + feature(7) * weights(6)
            + feature(8) * weights(7)
        )
        case _ => None
      }
    })


    val test = Seq(0.0, 22.0, 0.417, 0.0, 5.0, 14.0, 1.0, 4.0)
    val logtiPrediction = for (i <- weights; y <- test) yield (i, y, i * y)

    println(1.263200227888706 + 0.308688148697453 * 0 + (-0.04150802586369178 * 22.0) + (0.08771801000466706 * 0.417) + (0.6896853841812993 * 0.0) + (-0.3425440049065515 * 5.0) + (0.008629892776596084 * 14.0) + (0.0458687806620022 * 1.0) + (-0.46268114569065383 * 4.0))
    println(math.exp(-3.0101552587015927))

    spark.stop()
  }
}


