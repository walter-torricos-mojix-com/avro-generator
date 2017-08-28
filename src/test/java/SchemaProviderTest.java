import avro.generator.schema.SchemaProviderFactory;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.function.Function;

public class SchemaProviderTest {
	@Test
	public void schemaProviderForASimpleClassWithValueTypePropertiesReturnsTheCorrectJsonSchema() {
		Function<Class, JSONObject> sut = SchemaProviderFactory.Get();
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
		Function<Class, JSONObject> sut = SchemaProviderFactory.Get();
		JSONObject json = sut.apply(ClassWithDateProperty.class);
		String actual = json.toJSONString();

		Assert.assertEquals("{\"namespace\":\"avro.ClassWithDateProperty\",\"name\":\"ClassWithDateProperty\"," +
				"\"type\":\"record\",\"fields\":[{\"default\":\"null\",\"name\":\"date\"," +
				"\"type\":[\"null\",\"string\"]}]}", actual);
	}

	@Test
	public void schemaProviderForAClassWithAPropertyOfAReferenceTypeReturnTheCorrectJsonSchema() {
		Function<Class, JSONObject> sut = SchemaProviderFactory.Get();
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
		Function<Class, JSONObject> sut = SchemaProviderFactory.Get();
		JSONObject json = sut.apply(ClassWithListOfElements.class);
		String actual = json.toJSONString();

		Assert.assertEquals("{\"namespace\":\"avro.ClassWithListOfElements\"," +
				"\"name\":\"ClassWithListOfElements\",\"type\":\"record\"," +
				"\"fields\":[{\"default\":\"null\",\"name\":\"valueTypeList\",\"type\":[\"null\"," +
				"{\"default\":[],\"type\":\"array\",\"items\":\"double\"}]},{\"default\":\"null\"," +
				"\"name\":\"referenceTypeList\",\"type\":[\"null\",{\"default\":[],\"type\":\"array\"," +
				"\"items\":{\"default\":\"null\",\"name\":\"ClassWithDateProperty\"," +
				"\"type\":{\"namespace\":\"avro.ClassWithListOfElements\"," +
				"\"name\":\"ClassWithDateProperty\",\"type\":\"record\"," +
				"\"fields\":[{\"default\":\"null\",\"name\":\"date\",\"type\":[\"null\"," +
				"\"string\"]}]}}}]}]}", actual);
	}

	@Test
	public void schemaProviderForAClassWithPrivateFieldsReturnsASchemaWithoutThem() {
		Function<Class, JSONObject> sut = SchemaProviderFactory.Get();
		JSONObject json = sut.apply(ClassWithPrivateFields.class);
		String actual = json.toJSONString();

		Assert.assertEquals("{\"namespace\":\"avro.ClassWithPrivateFields\"," +
				"\"name\":\"ClassWithPrivateFields\",\"type\":\"record\"," +
				"\"fields\":[{\"default\":\"null\",\"name\":\"name\",\"type\":[\"null\",\"string\"]}]}",
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
}
