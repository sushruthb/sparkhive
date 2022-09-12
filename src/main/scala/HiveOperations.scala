import org.apache.spark.sql.SparkSession

object HiveOperations {
  def main(args: Array[String]): Unit ={

    val spark=SparkSession.builder().appName("Hive Ops").enableHiveSupport().getOrCreate()

    val dbName="default"

    val tableName="test"
    spark.sql("use "+dbName )
    spark.sql("SET hive.exec.dynamic.partition=true")
    spark.sql("SET hive.exec.dynamic.partition.mode=nonstrict")
    spark.sql("SET hive.exec.max.dynamic.partitions.pernode=400")

    val sqlString="create table if not exists " + dbName + "." + tableName + "(empid String,ename String, designation String,dept String)"
    spark.sql(sqlString);

    spark.close()
  }

}
