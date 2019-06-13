package net.psybit.pojo;

import java.io.File;

/**
 * Project neccesary info to create the corresponding pojo factories
 * 
 * @author n3k0
 *
 */
public class FidderOptions {

	private File testSourceDirectory;
	private int stringsLength;
	private int integerLimit;
	private double floatPointFromLimit;
	private double floatPointToLimit;
	private int dataStructureSize;
	private boolean staticMethods;
	private boolean canIHazFidder;
	private String instanceName;
	private String createMethodName;

	public File getTestSourceDirectory() {
		return testSourceDirectory;
	}

	public void setTestSourceDirectory(File testSourceDirectory) {
		this.testSourceDirectory = testSourceDirectory;
	}

	public int getStringsLength() {
		return stringsLength;
	}

	public void setStringsLength(int stringsLength) {
		this.stringsLength = stringsLength;
	}

	public int getIntegerLimit() {
		return integerLimit;
	}

	public void setIntegerLimit(int integerLimit) {
		this.integerLimit = integerLimit;
	}

	public double getFloatPointFromLimit() {
		return floatPointFromLimit;
	}

	public void setFloatPointFromLimit(double floatPointFromLimit) {
		this.floatPointFromLimit = floatPointFromLimit;
	}

	public double getFloatPointToLimit() {
		return floatPointToLimit;
	}

	public void setFloatPointToLimit(double floatPointToLimit) {
		this.floatPointToLimit = floatPointToLimit;
	}

	public int getDataStructureSize() {
		return dataStructureSize;
	}

	public void setDataStructureSize(int dataStructureSize) {
		this.dataStructureSize = dataStructureSize;
	}

	public boolean isStaticMethods() {
		return staticMethods;
	}

	public void setStaticMethods(boolean staticMethods) {
		this.staticMethods = staticMethods;
	}

	public boolean isCanIHazFidder() {
		return canIHazFidder;
	}

	public void setCanIHazFidder(boolean canIHazFidder) {
		this.canIHazFidder = canIHazFidder;
	}

	public String getInstanceName() {
		return instanceName;
	}

	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}

	public String getCreateMethodName() {
		return createMethodName;
	}

	public void setCreateMethodName(String createMethodName) {
		this.createMethodName = createMethodName;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FidderOptions [");
		if (testSourceDirectory != null)
			builder.append("testSourceDirectory=").append(testSourceDirectory).append(", ");
		builder.append("stringsLength=").append(stringsLength).append(", integerLimit=").append(integerLimit)
				.append(", floatPointFromLimit=").append(floatPointFromLimit).append(", floatPointToLimit=")
				.append(floatPointToLimit).append(", dataStructureSize=").append(dataStructureSize)
				.append(", staticMethods=").append(staticMethods).append(", canIHazFidder=").append(canIHazFidder)
				.append(", ");
		if (instanceName != null)
			builder.append("instanceName=").append(instanceName).append(", ");
		if (createMethodName != null)
			builder.append("createMethodName=").append(createMethodName);
		builder.append("]");
		return builder.toString();
	}
}