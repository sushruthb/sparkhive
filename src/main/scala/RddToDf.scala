import org.apache.spark.sql.SparkSession

object RddToDf {
  def main(args: Array[String]) {

    val logFile = "/Users/I504285/spark-3.3.0-bin-hadoop3/examples/src/main/resources/people.txt" // Should be some file on your system
    val spark = SparkSession.builder.appName("RDDTODFExample").master("local[*]").getOrCreate()
    import spark.implicits._
    val peopleDF = spark.sparkContext
      .textFile(logFile)
      .map(_.split(","))
      .map(attributes => Person(attributes(0), attributes(1).trim.toInt))
      .toDF()
    peopleDF.show()

    // Register the DataFrame as a temporary view
    peopleDF.createOrReplaceTempView("people")

    // SQL statements can be run by using the sql methods provided by Spark
    val teenagersDF = spark.sql("SELECT name, age FROM people WHERE age BETWEEN 13 AND 19")

    // The columns of a row in the result can be accessed by field index
    teenagersDF.map(teenager => "Name: " + teenager(0)).show()
    // or by field name
    teenagersDF.map(teenager => "Name: " + teenager.getAs[String]("name")).show()

    // No pre-defined encoders for Dataset[Map[K,V]], define explicitly
    implicit val mapEncoder = org.apache.spark.sql.Encoders.kryo[Map[String, Any]]

    // row.getValuesMap[T] retrieves multiple columns at once into a Map[String, T]
    teenagersDF.map(teenager => teenager.getValuesMap[Any](List("name", "age"))).collect()



  }


}
