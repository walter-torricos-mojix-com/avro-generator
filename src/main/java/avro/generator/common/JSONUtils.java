package avro.generator.common;

import com.cedarsoftware.util.io.JsonWriter;
import org.json.simple.JSONObject;

public class JSONUtils {
	public static String toPrettyString(JSONObject json) {
		return JsonWriter.formatJson(json.toJSONString());
	}
}
