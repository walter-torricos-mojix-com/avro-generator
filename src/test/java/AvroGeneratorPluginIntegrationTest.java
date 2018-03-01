import avro.generator.Bootstrap;
import avro.generator.common.Checked;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

public class AvroGeneratorPluginIntegrationTest {
	@Test
	public void generateTheAvroSchemaAndAvroClassesForAClass() {
		String classPath = "/Users/work/Documents/workspace/transaction-service/target/scala-2.11/classes";
		String className = "models.bss.transaction.Transaction2";
		String avroOutputFolder = "/Users/work/Documents/workspace/transaction-service/app/avro";
		String outputFolder = "/Users/work/Documents/workspace/transaction-service/app/avro";

		Checked.TetraConsumer<String, String, String, String> sut = new Bootstrap().getGenerateAvroSchemaAndClassesTask();

		try {
			Files.createDirectories(Paths.get(outputFolder));
			sut.accept(classPath, className, avroOutputFolder, outputFolder);
			Assert.assertTrue(true);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(e.getMessage(), false);
		}
	}

	@Ignore
	@Test
	public void generateTheAvroClassesFromAnAvroSchema() {
		String avroSchemaPath = "/Users/work/Documents/workspace/transaction-service/conf/schemas/avro" +
				"/Transaction.avsc";
		String outputFolder = "/Users/work/Documents/workspace/transaction-service/app";

		Checked.BiConsumer<String, String> sut = new Bootstrap().getGenerateFromAvroSchema();

		try {
			sut.accept(avroSchemaPath, outputFolder);
			Assert.assertTrue(true);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(e.getMessage(), false);
		}
	}

	@Ignore
	@Test
	public void generateAvroSchemaForAClass() {
		String classPath = "/Users/work/Documents/workspace/transaction-service/target/scala-2.11/classes";
		String className = "models.bss.transaction.Transaction2";
		String avroOutputFolder = "/Users/work/Documents/workspace/transaction-service/conf/schemas/avro";

		Checked.TriConsumer<String, String, String> sut = new Bootstrap().getGenerateAvroSchemaTask();

		try {
			sut.accept(classPath, className, avroOutputFolder);
			Assert.assertTrue(true);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(e.getMessage(), false);
		}
	}
}
