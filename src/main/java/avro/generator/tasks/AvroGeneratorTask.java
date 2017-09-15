package avro.generator.tasks;

import avro.generator.common.Checked;
import avro.generator.common.FileUtils;

public class AvroGeneratorTask {
	public static void generateSchema(
		Checked.BiFunction<String, String, Class> classLoader,
		Checked.Function<Class, String> schemaProvider,
		Checked.BiFunction<String, Class, String> schemaPathProvider,
		Checked.BiConsumer<String, String> avroClassGenerator,
		String classPath,
		String targetClassFullName,
	        String avroOutputFolder,
	        String outputFolder) throws Exception {

		Class targetClass = classLoader.apply(classPath, targetClassFullName);
		String avroSchema = schemaProvider.apply(targetClass);
		String avroSchemaPath  = schemaPathProvider.apply(avroOutputFolder, targetClass);
		FileUtils.createOrReplaceFile(avroSchemaPath, avroSchema);
		avroClassGenerator.accept(avroSchemaPath, outputFolder);
	}

	public static void generateSchemaInfoDecorator(
		Checked.TetraConsumer<String, String, String, String> generator,
		String classPath,
		String targetClassFullName,
		String avroOutputFolder,
		String outputFolder) {

		System.out.println("Avro Generator Task Start......");
		System.out.println("classPath: " + classPath);
		System.out.println("targetClassFullName: " + targetClassFullName);
		System.out.println("avroSchemaOutputFolder: " + avroOutputFolder);
		System.out.println("avroClassesOutputFolder: " + outputFolder);

		try {
			generator.accept(classPath, targetClassFullName, avroOutputFolder, outputFolder);
			System.out.println("Avro generation finished successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void displayHelp() {
		System.out.println("avro example: ");
		System.out.println("$ sbt \"avro complete.class.Name /path/to/avro/schema/folder " +
				"/path/to/avro/classes/output/folder\"");
		System.out.println("avro receives 3 arguments:");
		System.out.println("\t*The complete name of the target class");
		System.out.println("\t*The path where the avro schema will be saved");
		System.out.println("\t*The path where the avro classes will be generated");
		System.out.println();
		System.out.println("avro from JSON example: ");
		System.out.println("$ sbt \"avroJson /path/to/avro/schema.avsc /path/to/avro/classes/output/folder\"");
		System.out.println("avroJson receives 2 arguments:");
		System.out.println("\t*The path to the avro schema file");
		System.out.println("\t*The path where the avro schema will be saved");
	}

	public static void generateFromJson(
		Checked.BiConsumer<String, String> avroClassGenerator,
		String avroSchemaPath,
		String outputFolder) throws Exception {

		avroClassGenerator.accept(avroSchemaPath, outputFolder);
	}
}
