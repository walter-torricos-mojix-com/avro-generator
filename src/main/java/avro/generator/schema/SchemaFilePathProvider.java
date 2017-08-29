package avro.generator.schema;

public class SchemaFilePathProvider {
	public static String Get(String basePath, Class model) {
		return basePath + "/" + model.getSimpleName() + ".avsc";
	}
}
