import avro.generator.{Bootstrap}

object Main {
  def main(args: Array[String]) {
      val app = new Bootstrap()
      val classPath = args(0)
      val className = args(1)
      val outputFolder = args(2)
      val avroGeneratorTask = app.getGenerateAvroSchemaTask();
      avroGeneratorTask.accept(classPath, className, outputFolder)
  }
}