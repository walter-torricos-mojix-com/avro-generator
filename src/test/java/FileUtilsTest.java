import avro.generator.common.FileUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class FileUtilsTest {
	@Test
	public void createOrReplaceWhenCalledCreatesAFile() {
		String content = "Some content";
		String path = "./src/test/resources/test.txt";

		try {
			FileUtils.createOrReplaceFile(path, content);
			Assert.assertTrue(FileUtils.fileExists(path));
		} catch (IOException e) {
			e.printStackTrace();
			Assert.assertTrue(e.getMessage(), false);
		} finally {
			try {
				FileUtils.deleteIfFileExists(path, null);
			} catch (IOException e) {
				e.printStackTrace();
				Assert.assertTrue(e.getMessage(), false);
			}
		}
	}

	@Test
	public void createOrReplaceWhenTheFileExistsReCreatesTheFile() {
		String content = "Some content";
		String path = "./src/test/resources/test.txt";

		try {
			FileUtils.createOrReplaceFile(path, content);
			FileUtils.createOrReplaceFile(path, content);
			Assert.assertTrue(FileUtils.fileExists(path));
		} catch (IOException e) {
			e.printStackTrace();
			Assert.assertTrue(e.getMessage(), false);
		} finally {
			try {
				FileUtils.deleteIfFileExists(path, null);
			} catch (IOException e) {
				e.printStackTrace();
				Assert.assertTrue(e.getMessage(), false);
			}
		}
	}
}
