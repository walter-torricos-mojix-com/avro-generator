import avro.generator.Bootstrap;
import avro.generator.common.Checked;
import org.junit.Assert;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

public class AvroGeneratorPluginIntegrationTest {
	@Test
	public void avroGenerationTaskWhenInvokedWithValidParametersGeneratesTheClassesCorrectly() {
		String classPath = "./src/test/resources/classes";
		String className = "model.Person";
		String avroOutputFolder = "./src/test/resources/output/generatortest";
		String outputFolder = "./src/test/resources/output/generatortest";

		Checked.TetraConsumer<String, String, String, String> sut = new Bootstrap().getGenerateAvroSchemaTask();

		try {
			Files.createDirectories(Paths.get(outputFolder));
			sut.accept(classPath, className, avroOutputFolder, outputFolder);
			Assert.assertTrue(true);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(e.getMessage(), false);
		}
	}
}
