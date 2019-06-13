package net.psybit.utils;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import net.psybit.exception.MotherFactoryException;

/**
 * Validate input data
 *
 * @author n3k0
 *
 */
public class Validator {

	private Validator() {}

	public static void validateParameter(Class<?> klazz, final String name, Object parameter) {
		if (String.class.equals(klazz)) {
			validateString((String) parameter, name);
		}
		if (List.class.equals(klazz)) {
			validateList((List<?>) parameter, name);
		}
	}

	public static void validateListSameSize(final List<?> first, final List<?> second) {
		if (first != null && second != null && first.size() != second.size()) {
			detonateRuntimeException(String.format("%s and the field: %s", first, second),
					new IllegalArgumentException());
		}
	}

	private static void validateList(final List<?> parameter, final String name) {
		if (parameter == null || parameter.isEmpty()) {
			detonateRuntimeException(name, new NullPointerException());
		}
	}

	private static void validateString(String parameter, final String name) {
		if (StringUtils.isBlank(parameter)) {
			detonateRuntimeException(name, new NullPointerException());
		}
	}

	public static void detonateRuntimeException(String name, Throwable throwable) {
		throw new MotherFactoryException(String.format("The field: %s does not contain any valid value!!", name),
				throwable);
	}
}