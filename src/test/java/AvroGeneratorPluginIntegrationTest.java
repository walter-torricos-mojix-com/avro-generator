import avro.generator.Bootstrap;
import avro.generator.common.Checked;
import org.junit.Test;

public class AvroGeneratorPluginIntegrationTest {
	@Test
	public void avroGenerationTaskWhenInvokedWithValidParametersGeneratesTheClassesCorrectly() {
		String classPath = "./src/test/resources/classes";
		String className = "model.Person";
		String outputFolder = "./src/test/resources/avro";

		Checked.TriConsumer<String, String, String> sut = new Bootstrap().getGenerateAvroSchemaTask();

		try {
			sut.accept(classPath, className, outputFolder);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
