package net.psybit.poet;

import java.io.IOException;
import java.util.Date;

import javax.lang.model.element.Modifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeSpec.Builder;

import net.psybit.loader.ClassReader;
import net.psybit.pojo.Container;
import net.psybit.pojo.MojoSettings;
import net.psybit.utils.Constants;

/**
 * Spits a java file previously filled with all the information for a factory
 *
 * @author n3k0
 *
 */
public class FactoryFileSpitter {

	private static final Logger LOGGER = LoggerFactory.getLogger(FactoryFileSpitter.class);

	private ClassReader classReader;

	private MojoSettings mojoSettings;

	private FieldSpec simpleFidderSpec;

	public FactoryFileSpitter(final MojoSettings mojoSettings) {
		this.mojoSettings = mojoSettings;
		this.classReader = ClassReader.getInstance(mojoSettings);
	}

	public void writeAll() {
		for (Container container : mojoSettings.getContainers()) {
			write(container);
		}
	}

	public void write(final Container container) {

		this.classReader.load(container);

		if (!Constants.projectClasses().isEmpty()) {
			FactoryCoder factoryCoder = new FactoryCoder(mojoSettings.getFidderOptions());

			Builder typeSpecBuilder = TypeSpec.classBuilder(container.getFactoryName());
			typeSpecBuilder.addModifiers(Modifier.PUBLIC);

			if (mojoSettings.getFidderOptions().isCanIHazFidder()) {
				typeSpecBuilder.addField(simpleFidderSpec());
			}
			typeSpecBuilder.addMethods(factoryCoder.generateBody());

			JavaFile javaFile = JavaFile.builder(container.getFactoryPackage(), typeSpecBuilder.build())
					.skipJavaLangImports(true).build();

			LOGGER.debug("Writing {} at {}", javaFile, new Date());

			try {
				javaFile.writeTo(mojoSettings.getTestSourceDirectory());
			}
			catch (IOException e) {
				LOGGER.error("FactoryFileSpitter.write -->> ", e);
			}
		}
	}

	private FieldSpec simpleFidderSpec() {
		if (simpleFidderSpec == null) {
			return FieldSpec.builder(
					ClassName.get(this.mojoSettings.getSimpleFidderPackage(), "SimpleFidder")
					, this.mojoSettings.getFidderOptions().getInstanceName()
					, Modifier.PRIVATE, Modifier.STATIC
					, Modifier.FINAL).initializer("SimpleFidder.getInstance()").build();
		}
		return simpleFidderSpec;
	}
}