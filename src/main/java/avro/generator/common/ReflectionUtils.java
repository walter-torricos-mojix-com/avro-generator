package avro.generator.common;

import java.io.File;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Optional;

public class ReflectionUtils {
	public static Class loadClass(String classesPath, String targetClass) throws MalformedURLException,
			ClassNotFoundException {
		File classes = new File(classesPath);
		URL classesUrl = classes.toURI().toURL();
		URLClassLoader classLoader = new URLClassLoader(new URL[] { classesUrl });
		return classLoader.loadClass(targetClass);
	}

	public static Optional<Field> getClassField(Class target, String fieldName) {
		try {
			return Optional.ofNullable(target.getDeclaredField(fieldName));
		} catch (NoSuchFieldException e) {
			return Optional.empty();
		}
	}
}
