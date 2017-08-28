import avro.generator.common.ReflectionUtils;
import org.junit.Assert;
import org.junit.Test;

public class ReflectionUtilsTest {
	@Test
	public void loadClassWithValidPathsLoadsTheTargetClass() {
		String classesPath = "./src/test/resources/classes";
		String classFullName = "model.Person";

		try {
			Class actual = ReflectionUtils.LoadClass(classesPath, classFullName);

			Assert.assertNotNull(actual);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertFalse(e.getMessage(),true);
		}
	}
}
