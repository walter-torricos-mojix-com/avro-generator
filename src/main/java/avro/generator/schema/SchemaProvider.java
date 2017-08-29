package avro.generator.schema;

import avro.generator.common.ReflectionUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.math.BigInteger;
import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class SchemaProvider {
	public static JSONObject getSchema(Class model) {
		JSONObject schema = initSchema(model);
		JSONArray fields = schemaFieldsProvider(model);
		schema.put(SchemaConstants.Properties.fields, fields);

		return schema;
	}

	private static JSONObject initSchema(Class model) {
		JSONObject json = new JSONObject();
		json.put(SchemaConstants.Properties.namespace, getNamespace(model));
		json.put(SchemaConstants.Properties.type, SchemaConstants.Values.record);
		json.put(SchemaConstants.Properties.name, model.getSimpleName());

		return json;
	}

	private static JSONArray schemaFieldsProvider(Class model) {
		JSONArray schemaFields = new JSONArray();
		List<JSONObject> fields = Arrays.stream(model.getFields())
			.map(field -> fieldsTypeProvider(
				model,
				new SchemaFieldMetadata(
					field.getType(),
					field.getName(),
					field.getGenericType(),
					false)))
			.collect(Collectors.toList());

		for (JSONObject field : fields) {
			if(field == null) {
				System.out.println("The field " + field.getClass().getName() + "of type " +
					field.getClass().getTypeName() + "was not included in the avro schema.");
			}

			schemaFields.add(field);
		}

		return schemaFields;
	}

	private static JSONObject fieldsTypeProvider(Class model, SchemaFieldMetadata field) {
		List<BiFunction<Class, SchemaFieldMetadata, JSONObject>> providers = Arrays.asList(
			SchemaProvider::getStringFieldSchema,
			SchemaProvider::getNaturalNumberFieldSchema,
			SchemaProvider::getDecimalNumberFieldSchema,
			SchemaProvider::getBooleanFieldSchema,
			SchemaProvider::getDateFieldSchema,
			SchemaProvider::getEnumSchema,
			SchemaProvider::getListFieldSchema,
			SchemaProvider::getRecordFieldSchema);

		return providers.stream()
			.map(provider -> provider.apply(model, field))
			.filter(result -> result != null)
			.findFirst()
			.orElse(null);
	}

	private static JSONObject getCommonFieldSchema(Class model, SchemaFieldMetadata field) {
		JSONObject jsonField = new JSONObject();
		jsonField.put(SchemaConstants.Properties.name, field.getName());
		jsonField.put(SchemaConstants.Properties.defaultProperty, SchemaConstants.Values.nullValue);

		return jsonField;
	}

	private static JSONObject getStringFieldSchema(Class model, SchemaFieldMetadata field) {
		if(!isString(field.getType())) {
			return null;
		}

		JSONObject jsonField = getCommonFieldSchema(model, field);
		JSONArray types = new JSONArray();
		types.add(SchemaConstants.Values.nullValue);
		types.add(SchemaConstants.Values.string);
		jsonField.put(SchemaConstants.Properties.type, types);

		return jsonField;
	}

	private static JSONObject getNaturalNumberFieldSchema(Class model, SchemaFieldMetadata field) {
		Class fieldClass = field.getType();
		if(!isNatural(fieldClass)) {
			return null;
		}

		JSONObject jsonField = getCommonFieldSchema(model, field);
		JSONArray types = new JSONArray();
		types.add(SchemaConstants.Values.nullValue);
		types.add(SchemaConstants.Values.naturalNumber);
		jsonField.put(SchemaConstants.Properties.type, types);

		return jsonField;
	}

	private static JSONObject getDecimalNumberFieldSchema(Class model, SchemaFieldMetadata field) {
		Class fieldClass = field.getType();
		if(!isDecimal(fieldClass)) {
			return null;
		}

		JSONObject jsonField = getCommonFieldSchema(model, field);
		JSONArray types = new JSONArray();
		types.add(SchemaConstants.Values.nullValue);
		types.add(SchemaConstants.Values.decimalNumber);
		jsonField.put(SchemaConstants.Properties.type, types);

		return jsonField;
	}

	private static JSONObject getBooleanFieldSchema(Class model, SchemaFieldMetadata field) {
		if(!isBoolean(field.getType())) {
			return null;
		}

		JSONObject jsonField = getCommonFieldSchema(model, field);
		JSONArray types = new JSONArray();
		types.add(SchemaConstants.Values.nullValue);
		types.add(SchemaConstants.Values.booleanValue);
		jsonField.put(SchemaConstants.Properties.type, types);

		return jsonField;
	}

	private static JSONObject getDateFieldSchema(Class model, SchemaFieldMetadata field) {
		if(!isDate(field.getType())) {
			return null;
		}

		JSONObject jsonField = getCommonFieldSchema(model, field);
		JSONArray types = new JSONArray();
		types.add(SchemaConstants.Values.nullValue);
		types.add(SchemaConstants.Values.string);
		jsonField.put(SchemaConstants.Properties.type, types);

		return jsonField;
	}

	private static JSONObject getEnumSchema(Class model, SchemaFieldMetadata field) {
		if(!field.getType().isEnum()) {
			return null;
		}

		JSONObject jsonField = getCommonFieldSchema(model, field);
		JSONArray types = new JSONArray();
		types.add(SchemaConstants.Values.nullValue);
		Optional<Field> valueField = ReflectionUtils.getClassField(field.getType(), "value");
		if(!valueField.isPresent()) {
			types.add(SchemaConstants.Values.string);
			jsonField.put(SchemaConstants.Properties.type, types);

			return jsonField;
		}

		Class valueType = valueField.get().getType();
		if(isNatural(valueType)) {
			types.add(SchemaConstants.Values.naturalNumber);
		}
		else if(isBoolean(valueType)) {
			types.add(SchemaConstants.Values.booleanValue);
		}
		else if(isDecimal(valueType)) {
			types.add(SchemaConstants.Values.decimalNumber);
		}
		else {
			types.add(SchemaConstants.Values.string);
		}

		jsonField.put(SchemaConstants.Properties.type, types);
		return jsonField;
	}

	private static JSONObject getListFieldSchema(Class model, SchemaFieldMetadata field) {
		if(!Collection.class.isAssignableFrom(field.getType())) {
			return null;
		}

		JSONObject jsonField = getCommonFieldSchema(model, field);
		JSONArray types = new JSONArray();
		types.add(SchemaConstants.Values.nullValue);
		JSONObject arrayType = new JSONObject();
		arrayType.put(SchemaConstants.Properties.type, SchemaConstants.Values.array);
		arrayType.put(SchemaConstants.Properties.defaultProperty, new JSONArray());
		Class listParameterClass = (Class)((ParameterizedType)field.getGenericType())
			.getActualTypeArguments()[0];
		JSONObject item = fieldsTypeProvider(model, new SchemaFieldMetadata(
			listParameterClass, listParameterClass.getSimpleName(), null, true));
		arrayType.put(SchemaConstants.Properties.items, item);
		types.add(arrayType);
		jsonField.put(SchemaConstants.Properties.type, types);

		return jsonField;
	}

	private static JSONObject getRecordFieldSchema(Class model, SchemaFieldMetadata field) {
		if(field.getType().getCanonicalName().contains("java")) {
			return null;
		}

		JSONObject jsonField = getCommonFieldSchema(model, field);
		JSONArray types = new JSONArray();
		types.add(SchemaConstants.Values.nullValue);
		JSONObject record = new JSONObject();
		record.put(SchemaConstants.Properties.type, SchemaConstants.Values.record);
		record.put(SchemaConstants.Properties.namespace, getNamespace(model));
		String name = field.getType().getSimpleName();
		record.put(SchemaConstants.Properties.name,
			   name.substring(0, 1).toUpperCase() + name.substring(1));
		record.put(SchemaConstants.Properties.fields, schemaFieldsProvider(field.getType()));
		if(field.isArrayGenericType()) {
			return record;
		}

		types.add(record);
		jsonField.put(SchemaConstants.Properties.type, types);
		return jsonField;
	}

	private static String getNamespace(Class model) {
		return SchemaConstants.Values.avroPackage + "." + model.getSimpleName();
	}

	private static boolean isNatural(Class target) {
		return target.isAssignableFrom(int.class) || target.isAssignableFrom(long.class) ||
			target.isAssignableFrom(byte.class) || target.isAssignableFrom(BigInteger.class) ||
			target.isAssignableFrom(Integer.class) || target.isAssignableFrom(Long.class) ||
			target.isAssignableFrom(Byte.class);
	}

	private static boolean isDecimal(Class target) {
		return target.isAssignableFrom(float.class) || target.isAssignableFrom(double.class) ||
			target.isAssignableFrom(Float.class) || target.isAssignableFrom(Double.class);
	}

	private static boolean isBoolean(Class target) {
		return target.isAssignableFrom(boolean.class) ||
			target.isAssignableFrom(Boolean.class);
	}

	private static boolean isString(Class target) {
		return target.isAssignableFrom(String.class);
	}

	private static boolean isDate(Class target) {
		return target.isAssignableFrom(Date.class);
	}
}
