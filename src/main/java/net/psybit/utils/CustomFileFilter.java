package net.psybit.utils;

import java.io.File;
import java.io.FileFilter;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class CustomFileFilter implements FileFilter {

	private List<String> inCriterias;
	private List<String> exCriterias;

	public CustomFileFilter(final List<String> inCriterias, List<String> exCriterias) {
		this.inCriterias = inCriterias;
		this.exCriterias = exCriterias;
	}

	@Override
	public boolean accept(File file) {
		if ((this.inCriterias == null || this.inCriterias.isEmpty())
				&& (this.exCriterias == null || this.exCriterias.isEmpty())) {
			return true;
		}

		boolean resultPlus = searchInCriterias(inCriterias, file);
		boolean resultMinus = searchInCriterias(exCriterias, file);

		if (resultMinus) {
			return false;
		}
		else if (resultPlus) {
			return true;
		}
		return true;
	}

	boolean searchInCriterias(final List<String> criterias, final File file) {

		boolean result = false;

		for (String criteria : criterias) {
			if (isPackage(criteria) && matchDirHierarchy(file, criteria)) {
				return true;
			}
		}
		for (String criteria : criterias) {
			String fileName = file.getName();

			if (criteria.startsWith("^") || criteria.endsWith("$")) {
				result = Pattern.compile(criteria).matcher(fileName).find();
			}
			else {
				result = Pattern.compile(criteria).matcher(fileName).matches();
			}
			if (result) {
				break;
			}
		}
		return result;
	}

	boolean matchDirHierarchy(final File file, final String criteria) {
		if (isPackage(criteria)) {

			String[] dirs = criteria.split("\\.");
			int count = countMatches(criteria);

			File parent = file.getParentFile();

			for (int i = count; i >= 0; i--) {
				String name = parent.getName();

				if (!dirs[i].equals(name)) {
					return false;
				}
				parent = parent.getParentFile();
			}
		}
		return true;
	}

	boolean isPackage(final String toCheck) {
		return countMatches(toCheck) > 1;
	}

	int countMatches(final String toCheck) {
		return StringUtils.countMatches(toCheck, Constants.DOT_CHAR);
	}
}