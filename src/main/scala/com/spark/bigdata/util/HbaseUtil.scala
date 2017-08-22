package com.spark.bigdata.util

import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.hadoop.hbase.client.{Connection, ConnectionFactory, Put}
import net.ceedubs.ficus.Ficus._
import net.ceedubs.ficus.readers.ArbitraryTypeReader._

object HbaseUtil {

  lazy val config=ConfigUtil.readClassPathConfig[HbaseConfig]("Hbase","conifg")

  def hbaseConnection(): Connection={
    val hbaseConf = HBaseConfiguration.create()
    hbaseConf.set("hbase.zookeeper.quorum", config.zkList)
    hbaseConf.set("hbase.zookeeper.property.clientPort", config.zkPort)
    val hbaseConn = ConnectionFactory.createConnection(hbaseConf)
    hbaseConn
  }


  case class HbaseConfig(zkList:String,zkPort:String="2181")


  def main(args: Array[String]): Unit = {
    println(config.zkList)
  }

}
