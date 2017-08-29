package avro.generator;

import avro.generator.common.Checked;
import avro.generator.common.FileUtils;

public class Tasks {
	public static void generateAvroSchema(
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

	public static void generateAvroSchemaInfo(
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
}
