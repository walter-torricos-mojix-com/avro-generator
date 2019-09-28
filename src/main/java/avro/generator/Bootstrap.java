package avro.generator;

import avro.generator.common.Checked;
import avro.generator.common.JSONUtils;
import avro.generator.common.ReflectionUtils;
import avro.generator.schema.SchemaFilePathProvider;
import avro.generator.schema.SchemaProvider;
import avro.generator.tasks.AvroGeneratorTask;
import avro.generator.tool.AvroTool;

public class Bootstrap {

	private final Checked.TetraConsumer<String, String, String, String> generateAvroSchemaAndClassesTask;

	private final Checked.TriConsumer<String, String, String> generateAvroSchemaTask;

	private final Runnable displayInfo;

	private Checked.BiConsumer<String, String> generateFromAvroSchema;

	public Bootstrap() {
		this.generateAvroSchemaAndClassesTask = this.createAvroSchemaAndClassesTask();
		this.generateAvroSchemaTask = this.createAvroSchemaTask();
		this.generateFromAvroSchema = this.createAvroFromSchema();
		this.displayInfo = AvroGeneratorTask::displayHelp;
	}

	public Checked.TetraConsumer<String, String, String, String> getGenerateAvroSchemaAndClassesTask() {
		return this.generateAvroSchemaAndClassesTask;
	}

	public Checked.TriConsumer<String, String, String> getGenerateAvroSchemaTask() {
		return generateAvroSchemaTask;
	}

	public Runnable getDisplayInfo() {
		return this.displayInfo;
	}

	public Checked.BiConsumer<String, String> getGenerateFromAvroSchema() {
		return this.generateFromAvroSchema;
	}

	private Checked.TetraConsumer<String, String, String, String> createAvroSchemaAndClassesTask() {
		Checked.Function<Class, String> schemaProvider = targetClass ->
			JSONUtils.toPrettyString(SchemaProvider.getSchema(targetClass));

		Checked.TetraConsumer<String, String, String, String> avroSchemaGeneratorTask =
			(classPath, className, avroOutputPath, outputPath) -> AvroGeneratorTask.generateSchemaAndClasses(
				ReflectionUtils::loadClass,
				schemaProvider,
				SchemaFilePathProvider::Get,
				AvroTool::generateAvroClasses,
				classPath,
				className,
				avroOutputPath,
				outputPath);

		Checked.TetraConsumer<String, String, String, String> taskWithLogs =
			(classPath, className, avroOutputPath, outputPath) ->
				AvroGeneratorTask.generateSchemaInfoDecorator(
					avroSchemaGeneratorTask,
					classPath,
					className,
					avroOutputPath,
					outputPath);

		return taskWithLogs;
	}

	private Checked.TriConsumer<String, String, String> createAvroSchemaTask() {
		Checked.Function<Class, String> schemaProvider = targetClass ->
				JSONUtils.toPrettyString(SchemaProvider.getSchema(targetClass));

		Checked.TriConsumer<String, String, String> avroSchemaGeneratorTask =
				(classPath, className, avroOutputPath) -> AvroGeneratorTask.generateSchema(
						ReflectionUtils::loadClass,
						schemaProvider,
						SchemaFilePathProvider::Get,
						classPath,
						className,
						avroOutputPath);

		return avroSchemaGeneratorTask;
	}

	private Checked.BiConsumer<String,String> createAvroFromSchema() {
		return (avroSchemaPath, outputFolder) ->
			AvroGeneratorTask.generateFromJson(AvroTool::generateAvroClasses, avroSchemaPath, outputFolder);
	}
}
