package com.spark.bigdata

import com.spark.bigdata.util.ConfigUtil
import org.apache.spark.SparkConf

/**
  *
  */
package object es {
  import net.ceedubs.ficus.Ficus._
  import net.ceedubs.ficus.readers.ArbitraryTypeReader._

  private lazy val config = ConfigUtil.readClassPathConfig[EsConf]("es", "config")

  implicit class EsConfig(conf: SparkConf) {

    def build(index: String, typeTable: String, mapId: String): SparkConf = {
      conf
        //        .set("spark.buffer.pageSize", "8m")
        .set("es.nodes", config.nodes)
        .set("es.port", config.port)
        .set("es.scroll.size", "2000")
        .set("es.resource", s"$index/$typeTable")
        .set("es.index.auto.create", "true")
        .set("es.write.operation", "upsert")
        .set("es.mapping.id", s"$mapId")
    }

  }

}

case class EsConf(nodes: String, port: String)



