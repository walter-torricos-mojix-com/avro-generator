package avro.generator;

import avro.generator.tool.avroTool;
import avro.generator.common.checkedBiConsumer;

public class Bootstrap {

	public checkedBiConsumer<String, String> init() {
		return avroTool::generateAvroSchema;
	}
}
