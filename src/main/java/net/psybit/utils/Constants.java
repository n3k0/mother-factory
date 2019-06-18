package net.psybit.utils;

import java.io.File;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * ... This don't need additional description
 *
 * @author n3k0
 *
 */
public class Constants {

	private Constants() {}

	public static final String DOT = ".";
	public static final char DOT_CHAR = '.';
	public static final String SLASH = File.separator;
	public static final String SLASH_REGEX = "\\" + File.separator;
	public static final char SLASH_CHAR = File.separator.charAt(0);
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String SERIAL_VERSION_UID = "serialVersionUID";
	public static final String NEW_$T = "new $T(";
	public static final String $T_DOT_VALUE_OF = "$T.valueOf(";
	public static final String EMPTY_SPACE = "";
	public static final CharSequence CLASS_SUFFIX = ".class";

	private static final Set<Class<?>> PROJECT_CLASSES = Collections.synchronizedSet(new HashSet<Class<?>>());
	public static final String FACTORY_DEFAULT_NAME = "FatFactory";
	
	public static Set<Class<?>> projectClasses(){
		return PROJECT_CLASSES;
	}
}