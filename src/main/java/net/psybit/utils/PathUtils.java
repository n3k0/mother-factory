package net.psybit.utils;

import java.io.File;

import org.apache.commons.lang3.StringUtils;

import net.psybit.exception.MotherFactoryException;

/**
 * Builds package/paths with package/paths strings
 * 
 * @author n3k0
 *
 */
public class PathUtils {

	private PathUtils() {}

	public static String toPath(Object... toConvert) {
		return StringUtils.replaceChars(StringUtils.join(toConvert), Constants.DOT_CHAR, Constants.SLASH_CHAR);
	}

	public static String pathFrom(String... subPaths) {
		StringBuilder builder = new StringBuilder(0);
		for (int i = 0; i < subPaths.length; i++) {
			String subPath = subPaths[i];
			builder.append(subPath);

			if (i != (subPaths.length - 1)) {
				builder.append(Constants.SLASH);
			}
		}
		return builder.toString();
	}

	public static File packageAsFile(final File root, final String pkgTarget) {
		return packageAsFile(root, pkgTarget, false);
	}

	public static File packageAsFile(final File root, final String pkgTarget, final boolean create) {
		String pkgTargetPath = toPath(pkgTarget);

		File pkgTargetDirPath = new File(root, pkgTargetPath);

		if (!pkgTargetDirPath.exists()) {

			if (create) {
				pkgTargetDirPath.mkdirs();
			}
			else {
				throw new MotherFactoryException(String.format(
						"The folder %s does not exists, the project must be compiled to execute this plugin!",
						pkgTargetPath));
			}
		}
		return pkgTargetDirPath;
	}

	public static String fileAsPackage(String from, final String parent) {
		String pkg = StringUtils.replaceChars(parent, Constants.SLASH_CHAR, Constants.DOT_CHAR);

		pkg = pkg.substring(pkg.indexOf(from), pkg.length());

		return pkg;
	}
}