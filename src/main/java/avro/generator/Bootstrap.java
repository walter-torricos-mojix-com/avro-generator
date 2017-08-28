package avro.generator;

import avro.generator.common.Checked;
import avro.generator.common.JSONUtils;
import avro.generator.common.ReflectionUtils;
import avro.generator.schema.SchemaProviderFactory;
import avro.generator.tool.AvroTool;

public class Bootstrap {

	private final Checked.TriConsumer<String, String, String> generateAvroSchemaTask;

	public Bootstrap() {
		this.generateAvroSchemaTask = this.createAvroSchemaTask();
	}

	public Checked.TriConsumer<String, String, String> getGenerateAvroSchemaTask() {
		return generateAvroSchemaTask;
	}

	private Checked.TriConsumer<String, String, String> createAvroSchemaTask() {
		Checked.Function<Class, String> schemaProvider = targetClass ->
				JSONUtils.toPrettyString(SchemaProviderFactory.Get().apply(targetClass));

		Checked.TriConsumer<String, String, String> avroSchemaGeneratorTask =
				(classPath, className, outputPath) -> Tasks.generateAvroSchema(
						ReflectionUtils::LoadClass,
						schemaProvider,
						AvroTool::generateAvroClasses,
						classPath,
						className,
						outputPath);

		Checked.TriConsumer<String, String, String> taskWithInfo = (classPath, className, outputPath) ->
			Tasks.generateAvroSchemaInfo(avroSchemaGeneratorTask, classPath, className, outputPath);

		return taskWithInfo;
	}
}
