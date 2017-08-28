package avro.generator.common;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class ReflectionUtils {
	public static Class LoadClass(String classesPath, String targetClass) throws MalformedURLException,
			ClassNotFoundException {
		File classes = new File(classesPath);
		URL classesUrl = classes.toURI().toURL();
		URLClassLoader classLoader = new URLClassLoader(new URL[] { classesUrl });
		return classLoader.loadClass(targetClass);
	}
}
