package net.psybit.pojo;

import java.io.File;
import java.util.List;

import org.apache.maven.project.MavenProject;

/**
 * Project neccesary info to create the corresponding pojo factories
 * 
 * @author n3k0
 *
 */
public class MojoSettings {

	private MavenProject project;

	private String simpleFidderPackage;
	private List<Container> containers;
	private File outputDirectory;
	private File testSourceDirectory;
	private FidderOptions fidderOptions;
	private List<String> fileFilterExclusions;
	private List<String> fileFilterInclusions;

	public MavenProject getProject() {
		return project;
	}

	public void setProject(MavenProject project) {
		this.project = project;
	}

	public String getSimpleFidderPackage() {
		return simpleFidderPackage;
	}

	public void setSimpleFidderPackage(String simpleFidderPackage) {
		this.simpleFidderPackage = simpleFidderPackage;
	}

	public List<Container> getContainers() {
		return containers;
	}

	public void setContainers(List<Container> containers) {
		this.containers = containers;
	}

	public File getOutputDirectory() {
		return outputDirectory;
	}

	public void setOutputDirectory(File outputDirectory) {
		this.outputDirectory = outputDirectory;
	}

	public File getTestSourceDirectory() {
		return testSourceDirectory;
	}

	public void setTestSourceDirectory(File testSourceDirectory) {
		this.testSourceDirectory = testSourceDirectory;
	}

	public FidderOptions getFidderOptions() {
		return fidderOptions;
	}

	public void setFidderOptions(FidderOptions fidderOptions) {
		this.fidderOptions = fidderOptions;
	}

	public List<String> getFileFilterExclusions() {
		return fileFilterExclusions;
	}

	public void setFileFilterExclusions(List<String> fileFilterExclusions) {
		this.fileFilterExclusions = fileFilterExclusions;
	}

	public List<String> getFileFilterInclusions() {
		return fileFilterInclusions;
	}

	public void setFileFilterInclusions(List<String> fileFilterInclusions) {
		this.fileFilterInclusions = fileFilterInclusions;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(0);
		builder.append("MojoSettings [");
		if (project != null)
			builder.append("project=").append(project).append(", ");
		if (simpleFidderPackage != null)
			builder.append("simpleFidderPackage=").append(simpleFidderPackage).append(", ");
		if (containers != null)
			builder.append("containers=").append(containers).append(", ");
		if (outputDirectory != null)
			builder.append("outputDirectory=").append(outputDirectory).append(", ");
		if (testSourceDirectory != null)
			builder.append("testSourceDirectory=").append(testSourceDirectory).append(", ");
		if (fidderOptions != null)
			builder.append("fidderOptions=").append(fidderOptions).append(", ");
		if (fileFilterExclusions != null)
			builder.append("fileFilterExclusions=").append(fileFilterExclusions).append(", ");
		if (fileFilterInclusions != null)
			builder.append("fileFilterInclusions=").append(fileFilterInclusions);
		builder.append("]");
		return builder.toString();
	}
}