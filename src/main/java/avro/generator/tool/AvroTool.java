package avro.generator.tool;

import org.apache.avro.tool.SpecificCompilerTool;
import org.apache.avro.tool.Tool;

import java.util.Arrays;
import java.util.List;

public class AvroTool {
	public static void generateAvroClasses(
		String avroSchemaPath,
		String outputFolder
	) throws Exception {
		List<String> args = Arrays.asList(
			"schema",
			avroSchemaPath,
			outputFolder);
		Tool tool = new SpecificCompilerTool();
		tool.run(System.in, System.out, System.err, args);
	}
}
