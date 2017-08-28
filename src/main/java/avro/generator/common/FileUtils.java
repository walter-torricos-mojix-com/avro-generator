package avro.generator.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.Paths.get;

public class FileUtils {
	public static void createOrReplaceFile(String filePath, String fileContent) throws IOException {
		deleteIfFileExists(filePath, fileContent);
		createFileWith(filePath, fileContent);
	}

	public static void deleteIfFileExists(String filePath, String fileContent) throws IOException {
		if(fileExists(filePath)) {
			Path path = get(filePath);
			Files.delete(path);
		}
	}

	public static void createFileWith(String filePath, String fileContent) throws IOException {
		Files.write(Paths.get(filePath), fileContent.getBytes());
	}

	public static boolean fileExists(String filePath) {
		File file = new File(filePath);
		return file.exists();
	}
}
