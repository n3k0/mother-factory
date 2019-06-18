package net.psybit.loader;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.psybit.exception.MotherFactoryException;
import net.psybit.pojo.Container;
import net.psybit.pojo.MojoSettings;
import net.psybit.utils.Constants;
import net.psybit.utils.CustomFileFilter;

/**
 * Reads the entire classpath in order to load the requested class
 *
 * @author n3k0
 */
public class ClassReader {

	private static final Logger LOGGER = LoggerFactory.getLogger(ClassReader.class);

	private static ClassReader instance;

	private MojoSettings mojoSettings;

	private ClassReader(final MojoSettings mojoSettings) {
		this.mojoSettings = mojoSettings;
	}

	public static final synchronized ClassReader getInstance(final MojoSettings mojoSettings) {
		if (instance == null) {
			instance = new ClassReader(mojoSettings);
		}
		return instance;
	}

	public void load(final Container mojoContainer) {
		URLClassLoader classLoader = generateCompoundClassLoader();

		File pojoTargetClassFolder = mojoContainer.getPojoTargetClassFolder();
		String outputDirectory = this.mojoSettings.getOutputDirectory().getPath().concat(Constants.SLASH);

		FileFilter fileFilter = new CustomFileFilter(mojoSettings.getFileFilterInclusions(),
				mojoSettings.getFileFilterExclusions());

		List<File> fileClasses = this.filesByCriterias(pojoTargetClassFolder, fileFilter);

		if (fileClasses != null && !fileClasses.isEmpty()) {

			Constants.projectClasses().clear();

			for (File fileClass : fileClasses) {

				String path = fileClass.getParent().replace(outputDirectory, Constants.EMPTY_SPACE);

				String pkg = path.replaceAll(Constants.SLASH_REGEX, Constants.DOT);

				if (StringUtils.isNotBlank(pkg)) {
					String className = pkg.concat(Constants.DOT)
							.concat(fileClass.getName().replace(Constants.CLASS_SUFFIX, Constants.EMPTY_SPACE));

					try {
						Class<?> klazz = classLoader.loadClass(className);

						Constants.projectClasses().add(klazz);

						LOGGER.debug("Added class >>> {}", className);
					}
					catch (ClassNotFoundException e) {
						LOGGER.error("Class not found >>> {}", className);
					}
				}
			}
		}

		try {
			if (classLoader != null) {
				classLoader.close();
			}
		}
		catch (IOException e) {
			LOGGER.error("ClassReader load -->> ", e);
		}
	}

	@SuppressWarnings("unchecked")
	URLClassLoader generateCompoundClassLoader() {
		final File outputDirectory = mojoSettings.getOutputDirectory();

		Set<String> elements = new HashSet<>();
		List<URL> uris = new ArrayList<>(0);
		URLClassLoader urlClassLoader = null;
		ClassLoader contextClassLoader = null;
		URL[] array = null;

		try {
			if (mojoSettings.getProject().getCompileClasspathElements() != null) {
				elements.addAll(mojoSettings.getProject().getCompileClasspathElements());
			}
			if (mojoSettings.getProject().getTestClasspathElements() != null) {
				elements.addAll(mojoSettings.getProject().getTestClasspathElements());
			}
			if (mojoSettings.getProject().getRuntimeClasspathElements() != null) {
				elements.addAll(mojoSettings.getProject().getRuntimeClasspathElements());
			}
			if (mojoSettings.getProject().getSystemClasspathElements() != null) {
				elements.addAll(mojoSettings.getProject().getSystemClasspathElements());
			}

			for (String element : elements) {
				uris.add(new File(element).toURI().toURL());
			}
			array = uris.toArray(new URL[0]);
		}
		catch (DependencyResolutionRequiredException | MalformedURLException e) {
			throw new MotherFactoryException(e);
		}
		try {
			contextClassLoader = new URLClassLoader(array, Thread.currentThread().getContextClassLoader());

			Thread.currentThread().setContextClassLoader(contextClassLoader);
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

			urlClassLoader = new URLClassLoader(new URL[] { outputDirectory.toURI().toURL() }, classLoader);
		}
		catch (MalformedURLException e) {
			throw new MotherFactoryException(e);
		}

		return urlClassLoader;
	}

	List<File> lookAround(File root, final FileFilter fileFilter) {
		List<File> fileClasses = new ArrayList<>(0);

		for (File file : root.listFiles(fileFilter)) {
			if (file.isDirectory()) {
				fileClasses.addAll(this.lookAround(file, fileFilter));
			}
			else {
				fileClasses.add(file);
			}
		}
		return fileClasses;
	}

	List<File> filesByCriterias(final File root, final FileFilter... fileFilters) {
		List<File> files = new LinkedList<>();

		if (fileFilters != null && fileFilters.length > 0) {
			for (FileFilter fileFilter : fileFilters) {
				if (fileFilter != null) {
					files.addAll(lookAround(root, fileFilter));
				}
			}
		}
		return files;
	}
}