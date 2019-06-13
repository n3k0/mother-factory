package net.psybit.freemarker;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;
import net.psybit.exception.MotherFactoryException;
import net.psybit.utils.Constants;
import net.psybit.utils.PathUtils;

/**
 * Take a template, replace the values, and copy it to a specific location
 *
 * @author morsetvite
 *
 */
public class FreeMarkerHandler {

	private static Configuration configuration = new Configuration(new Version(2, 3, 20));

	public FreeMarkerHandler() {
		configuration.setClassLoaderForTemplateLoading(FreeMarkerHandler.class.getClassLoader(), "/templates");
		configuration.setDefaultEncoding("UTF-8");
		configuration.setLocale(Locale.US);
		configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
	}

	public void handsOnTemplate(final File testSourceDirectory, final String simpleFidderPackage,
			final String methodName) {
		final Map<String, Object> input = new HashMap<>();
		input.put("simple_fidder_package", simpleFidderPackage);
		input.put("method_name", methodName);

		String testSrcSimpleFidderFile = PathUtils.toPath(simpleFidderPackage, Constants.SLASH)
				.concat("SimpleFidder.java");
		File targetTestSrcSimpleFidderFile = new File(testSourceDirectory, testSrcSimpleFidderFile);

		try (Writer fileWriter = new FileWriter(targetTestSrcSimpleFidderFile, true)) {

			Template template = configuration.getTemplate("SimpleFidder.base.ftl");
			template.process(input, fileWriter);
		}
		catch (IOException | TemplateException e) {
			throw new MotherFactoryException(e);
		}
	}
}