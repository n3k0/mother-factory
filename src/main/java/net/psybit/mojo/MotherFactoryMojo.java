package net.psybit.mojo;

import java.io.File;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.psybit.freemarker.FreeMarkerHandler;
import net.psybit.poet.FactoryFileSpitter;
import net.psybit.pojo.Container;
import net.psybit.pojo.FidderOptions;
import net.psybit.pojo.MojoSettings;
import net.psybit.utils.Constants;
import net.psybit.utils.PathUtils;
import net.psybit.utils.Validator;

/**
 *
 * Mojo parameter reader and the guilt of invoke the other classes
 *
 * goal gen
 *
 * @phase process-classes
 * @description spits the source code for static pojo factories
 * @requiresDependencyResolution test
 */
@Mojo(name = "gen", requiresDependencyResolution = ResolutionScope.TEST)
public class MotherFactoryMojo extends AbstractMojo {

	private static final Logger LOGGER = LoggerFactory.getLogger(MotherFactoryMojo.class);

	@Parameter(defaultValue = "${project}", required = false, readonly = true)
	private MavenProject project;

	// default to project/src/main/java
	@Parameter(defaultValue = "${project.build.sourceDirectory}", required = false)
	private File sourceDirectory;

	// default to project/target/classes
	@Parameter(property = "outputDirectory", defaultValue = "${project.build.outputDirectory}")
	private File outputDirectory;

	// default to project/src/test/java
	@Parameter(property = "testSourceDirectory", defaultValue = "${project.build.testSourceDirectory}")
	private File testSourceDirectory;

	@Parameter(defaultValue = "${pom.groupId}")
	private String simpleFidderPackage;

	@Parameter(property = "pojoPackages", defaultValue = "${project.build.outputDirectory}")
	private List<String> pojoPackages;

	@Parameter(property = "factoryQualifiedNames", defaultValue = "${project.build.testSourceDirectory}.FatFactory")
	private List<String> factoryQualifiedNames;

	@Parameter(property = "fileFilterInclusions", defaultValue = "${pojoPackages}")
	private List<String> fileFilterInclusions;

	@Parameter(property = "fileFilterExclusions")
	private List<String> fileFilterExclusions;

	@Parameter(defaultValue = "false", property = "staticMethods")
	private boolean staticMethods;

	@Parameter(defaultValue = "false", property = "canIHazFidder")
	private boolean canIHazFidder;

	@Parameter(defaultValue = "INSTANCE", property = "simpleFidderInstanceName")
	private String simpleFidderInstanceName;

	@Parameter(defaultValue = "create", property = "simpleFidderCreateMethodName")
	private String simpleFidderCreateMethodName;

	@Parameter(defaultValue = "100", property = "stringsLength")
	private int stringsLength;

	@Parameter(defaultValue = "100", property = "integerLimit")
	private int integerLimit;

	@Parameter(defaultValue = "9.5", property = "canIHazFidder")
	private double floatPointFromLimit;

	@Parameter(defaultValue = "100.3", property = "canIHazFidder")
	private double floatPointToLimit;

	@Parameter(defaultValue = "2", property = "canIHazFidder")
	private int dataStructureSize;

	@Override
	public void execute() throws MojoExecutionException {
		LOGGER.debug("Validating parameters....");

		Validator.validateListSameSize(this.pojoPackages, this.factoryQualifiedNames);

		MojoSettings mojoSettings = new MojoSettings();

		LOGGER.debug("Checking target folders....");

		mojoSettings.setContainers(this.createContainers());

		LOGGER.debug("Filling additional parameters....");

		mojoSettings.setSimpleFidderPackage(this.simpleFidderPackage);
		mojoSettings.setProject(this.project);
		mojoSettings.setOutputDirectory(this.outputDirectory);
		mojoSettings.setTestSourceDirectory(this.testSourceDirectory);

		mojoSettings.setFileFilterInclusions(listOrNull(this.fileFilterInclusions));
		mojoSettings.setFileFilterExclusions(listOrNull(this.fileFilterExclusions));

		LOGGER.debug("Filling fidder parameters....");

		FidderOptions fidderOptions = new FidderOptions();

		fidderOptions.setStaticMethods(this.staticMethods);
		fidderOptions.setCanIHazFidder(this.canIHazFidder);
		fidderOptions.setInstanceName(this.simpleFidderInstanceName);
		fidderOptions.setCreateMethodName(this.simpleFidderCreateMethodName);
		fidderOptions.setStringsLength(this.stringsLength);
		fidderOptions.setIntegerLimit(this.integerLimit);
		fidderOptions.setFloatPointFromLimit(this.floatPointFromLimit);
		fidderOptions.setFloatPointToLimit(this.floatPointToLimit);
		fidderOptions.setDataStructureSize(this.dataStructureSize);

		mojoSettings.setFidderOptions(fidderOptions);

		LOGGER.debug("Processing found classes....");

		FactoryFileSpitter factoryFileSpitter = new FactoryFileSpitter(mojoSettings);
		factoryFileSpitter.writeAll();

		if (this.canIHazFidder) {
			LOGGER.debug("Exporting helper files....");

			FreeMarkerHandler freeMarkerHandler = new FreeMarkerHandler();
			freeMarkerHandler.handsOnTemplate(this.testSourceDirectory, this.simpleFidderPackage,
					simpleFidderCreateMethodName);
		}

		LOGGER.debug("Task finished have a nice day ;-)....");
	}

	private List<Container> createContainers() {
		List<Container> containers = new LinkedList<>();
		if (this.pojoPackages != null && !this.pojoPackages.isEmpty()) {
			for (int i = 0; i < this.pojoPackages.size(); i++) {
				Container container = new Container();

				String factoryQualifiedName = this.factoryQualifiedNames.get(i);
				int separator = factoryQualifiedName.lastIndexOf(Constants.DOT_CHAR);

				String pojoPackage = null;
				String factoryPackageSegment = factoryQualifiedName.substring(0, separator);
				String factoryNameSegment = null;

				if (factoryQualifiedName.endsWith(Constants.FACTORY_DEFAULT_NAME)) {
					pojoPackage = Constants.EMPTY_SPACE;
					factoryPackageSegment = Constants.EMPTY_SPACE;
					factoryNameSegment = Constants.FACTORY_DEFAULT_NAME;
				}
				else {
					pojoPackage = this.pojoPackages.get(i);
					factoryNameSegment = factoryQualifiedName.substring(separator + 1, factoryQualifiedName.length());
				}

				container.setFactoryPackage(factoryPackageSegment);
				container.setFactoryName(factoryNameSegment);
				container.setPojoPackage(pojoPackage);

				container.setPojoTargetClassFolder(PathUtils.packageAsFile(this.outputDirectory, pojoPackage));
				container.setFactorySrcTestFolder(
						PathUtils.packageAsFile(this.testSourceDirectory, factoryPackageSegment, true));

				containers.add(container);
			}
		}
		return containers;
	}

	private <T> List<T> listOrNull(final List<T> list) {
		if (list == null || list.isEmpty()) {
			return Collections.emptyList();
		}
		return list;
	}
}