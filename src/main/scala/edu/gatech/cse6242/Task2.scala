package edu.gatech.cse6242

import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf

object Task2 {
  def main(args: Array[String]) {
    val sc = new SparkContext(new SparkConf().setAppName("Task2"))

    // read the file
    val file = sc.textFile("hdfs://localhost:8020" + args(0))

    /* TODO: Needs to be implemented */

    // read each line and select only the tgt and weight
    val tgt = file.map(line=> line.split("\t") match { case Array(x,y,z) => (y.toInt,z.toInt) } )

    // cache the rdd
    tgt.cache()

    // add the weights for each tgt edge and take only values > 0
    val edge = tgt.reduceByKey((a, b) => a + b).filter(_._2 > 0).map(x=>x._1+"\t"+x._2)

    // collect all data at the master
    edge.collect()

    // store output on given HDFS path.
    // YOU NEED TO CHANGE THIS
    edge.saveAsTextFile("hdfs://localhost:8020" + args(1))
  }
}
