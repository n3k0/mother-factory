package net.psybit.gen.factory;

import java.io.File;
import java.util.regex.Pattern;

import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.codehaus.plexus.PlexusTestCase;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import net.psybit.mojo.MotherFactoryMojo;
import net.psybit.utils.PathUtils;

/*
 * This test is useless as we need a project apart because the classpath in a test is
 * not read in the same way as a real project, added just to illustrate a mojo test
 */
@Ignore
public class DegenerateMojoTest extends AbstractMojoTestCase {

	@Test
	public void shouldExecuteMojo() throws Exception {
		String baseDir = PlexusTestCase.getBasedir();

		String pom = PathUtils.pathFrom("src", "test", "resources", "pom.xml");

		File pomXml = new File(baseDir, pom);

		Assert.assertTrue(pomXml.exists());

		MotherFactoryMojo mojo = (MotherFactoryMojo) this.lookupMojo("gen", pomXml);

		Assert.assertNotNull(mojo);

		mojo.execute();

		String externalFactoryPath = PathUtils.pathFrom("src", "test", "java", "custom", "pkg", "facade", "V01",
				"ExternalFactory.java");

		File externalFactory = new File(baseDir, externalFactoryPath);
		Assert.assertTrue(externalFactory.exists());

		String internalFactoryPath = PathUtils.pathFrom("src", "test", "java", "custom", "pkg", "business",
				"InternalFactory.java");
		File internalFactory = new File(baseDir, internalFactoryPath);
		Assert.assertTrue(internalFactory.exists());

		String reportFolderPath = PathUtils.pathFrom("target", "test-classes");

		File reportFolder = new File(baseDir, reportFolderPath);

		Assert.assertNotNull(reportFolder);

		File[] list = reportFolder.listFiles();

		Assert.assertFalse(list == null || list.length == 0);

		File report = reportFolder.listFiles()[0];

		Assert.assertNotNull(report);

		Pattern pattern = Pattern
				.compile("(generated\\-)([0-9]{4})-([0-9]{2})-([0-9]{2})_([0-9]{2})-([0-9]{2})-([0-9]{2})(.txt)");

		Assert.assertTrue(pattern.matcher(report.getName()).matches());
		Assert.assertTrue(report.exists());
	}
}