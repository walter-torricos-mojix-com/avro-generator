package avro.generator.tool;

import org.apache.avro.tool.SpecificCompilerTool;
import org.apache.avro.tool.Tool;

import java.util.Arrays;
import java.util.List;

public class avroTool {
	public static void generateAvroSchema(
		String avroSchema,
		String outputFolder
	) throws Exception {
		List<String> args = Arrays.asList(
			"schema",
			avroSchema,
			outputFolder);
		Tool tool = new SpecificCompilerTool();
		tool.run(System.in, System.out, System.err, args);
	}
}
