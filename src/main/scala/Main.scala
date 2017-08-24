import avro.generator.{Bootstrap}

object Main {
  def main(args: Array[String]) {
    val app = new Bootstrap().init();
    val className = args(0);
    val outputFolder = args(1)
    app.accept(className, outputFolder)
  }
}