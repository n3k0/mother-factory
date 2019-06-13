package net.psybit.feeder;

import java.lang.reflect.Constructor;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.psybit.pojo.FidderOptions;
import net.psybit.pojo.SentenceWithParams;
import net.psybit.utils.Constants;

/**
 *
 * Configure the appropriate String that Javapoet will use as format to build
 * the sentences of getters/setters for a specific data type
 *
 * @author n3k0
 */
public class ComplexFidder {

	private static final Logger LOGGER = LoggerFactory.getLogger(ComplexFidder.class);

	private static ComplexFidder complexFidder;

	private FidderOptions fidderOptions;
	private SimpleFidder simpleFeeder;

	private ComplexFidder(final FidderOptions fidderOptions) {
		this.fidderOptions = fidderOptions;

		simpleFeeder = SimpleFidder.getInstance(this.fidderOptions.getStringsLength(),
				this.fidderOptions.getIntegerLimit(), this.fidderOptions.getFloatPointFromLimit(),
				this.fidderOptions.getFloatPointToLimit(), this.fidderOptions.getDataStructureSize());
	}

	public static final synchronized ComplexFidder getInstance(final FidderOptions fidderOptions) {
		if (complexFidder == null) {
			complexFidder = new ComplexFidder(fidderOptions);
		}
		return complexFidder;
	}

	public SentenceWithParams createComplex(final Class<?> klazz, String fieldName, final boolean canIHazFidder,
			final Object... additionals) {

		LOGGER.debug("Trying to build field with class {} name {} canIHazFidder {} additionals {} ", klazz.getName(),
				fieldName, canIHazFidder, additionals);

		StringBuilder sentence = new StringBuilder(0);
		List<Object> parameters = new ArrayList<>(0);
		parameters.add(fieldName);

		sentence.append(pojoSetSyntax(parameters, klazz, canIHazFidder, additionals)).append(";\n");

		SentenceWithParams sentenceWithParams = new SentenceWithParams();

		sentenceWithParams.setSentence(sentence.toString());
		sentenceWithParams.addParameters(parameters);

		return sentenceWithParams;
	}

	String pojoSetSyntax(final List<Object> parameters, Class<?> klazz, final boolean canIHazFidder,
			final Object... additionals) {

		String valueSyntax = valueSyntax(parameters, klazz, canIHazFidder, additionals);

		return String.format("pojo.set$L(%s)", valueSyntax);
	}

	String valueSyntax(final List<Object> parameters, Class<?> klazz, final boolean canIHazFidder,
			final Object... additionals) {

		String pojoSyntaxValue = null;

		if (List.class.isAssignableFrom(klazz)) {
			parameters.add(Arrays.class);

			pojoSyntaxValue = "$T.asList(";

			pojoSyntaxValue += valueSyntax(parameters, (Class<?>) additionals[0], canIHazFidder, additionals);

			pojoSyntaxValue += ")";
		}
		else if (Set.class.isAssignableFrom(klazz)) {
			parameters.add(HashSet.class);
			parameters.add((Class<?>) additionals[0]);

			pojoSyntaxValue = "new $T<$T>(){\n\tprivate static final long serialVersionUID = 1L;\n\t{\n\t add("
					+ valueSyntax(parameters, (Class<?>) additionals[0], canIHazFidder, additionals) + "); \n\t}\n}";
		}
		else if (Map.class.isAssignableFrom(klazz)) {
			parameters.add(HashMap.class);
			parameters.add((Class<?>) additionals[0]);
			parameters.add((Class<?>) additionals[1]);

			pojoSyntaxValue = "new $T<$T, $T>() {\n\tprivate static final long serialVersionUID = 1L;\n{ \n\t\tput("
					+ valueSyntax(parameters, (Class<?>) additionals[0], canIHazFidder, additionals) + ", "
					+ valueSyntax(parameters, (Class<?>) additionals[1], canIHazFidder, additionals) + "); \n\t}\n}";

		}
		else if (klazz.isInterface()) {
			parameters.add(klazz);
			return "($T)null";
		}
		else if (klazz.isArray()) {
			parameters.add(klazz);
			Class<?> implementedClass = klazz.getComponentType();
			return "new $T{" + valueSyntax(parameters, implementedClass, canIHazFidder) + "}";
		}
		else {
			pojoSyntaxValue = "";

			if (Constants.projectClasses().contains(klazz)) {
				pojoSyntaxValue += this.fidderOptions.getCreateMethodName();
				pojoSyntaxValue += klazz.getSimpleName();
				pojoSyntaxValue += ("()");
			}
			else {
				if (canIHazFidder) {
					pojoSyntaxValue += this.fidderOptions.getInstanceName() + "."
							+ this.fidderOptions.getCreateMethodName() + "($T.class)";
				}
				else {
					pojoSyntaxValue += this.literal(klazz, canIHazFidder);
				}
				if (pojoSyntaxValue.contains("$T")) {
					parameters.add(klazz);
				}
			}
		}
		return pojoSyntaxValue;
	}

	public String literal(final Class<?> klazz, final boolean canIHazFidder) {
		if (String.class.isAssignableFrom(klazz)) {
			return "\"" + simpleFeeder.string() + "\"";
		}
		else if (Long.TYPE.isAssignableFrom(klazz)) {
			return simpleFeeder.longValue() + "L";
		}
		else if (Long.class.isAssignableFrom(klazz)) {
			return Constants.$T_DOT_VALUE_OF + "\"" + simpleFeeder.longValue() + "L\")";
		}
		else if (Integer.TYPE.isAssignableFrom(klazz)) {
			return simpleFeeder.intValue().toString();
		}
		else if (Integer.class.isAssignableFrom(klazz)) {
			return Constants.$T_DOT_VALUE_OF + "\"" + simpleFeeder.intValue() + "\")";
		}
		else if (Boolean.TYPE.isAssignableFrom(klazz)) {
			return simpleFeeder.boolValue().toString();
		}
		else if (Boolean.class.isAssignableFrom(klazz)) {
			return Constants.$T_DOT_VALUE_OF + "\"" + simpleFeeder.boolValue() + "\")";
		}
		else if (Double.TYPE.isAssignableFrom(klazz)) {
			return simpleFeeder.doubleValue().toString();
		}
		else if (Double.class.isAssignableFrom(klazz)) {
			return Constants.$T_DOT_VALUE_OF + "\"" + simpleFeeder.doubleValue() + "\")";
		}
		else if (Float.TYPE.isAssignableFrom(klazz)) {
			return simpleFeeder.floatValue() + "f";
		}
		else if (Float.class.isAssignableFrom(klazz)) {
			return Constants.$T_DOT_VALUE_OF + "\"" + simpleFeeder.floatValue() + "f\")";
		}
		else if (Byte.TYPE.isAssignableFrom(klazz)) {
			return "($T)" + simpleFeeder.byteValue();
		}
		else if (Byte.class.isAssignableFrom(klazz)) {
			return Constants.$T_DOT_VALUE_OF + "\"" + simpleFeeder.byteValue() + "\")";
		}
		else if (Short.TYPE.isAssignableFrom(klazz)) {
			return "($T)" + simpleFeeder.shortValue();
		}
		else if (Short.class.isAssignableFrom(klazz)) {
			return Constants.$T_DOT_VALUE_OF + "\"" + simpleFeeder.shortValue() + "\")";
		}
		else if (Character.class.isAssignableFrom(klazz)) {
			return Constants.$T_DOT_VALUE_OF + "'" + simpleFeeder.charValue() + "')";
		}
		else if (Character.TYPE.isAssignableFrom(klazz)) {
			return "'" + simpleFeeder.charValue() + "'";
		}
		else if (BigInteger.class.isAssignableFrom(klazz)) {
			return Constants.NEW_$T + "\"" + simpleFeeder.intValue() + "\")";
		}
		else if (BigDecimal.class.isAssignableFrom(klazz)) {
			return Constants.NEW_$T + "\"" + simpleFeeder.doubleValue() + "\")";
		}
		else if (Date.class.isAssignableFrom(klazz)) {
			return Constants.NEW_$T + simpleFeeder.date().getTime() + "L)";
		}
		else if (Timestamp.class.isAssignableFrom(klazz)) {
			return Constants.NEW_$T + simpleFeeder.timestamp().getTime() + "L)";
		}
		else if (Enum.class.isAssignableFrom(klazz) || klazz.isEnum()) {
			Object[] enumValues = klazz.getEnumConstants();

			if (enumValues != null && enumValues.length > 0) {
				return "$T." + enumValues[simpleFeeder.intValue(enumValues.length)].toString();
			}
			else {
				return "$T.values()[0]";
			}
		}
		if (klazz.isInterface()) {
			return "($T)null";
		}
		else {
			return buildConstructorSentence(klazz, canIHazFidder).getSentence();
		}
	}

	public SentenceWithParams initializeConstructorSentence(final Class<?> klazz, final boolean canIHazFidder) {
		SentenceWithParams sentenceWithParams = new SentenceWithParams();
		sentenceWithParams.addParameters(klazz);

		StringBuilder builder = new StringBuilder("$T pojo = ");

		SentenceWithParams constructorBody = buildConstructorSentence(klazz, canIHazFidder);

		builder.append(constructorBody.getSentence());

		sentenceWithParams.setByConstructor(constructorBody.isByConstructor());
		sentenceWithParams.setSentence(builder.toString());
		sentenceWithParams.addParameters(constructorBody.getParameters());

		return sentenceWithParams;
	}

	SentenceWithParams buildConstructorSentence(final Class<?> klazz, final boolean canIHazFidder) {
		SentenceWithParams sentenceWithParams = new SentenceWithParams();

		StringBuilder builder = new StringBuilder(0);

		Constructor<?> selected = selectByParameters(klazz.getDeclaredConstructors());

		if (selected == null) {
			Method[] methods = klazz.getMethods();

			for (Method method : methods) {
				if (isStaticOrFinal(method) && method.getReturnType().isAssignableFrom(klazz)) {
					builder.append("$T." + method.getName() + "()");
					break;
				}
			}
			sentenceWithParams.addParameters(klazz);
		}
		else if (selected.getParameterTypes().length > 0) {
			sentenceWithParams.setByConstructor(true);
			sentenceWithParams.addParameters(klazz);

			builder.append(Constants.NEW_$T);
			int parameterTypesLength = selected.getParameterTypes().length;

			for (int i = 0; i < parameterTypesLength; i++) {
				Class<?> parameterType = selected.getParameterTypes()[i];

				final String sentence;

				if (canIHazFidder) {
					sentence = this.fidderOptions.getInstanceName() + "." + this.fidderOptions.getCreateMethodName()
							+ "($T.class)";
				}
				else {
					sentence = literal(parameterType, canIHazFidder);
				}

				builder.append(sentence);

				if (sentence.contains("$T")) {
					sentenceWithParams.addParameters(parameterType);
				}

				if (i < (parameterTypesLength - 1)) {
					builder.append(',');
				}
			}
			builder.append(')');
		}
		else {
			builder.append("new $T()");
			sentenceWithParams.addParameters(klazz);
		}

		sentenceWithParams.setSentence(builder.toString());

		return sentenceWithParams;
	}

	public boolean isStaticOrFinal(final Member member) {
		if (member != null) {
			return java.lang.reflect.Modifier.isFinal(member.getModifiers())
					|| java.lang.reflect.Modifier.isStatic(member.getModifiers());
		}
		return false;
	}

	boolean isPrivate(final Member member) {
		if (member != null) {
			return java.lang.reflect.Modifier.isPrivate(member.getModifiers());
		}
		return false;
	}

	Constructor<?> selectByParameters(Constructor<?>[] constructors) {

		if (constructors == null || constructors.length == 0) {
			return null;
		}
		else {
			if (constructors.length == 1) {
				if (isPrivate(constructors[0])) {
					return null;
				}
				else {
					return constructors[0];
				}
			}
			else {
				Arrays.sort(constructors, new Comparator<Constructor<?>>() {

					@Override
					public int compare(Constructor<?> constructor1, Constructor<?> constructor2) {

						if (constructor1.getParameterTypes().length > constructor2.getParameterTypes().length) {
							return 1;
						}
						if (constructor1.getParameterTypes().length < constructor2.getParameterTypes().length) {
							return -1;
						}
						else {
							return 0;
						}
					}
				});

				for (int i = 0; i < constructors.length; i++) {
					if (!isPrivate(constructors[i])) {
						return constructors[i];
					}
				}
				return null;
			}
		}
	}
}