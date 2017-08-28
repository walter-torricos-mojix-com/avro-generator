import avro.generator.tool.AvroTool;
import org.apache.ivy.util.FileUtil;
import org.junit.Assert;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AvroToolTest {
	@Test
	public void generateAvroSchemaWhenCalledWithValidDirectoriesGeneratesTheAvroClasses() {
		String avroSchema = "./src/test/resources/ProductOrderNotification.avsc";
		String outputFolder = "./src/test/resources";
		Path generatedFilesPath = Paths.get("./src/test/resources/avro");
		Assert.assertFalse("avro folder does not exists", Files.exists(generatedFilesPath));
		try {
			AvroTool.generateAvroClasses(avroSchema, outputFolder);
			Assert.assertTrue(Files.exists(generatedFilesPath));
			FileUtil.forceDelete(generatedFilesPath.toFile());
		} catch (Exception e) {
			Assert.assertTrue(e.getMessage(), false);
		}
	}
}
