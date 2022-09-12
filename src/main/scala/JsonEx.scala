import org.apache.spark.sql.SparkSession
object JsonEx {
  def main(args: Array[String]) {
    val logFile = "/Users/I504285/spark-3.3.0-bin-hadoop3/examples/src/main/resources/people.json" // Should be some file on your system
    val spark = SparkSession.builder.appName("JsonExample").master("local[*]").getOrCreate()
    val df = spark.read.json(logFile)
    df.show()

    import spark.implicits._

    // Print the schema in a tree format
    df.printSchema()


    // Select only the "name" column
    df.select("name").show()
    // Select everybody, but increment the age by 1
    df.select($"name", $"age" + 1).show()

    // Select people older than 21
    df.filter($"age" > 21).show()

    // Count people by age
    df.groupBy("age").count().show()

    // Register the DataFrame as a SQL temporary view
    df.createOrReplaceTempView("people")

    val sqlDF = spark.sql("SELECT * FROM people")
    sqlDF.show()

    // Register the DataFrame as a global temporary view
    df.createGlobalTempView("people")

    // Global temporary view is tied to a system preserved database `global_temp`
    spark.sql("SELECT * FROM global_temp.people").show()


    // Global temporary view is cross-session
    spark.newSession().sql("SELECT * FROM global_temp.people").show()


    spark.stop()
  }
}
