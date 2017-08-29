package avro.generator;

import avro.generator.common.Checked;
import avro.generator.common.JSONUtils;
import avro.generator.common.ReflectionUtils;
import avro.generator.schema.SchemaFilePathProvider;
import avro.generator.schema.SchemaProvider;
import avro.generator.tool.AvroTool;

public class Bootstrap {

	private final Checked.TetraConsumer<String, String, String, String> generateAvroSchemaTask;

	public Bootstrap() {
		this.generateAvroSchemaTask = this.createAvroSchemaTask();
	}

	public Checked.TetraConsumer<String, String, String, String> getGenerateAvroSchemaTask() {
		return generateAvroSchemaTask;
	}

	private Checked.TetraConsumer<String, String, String, String> createAvroSchemaTask() {
		Checked.Function<Class, String> schemaProvider = targetClass ->
				JSONUtils.toPrettyString(SchemaProvider.getSchema(targetClass));

		Checked.TetraConsumer<String, String, String, String> avroSchemaGeneratorTask =
				(classPath, className, avroOutputPath, outputPath) -> Tasks.generateAvroSchema(
						ReflectionUtils::LoadClass,
						schemaProvider,
						SchemaFilePathProvider::Get,
						AvroTool::generateAvroClasses,
						classPath,
						className,
						avroOutputPath,
						outputPath);

		Checked.TetraConsumer<String, String, String, String> taskWithLogs =
				(classPath, className, avroOutputPath, outputPath) ->
					Tasks.generateAvroSchemaInfo(
						avroSchemaGeneratorTask,
						classPath,
						className,
						avroOutputPath,
						outputPath);

		return taskWithLogs;
	}
}
