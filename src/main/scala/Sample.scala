import org.apache.spark.sql.SparkSession

object Sample {

  def main(args: Array[String]) {
    val spark: SparkSession = SparkSession.builder()
      .master("local").appName("SparkByExamples.com")
      .getOrCreate()

    val columns = Seq("language","users_count")
    import spark.implicits._
    val data = Seq(("Java", "20000"), ("Python", "100000"), ("Scala", "3000"))

    val rdd = spark.sparkContext.parallelize(data)

    val dfFromRDD1 = rdd.toDF()
    dfFromRDD1.printSchema()




  }

}
