import avro.generator.{Bootstrap}

object Main {
  def main(args: Array[String]) {
      val app = new Bootstrap()
      val classPath = args(0)
      val className = args(1)
      val avroOutputFolder = args(2)
      val outputFolder = args(3)
      val avroGeneratorTask = app.getGenerateAvroSchemaTask();
      avroGeneratorTask.accept(classPath, className, avroOutputFolder, outputFolder)
  }
}