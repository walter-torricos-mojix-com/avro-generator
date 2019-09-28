package avro.generator.schema;

import java.lang.reflect.Type;

class SchemaFieldMetadata {
	private final String name;

	private final boolean isFromArray;

	private Class type;

	private Type genericType;

	SchemaFieldMetadata(Class fieldType, String fieldName, Type genericType, boolean isFromArray) {
		this.type = fieldType;
		this.name = fieldName;
		this.genericType = genericType;
		this.isFromArray = isFromArray;
	}

	Class getType() {
		return type;
	}

	String getName() {
		return name;
	}

	Type getGenericType() {
		return genericType;
	}

	boolean isArrayGenericType() {
		return isFromArray;
	}
}
