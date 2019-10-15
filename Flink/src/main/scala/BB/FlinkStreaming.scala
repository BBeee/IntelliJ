package BB

import org.apache.flink.api.common.typeinfo.TypeInformation
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.table.api.{TableEnvironment, Types}
import org.apache.flink.table.sources.CsvTableSource
import org.apache.flink.types.Row

object FlinkStreaming extends App {
  //  Simple Test using Socket (nc -kl 9900 in terminal)
    val sEnv = StreamExecutionEnvironment.getExecutionEnvironment
  //  val dStream : DataStream[String] = sEnv.socketTextStream("localhost",9900)
  //
  //  dStream.filter(_.length>10).map("Greater than "+_).print()
  //  dStream.filter(_.length<=10).map("S10> "+_).print()
  //
  //  sEnv.execute("Flink Test")

  // From streaming to Table
  // set up execution environment
  val env = StreamExecutionEnvironment.getExecutionEnvironment
  val tEnv = TableEnvironment.getTableEnvironment(env)

  // configure table source
  val customerSource = CsvTableSource.builder()
    .path("/home/hadoop/test.csv")
    .ignoreFirstLine()
    .fieldDelimiter(",")
    .field("id", Types.INT)
    .field("name", Types.STRING)
    .field("age", Types.INT)
    .field("description", Types.STRING)
    .build()

  // name your table source
  tEnv.registerTableSource("people", customerSource)

  // define your table program
  val table = tEnv
    .scan("people").filter( "age > 20").select("*")

  // convert it to a data stream
  implicit val typeInfo = TypeInformation.of(classOf[Row])
  val ds = tEnv.toAppendStream(table)

  ds.print()
  env.execute()
}
