package avro.generator;

import avro.generator.common.Checked;
import avro.generator.common.FileUtils;

public class Tasks {
	public static void generateAvroSchema(
		Checked.BiFunction<String, String, Class> classLoader,
		Checked.Function<Class, String> schemaProvider,
		Checked.BiConsumer<String, String> avroClassGenerator,
		String classPath,
		String targetClassFullName,
	        String outputFolder) throws Exception {

		Class targetClass = classLoader.apply(classPath, targetClassFullName);
		String avroSchema = schemaProvider.apply(targetClass);

		// TODO: avroSchemaPath should not be hardcoded
		String avroSchemaPath = outputFolder + "/avro.avsc";
		FileUtils.createOrReplaceFile(avroSchemaPath, avroSchema);

		avroClassGenerator.accept(avroSchemaPath, outputFolder);
	}

	public static void generateAvroSchemaInfo(
		Checked.TriConsumer<String, String, String> generator,
		String classPath,
		String targetClassFullName,
		String outputFolder) {

		System.out.println("Avro Generator Task Start......");
		System.out.println("classPath: " + classPath);
		System.out.println("targetClassFullName: " + targetClassFullName);
		System.out.println("outputFolder: " + outputFolder);

		try {
			generator.accept(classPath, targetClassFullName, outputFolder);
			System.out.println("Avro generation finished successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
