import avro.generator.common.JSONUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Test;

public class JSONUtilsTest {
	@Test
	public void toPrettyJSONReturnsAJSONInAPrettyFormat() {
		JSONArray array = new JSONArray();
		array.add("value");
		JSONObject json = new JSONObject();
		json.put("a", "string");
		json.put("b", "5");
		json.put("c", array);
		JSONObject root = new JSONObject();
		root.put("json", json);

		String actual = JSONUtils.toPrettyString(root);

		Assert.assertEquals("{\n" +
				"  \"json\":{\n" +
				"    \"a\":\"string\",\n" +
				"    \"b\":\"5\",\n" +
				"    \"c\":[\n" +
				"      \"value\"\n" +
				"    ]\n" +
				"  }\n" +
				"}", actual);
	}
}
