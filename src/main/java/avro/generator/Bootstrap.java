package avro.generator;

import avro.generator.tool.AvroTool;
import avro.generator.common.checkedBiConsumer;

public class Bootstrap {

	public checkedBiConsumer<String, String> init() {
		return AvroTool::generateAvroClasses;
	}
}
