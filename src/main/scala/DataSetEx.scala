import org.apache.spark.sql.SparkSession

case class Person(name: String, age: Long)

object DataSetEx {

  def main(args: Array[String]) {
    val logFile = "/Users/I504285/spark-3.3.0-bin-hadoop3/examples/src/main/resources/people.json" // Should be some file on your system
    val spark = SparkSession.builder.appName("JsonExample").master("local[*]").getOrCreate()
    import spark.implicits._
    val caseClassDS = Seq(Person("Andy", 32)).toDS()
    caseClassDS.show()

    val peopleDS = spark.read.json(logFile).as[Person]
    peopleDS.show()
  }
}
