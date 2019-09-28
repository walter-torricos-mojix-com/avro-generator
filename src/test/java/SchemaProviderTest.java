import avro.generator.schema.SchemaProvider;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class SchemaProviderTest {
	@Test
	public void schemaProviderForASimpleClassWithValueTypePropertiesReturnsTheCorrectJsonSchema() {
		Function<Class, JSONObject> sut = SchemaProvider::getSchema;
		JSONObject json = sut.apply(SimpleClass.class);
		String actual = json.toJSONString();

		Assert.assertEquals("{\"namespace\":\"avro.SimpleClass\",\"name\":\"SimpleClass\",\"type\":\"record\"," +
				"\"fields\":[{\"default\":\"null\",\"name\":\"name\",\"type\":[\"null\",\"string\"]}," +
				"{\"default\":\"null\",\"name\":\"age\",\"type\":[\"null\",\"long\"]}," +
				"{\"default\":\"null\",\"name\":\"average\",\"type\":[\"null\",\"double\"]}," +
				"{\"default\":\"null\",\"name\":\"gender\",\"type\":[\"null\",\"boolean\"]}]}", actual);
	}

	@Test
	public void schemaProviderForAClassWithADatePropertyReturnsTheTypeAsStringAsWeUseAvro1_7_7() {
		Function<Class, JSONObject> sut = SchemaProvider::getSchema;
		JSONObject json = sut.apply(ClassWithDateProperty.class);
		String actual = json.toJSONString();

		Assert.assertEquals("{\"namespace\":\"avro.ClassWithDateProperty\",\"name\":\"ClassWithDateProperty\"," +
				"\"type\":\"record\",\"fields\":[{\"default\":\"null\",\"name\":\"date\"," +
				"\"type\":[\"null\",\"string\"]}]}", actual);
	}

	@Test
	public void schemaProviderForAClassWithAPropertyOfAReferenceTypeReturnTheCorrectJsonSchema() {
		Function<Class, JSONObject> sut = SchemaProvider::getSchema;
		JSONObject json = sut.apply(ClassWithAReferenceType.class);
		String actual = json.toJSONString();

		Assert.assertEquals("{\"namespace\":\"avro.ClassWithAReferenceType\"," +
				"\"name\":\"ClassWithAReferenceType\",\"type\":\"record\"," +
				"\"fields\":[{\"default\":\"null\",\"name\":\"addtional\",\"type\":[\"null\"," +
				"\"string\"]},{\"default\":\"null\",\"name\":\"reference\",\"type\":[\"null\"," +
				"{\"namespace\":\"avro.ClassWithAReferenceType\",\"name\":\"ClassWithDateProperty\"," +
				"\"type\":\"record\",\"fields\":[{\"default\":\"null\",\"name\":\"date\"," +
				"\"type\":[\"null\",\"string\"]}]}]}]}", actual);
	}

	@Test
	public void schemaProviderForAClassWithAListOfElementsReturnTheCorrectJsonSchema() {
		Function<Class, JSONObject> sut = SchemaProvider::getSchema;
		JSONObject json = sut.apply(ClassWithListOfElements.class);
		String actual = json.toJSONString();

		Assert.assertEquals("{\"namespace\":\"avro.ClassWithListOfElements\"," +
				"\"name\":\"ClassWithListOfElements\",\"type\":\"record\"," +
				"\"fields\":[{\"default\":\"null\",\"name\":\"valueTypeList\",\"type\":[\"null\"," +
				"{\"default\":[],\"type\":\"array\",\"items\":{\"default\":\"null\"," +
				"\"name\":\"Double\",\"type\":[\"null\",\"double\"]}}]},{\"default\":\"null\"," +
				"\"name\":\"referenceTypeList\",\"type\":[\"null\",{\"default\":[],\"type\":\"array\"," +
				"\"items\":{\"namespace\":\"avro.ClassWithListOfElements\"," +
				"\"name\":\"ClassWithDateProperty\",\"type\":\"record\"," +
				"\"fields\":[{\"default\":\"null\",\"name\":\"date\",\"type\":[\"null\"," +
				"\"string\"]}]}}]}]}", actual);
	}

	@Test
	public void schemaProviderForAClassWithPrivateFieldsReturnsASchemaWithoutThem() {
		Function<Class, JSONObject> sut = SchemaProvider::getSchema;
		JSONObject json = sut.apply(ClassWithPrivateFields.class);
		String actual = json.toJSONString();

		Assert.assertEquals("{\"namespace\":\"avro.ClassWithPrivateFields\"," +
				"\"name\":\"ClassWithPrivateFields\",\"type\":\"record\"," +
				"\"fields\":[{\"default\":\"null\",\"name\":\"name\",\"type\":[\"null\",\"string\"]}]}",
				actual);
	}

	@Test
	public void schemaProviderForAClassWithEnumFieldsReturnsTheCorrectJsonSchema() {
		Function<Class, JSONObject> sut = SchemaProvider::getSchema;
		JSONObject json = sut.apply(ClassWithListOfEnumValues.class);
		String actual = json.toJSONString();

		Assert.assertEquals("{\"namespace\":\"avro.ClassWithListOfEnumValues\"," +
						"\"name\":\"ClassWithListOfEnumValues\",\"type\":\"record\"," +
						"\"fields\":[{\"default\":\"null\"," +
						"\"name\":\"productRelationshipTypes\",\"type\":[\"null\"," +
						"{\"default\":[],\"type\":\"array\",\"items\":{\"default\":\"null\"," +
						"\"name\":\"ProductRelationshipType\",\"type\":[\"null\"," +
						"\"string\"]}}]}]}",
				actual);
	}

	@Test
	public void schemaProviderForAClassWithIntEnumFieldsReturnsTheCorrectJsonSchema() {
		Function<Class, JSONObject> sut = SchemaProvider::getSchema;
		JSONObject json = sut.apply(ClassWithIntEnumValue.class);
		String actual = json.toJSONString();

		Assert.assertEquals("{\"namespace\":\"avro.ClassWithIntEnumValue\",\"name\":\"ClassWithIntEnumValue\"," +
						"\"type\":\"record\",\"fields\":[{\"default\":\"null\"," +
						"\"name\":\"intEnum\",\"type\":[\"null\",\"long\"]}]}",
				actual);
	}

	public class SimpleClass {
		public String name;

		public int age;

		public double average;

		public boolean gender;
	}

	public class ClassWithDateProperty {
		public String date;
	}

	public class ClassWithAReferenceType {
		public String addtional;

		public ClassWithDateProperty reference;
	}

	public class ClassWithListOfElements {
		public List<Double> valueTypeList;

		public List<ClassWithDateProperty> referenceTypeList;
	}

	public class ClassWithPrivateFields {
		private int privatePrimitive;

		private SimpleClass privateReference;

		public String name;
	}

	public class ClassWithListOfEnumValues {
		public List<ProductRelationshipType> productRelationshipTypes;
	}

	public class ClassWithIntEnumValue {
		public IntEnum intEnum;
	}

	public enum ProductRelationshipType implements Serializable {
		RELIES_ON("reliesOn"),
		BUNDLED("bundled"),
		TARGETS("targets"),
		IS_TARGETED("isTargeted");

		private String value;

		ProductRelationshipType(final String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public static Optional<ProductRelationshipType> fromString(String value) {
			ProductRelationshipType result = null;
			for (ProductRelationshipType type : ProductRelationshipType.values()) {
				if (type.getValue().equalsIgnoreCase(value)) {
					result = type;
					break;
				}
			}

			return Optional.ofNullable(result);
		}
	}

	public enum IntEnum implements Serializable {
		Zero(0),
		One(1);

		private int value;

		IntEnum(final int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}

		public static Optional<IntEnum> fromInteger(int value) {
			IntEnum result = null;
			for (IntEnum productOrderPriority : IntEnum.values()) {
				if (productOrderPriority.getValue() == value) {
					result = productOrderPriority;
					break;
				}
			}
			return Optional.ofNullable(result);
		}
	}
}
