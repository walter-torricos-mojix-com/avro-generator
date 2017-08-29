import avro.generator.Bootstrap
import sbt._
import sbt.Keys._
import sbt.complete.DefaultParsers._

object AvroGeneratorPlugin extends AutoPlugin {
    object autoImport {
        // Defines all settings/tasks that get automatically imported when the plugin is enabled.
        val avro = inputKey[Unit]("path")
        val avroHelp = inputKey[Unit]("path")
    }

    // make our own settings and commands available for the rest of the file
    import autoImport._

    // allow the plugin to be included automatically
    override def trigger: PluginTrigger = allRequirements

    private val app = new Bootstrap()

    //Provide default settings
    override lazy val projectSettings = Seq(
        avro := {
            val args: Seq[String] = spaceDelimited("").parsed
            val targetClassName = args(0)
            val avroFolderPath = args(1)
            val outputFolderPath = args(2)
            val classesFolder = (fullClasspath in Runtime).value.files
                .filter(c => c.getAbsolutePath().endsWith("classes"))
                .head
            val classPath = classesFolder.getAbsolutePath()
            val avroGeneratorTask = app.getGenerateAvroSchemaTask()
            avroGeneratorTask.accept(classPath, targetClassName, avroFolderPath, outputFolderPath)
        },
        avroHelp := {
            println("avro example: ")
            println("$ sbt \"avro complete.class.Name /path/to/avro/schema/folder /path/to/avro/classes/output/folder\"")
            println("avro receives 3 arguments:")
            println("\t*The complete name of the target class")
            println("\t*The path where the avro schema will be saved")
            println("\t*The path where the avro classes will be generated")
        }
    )
}
