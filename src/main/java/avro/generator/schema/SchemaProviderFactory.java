package avro.generator.schema;

import org.json.simple.JSONObject;

import java.util.function.Function;

public class SchemaProviderFactory {
	public static Function<Class, JSONObject> Get() {
		return modelClass -> SchemaProvider.getSchema(SchemaProvider::initSchema,
				SchemaProvider::schemaFieldsProvider, modelClass);
	}
}
