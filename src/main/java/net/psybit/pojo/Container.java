package net.psybit.pojo;

import java.io.File;

/**
 * Container (duh!) for factory - pojos relation info, and file system location
 *
 * @author n3k0
 *
 */
public class Container {

	private String pojoPackage;
	private String factoryPackage;
	private String factoryName;

	private File pojoTargetClassFolder;
	private File pojoTargetClassFile;//<<<< ver si se usa si no alv:

	private File factorySrcTestFolder;

	public String getPojoPackage() {
		return pojoPackage;
	}

	public void setPojoPackage(String pojoPackage) {
		this.pojoPackage = pojoPackage;
	}

	public String getFactoryPackage() {
		return factoryPackage;
	}

	public void setFactoryPackage(String factoryPackage) {
		this.factoryPackage = factoryPackage;
	}

	public String getFactoryName() {
		return factoryName;
	}

	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}

	public File getPojoTargetClassFolder() {
		return pojoTargetClassFolder;
	}

	public void setPojoTargetClassFolder(File pojoTargetClassFolder) {
		this.pojoTargetClassFolder = pojoTargetClassFolder;
	}

	public File getPojoTargetClassFile() {
		return pojoTargetClassFile;
	}

	public void setPojoTargetClassFile(File pojoTargetClassFile) {
		this.pojoTargetClassFile = pojoTargetClassFile;
	}

	public File getFactorySrcTestFolder() {
		return factorySrcTestFolder;
	}

	public void setFactorySrcTestFolder(File factorySrcTestFolder) {
		this.factorySrcTestFolder = factorySrcTestFolder;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Container [");
		if (pojoPackage != null)
			builder.append("pojoPackage=").append(pojoPackage).append(", ");
		if (factoryPackage != null)
			builder.append("factoryPackage=").append(factoryPackage).append(", ");
		if (factoryName != null)
			builder.append("factoryName=").append(factoryName).append(", ");
		if (pojoTargetClassFolder != null)
			builder.append("pojoTargetClassFolder=").append(pojoTargetClassFolder).append(", ");
		if (pojoTargetClassFile != null)
			builder.append("pojoTargetClassFile=").append(pojoTargetClassFile).append(", ");
		if (factorySrcTestFolder != null)
			builder.append("factorySrcTestFolder=").append(factorySrcTestFolder);
		builder.append("]");
		return builder.toString();
	}
}