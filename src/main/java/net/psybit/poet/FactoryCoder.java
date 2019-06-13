package net.psybit.poet;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.lang.model.element.Modifier;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.MethodSpec.Builder;

import net.psybit.feeder.ComplexFidder;
import net.psybit.pojo.FidderOptions;
import net.psybit.pojo.SentenceWithParams;
import net.psybit.utils.Constants;

/**
 * Takes the classes loaded, and generates the corresponding setter/getter set
 * for each factory
 *
 * @author n3k0
 *
 */
public class FactoryCoder {

	private static final Logger LOGGER = LoggerFactory.getLogger(FactoryCoder.class);

	private static final List<String> TAKEN_NAMES = new ArrayList<>();

	private ComplexFidder complexFidder;
	private FidderOptions fidderOptions;

	public FactoryCoder(final FidderOptions fidderOptions) {
		this.complexFidder = ComplexFidder.getInstance(fidderOptions);

		this.fidderOptions = fidderOptions;
	}

	public List<MethodSpec> generateBody() {
		List<MethodSpec> specs = null;

		specs = new ArrayList<>(Constants.projectClasses().size());

		for (Class<?> klazz : Constants.projectClasses()) {
			try {
				specs.add(this.createPojo(klazz));
			}
			catch (IllegalArgumentException e) {
				LOGGER.error("Can't generate code for {} class, check if it is a valid class.", klazz);
			}
		}
		return specs;
	}

	public MethodSpec createPojo(final Class<?> klazz) {
		String name = buildName(klazz);

		Builder methodSpecBuilder = MethodSpec.methodBuilder(name);

		methodSpecBuilder.addModifiers(Modifier.PUBLIC);

		if (this.fidderOptions.isStaticMethods()) {
			methodSpecBuilder.addModifiers(Modifier.STATIC);
		}

		methodSpecBuilder.returns(klazz);

		LOGGER.debug("Generating getter/setter for {}", klazz);

		methodSpecBuilder.addCode(this.generateSetGetPair(klazz));

		return methodSpecBuilder.build();
	}

	CodeBlock generateSetGetPair(final Class<?> klazz) {
		CodeBlock.Builder codeBlockBuilder = CodeBlock.builder();

		SentenceWithParams constructor = complexFidder.initializeConstructorSentence(klazz,
				this.fidderOptions.isCanIHazFidder());

		codeBlockBuilder.addStatement(constructor.getSentence(), constructor.getParameters());

		if (!constructor.isByConstructor()
				&& (klazz.getDeclaredFields() != null && klazz.getDeclaredFields().length > 0)) {

			List<Field> fields = fieldList(klazz);

			for (Field field : fields) {
				String fieldName = field.getName();

				if (!(complexFidder.isStaticOrFinal(field) || Constants.SERIAL_VERSION_UID.equals(fieldName))) {

					Class<?> fieldType = field.getType();
					Class<?> genericTypeClassOne = null;
					Class<?> genericTypeClassTwo = null;

					if (ParameterizedType.class.isAssignableFrom(field.getGenericType().getClass())) {
						ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();

						Type[] types = parameterizedType.getActualTypeArguments();

						Type typeOne = types[0];
						genericTypeClassOne = (Class<?>) typeOne;

						if (types.length > 1) {
							Type typeTwo = parameterizedType.getActualTypeArguments()[1];
							genericTypeClassTwo = (Class<?>) typeTwo;
						}
					}

					String fieldNameUpper = StringUtils.capitalize(fieldName);

					SentenceWithParams sentenceWithParams = complexFidder.createComplex(fieldType, fieldNameUpper,
							this.fidderOptions.isCanIHazFidder(), genericTypeClassOne, genericTypeClassTwo);

					LOGGER.debug("Sentence built {}", sentenceWithParams);

					codeBlockBuilder.add(sentenceWithParams.getSentence(), sentenceWithParams.getParameters());
				}
			}
		}
		codeBlockBuilder.addStatement("return pojo");
		return codeBlockBuilder.build();
	}

	String buildName(Class<?> klazz) {
		String prospect = this.fidderOptions.getCreateMethodName() + klazz.getSimpleName();

		if (TAKEN_NAMES.contains(prospect)) {
			String pkg = klazz.getPackage().getName();
			String pkgPrefix = pkg.substring(pkg.lastIndexOf('.') + 1);
			prospect = this.fidderOptions.getCreateMethodName() + StringUtils.capitalize(pkgPrefix)
					+ klazz.getSimpleName();
		}
		TAKEN_NAMES.add(prospect);

		return prospect;
	}

	List<Field> fieldList(final Class<?> klazz) {
		List<Field> fields = Arrays.asList(klazz.getDeclaredFields());

		Collections.sort(fields, new Comparator<Field>() {

			@Override
			public int compare(Field o1, Field o2) {
				return o1.getType().getSimpleName().compareTo(o2.getType().getSimpleName());
			}
		});

		return fields;
	}
}