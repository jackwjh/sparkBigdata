package com.spark.bigdata

import java.io.File

import com.spark.bigdata.db.{JDBCConfig, JdbcUtil}
import com.spark.bigdata.util.ConfigUtil
import com.typesafe.config.{Config, ConfigFactory}
import org.apache.spark._
import org.apache.spark.sql.{DataFrame, Dataset, SQLContext, SparkSession}
import scala.collection.JavaConverters._
/**
  *
  */
object Test {





  def main(args: Array[String]): Unit = {

    val spark=SparkSession.builder().appName("test").master("spark://master:7077").getOrCreate()
    import spark.implicits._

    val df=Seq(("1",1)).toDF("name","age")

    df.show()





    spark.stop()
  }



  def save2Mysql(df: DataFrame, table: String) = {
    JdbcUtil.save2Mysql(table)(df)
  }
  case class RedisConfig(host: String, port: String = "6379", db: String = "1", auth: Option[String] = None)
  case class Stu(name:String,age:Int)
}
