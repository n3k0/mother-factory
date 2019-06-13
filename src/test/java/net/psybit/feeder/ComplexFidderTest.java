package net.psybit.feeder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import net.psybit.pojo.FidderOptions;
import net.psybit.pojo.MojoSettings;

public class ComplexFidderTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(ComplexFidderTest.class);

	private static final String NOMBRE_CAMPO = "Campo";

	private static MojoSettings mojoSettings;
	private static FidderOptions fidderOptions;
	private static ComplexFidder COMPLEX_NO_EMBEDDED_SIMPLE_FIDDER;
	private static ComplexFidder COMPLEX_EMBEDDED_SIMPLE_FIDDER;
	private static List<String> CLASS_NAMES = Arrays.asList(CustomTwoElementsEnum.class.getCanonicalName(),
			CustomInterface.class.getCanonicalName(), CustomEmptyPojo.class.getCanonicalName(),
			CustomPojo.class.getCanonicalName());

	private StringBuilder stringBuilder;
	private List<Object> parameters;

	@BeforeTest
	public void Before() {
		stringBuilder = new StringBuilder();
		parameters = new ArrayList<>();
	}

	@BeforeClass
	public static void beforeClass() {
		fidderOptions = new FidderOptions();
		
		fidderOptions.setStringsLength(10);
		fidderOptions.setIntegerLimit(100);
		fidderOptions.setFloatPointFromLimit(10.1);
		fidderOptions.setFloatPointToLimit(100.1);
		fidderOptions.setDataStructureSize(2);

		mojoSettings = new MojoSettings();
		mojoSettings.setFidderOptions(fidderOptions);
		
		COMPLEX_NO_EMBEDDED_SIMPLE_FIDDER = ComplexFidder.getInstance(fidderOptions);

		fidderOptions.setCanIHazFidder(true);

		COMPLEX_EMBEDDED_SIMPLE_FIDDER = ComplexFidder.getInstance(fidderOptions);
	}

	@Test
	public void testCustomEnum() {
		COMPLEX_NO_EMBEDDED_SIMPLE_FIDDER.createComplex(CustomTwoElementsEnum.class, NOMBRE_CAMPO, true, true,
				stringBuilder, parameters, CLASS_NAMES);
		LOGGER.info("ComplexFidderTest.testCustomEnum >>> \n{}\n <<< OK! ", stringBuilder.toString());
	}

	@Test
	public void testCustomInterface() {
		COMPLEX_NO_EMBEDDED_SIMPLE_FIDDER.createComplex(CustomInterface.class, NOMBRE_CAMPO, true, true, stringBuilder,
				parameters, CLASS_NAMES);

		LOGGER.info("ComplexFidderTest.testCustomInterface >>> \n{}\n <<< OK! ", stringBuilder.toString());
	}

	@Test
	public void testCustomEmptyPojo() {
		COMPLEX_NO_EMBEDDED_SIMPLE_FIDDER.createComplex(CustomEmptyPojo.class, NOMBRE_CAMPO, true, true, stringBuilder,
				parameters, CLASS_NAMES);

		LOGGER.info("ComplexFidderTest.testCustomEmptyPojo >>> \n{}\n <<< OK! ", stringBuilder.toString());
	}

	@Test
	public void testCustomPojo() {
		COMPLEX_NO_EMBEDDED_SIMPLE_FIDDER.createComplex(CustomPojo.class, NOMBRE_CAMPO, true, true, stringBuilder,
				parameters, CLASS_NAMES);

		LOGGER.info("ComplexFidderTest.CustomPojo >>> \n{}\n <<< OK! ", stringBuilder.toString());
	}

	// Fidder
	@Test
	public void testCustomEnumFidder() {
		COMPLEX_EMBEDDED_SIMPLE_FIDDER.createComplex(CustomTwoElementsEnum.class, NOMBRE_CAMPO, true, true,
				stringBuilder, parameters, CLASS_NAMES);

		LOGGER.info("ComplexFidderTest.testCustomEnumFidder >>> \n{}\n <<< OK! ", stringBuilder.toString());
	}

	@Test
	public void testCustomInterfaceFidder() {
		COMPLEX_EMBEDDED_SIMPLE_FIDDER.createComplex(CustomInterface.class, NOMBRE_CAMPO, true, true, stringBuilder,
				parameters, CLASS_NAMES);

		Assert.assertNotNull(stringBuilder);

		LOGGER.info("ComplexFidderTest.testCustomInterfaceFidder >>> \n{}\n <<< OK! ", stringBuilder.toString());
	}

	@Test
	public void testCustomEmptyPojoFidder() {
		COMPLEX_EMBEDDED_SIMPLE_FIDDER.createComplex(CustomEmptyPojo.class, NOMBRE_CAMPO, true, true, stringBuilder,
				parameters, CLASS_NAMES);

		LOGGER.info("ComplexFidderTest.testCustomEmptyPojoFidder >>> \n{}\n <<< OK! ", stringBuilder.toString());
	}

	@Test
	public void testCustomPojoFidder() {
		COMPLEX_EMBEDDED_SIMPLE_FIDDER.createComplex(CustomPojo.class, NOMBRE_CAMPO, true, true, stringBuilder,
				parameters, CLASS_NAMES);

		LOGGER.info("ComplexFidderTest.CustomPojoFidder >>> \n{}\n <<< OK! ", stringBuilder.toString());
	}

	// example objects to make test
	enum CustomTwoElementsEnum {
		CUSTOM_1, CUSTOM_2
	}

	interface CustomInterface {}

	class CustomEmptyPojo {}

	class CustomPojo {
		byte _byte;
		char _char;
		short _short;
		int _int;
		long _long;
		String string;
		Byte oByte;
		Character oChar;
		Short oShort;
		Integer oInt;
		Long oLong;
		String[] arrayOneDimension;
		Integer[][] arrayTwoDimensions;
		List<Long> list;
		List<CustomPojo> customList;
		Map<String, String> map;
		Map<CustomPojo, CustomPojo> customMap;
		Set<Character> set;
		Set<CustomPojo> customSet;

		public byte get_byte() {
			return this._byte;
		}

		public void set_byte(byte _byte) {
			this._byte = _byte;
		}

		public char get_char() {
			return this._char;
		}

		public void set_char(char _char) {
			this._char = _char;
		}

		public short get_short() {
			return this._short;
		}

		public void set_short(short _short) {
			this._short = _short;
		}

		public int get_int() {
			return this._int;
		}

		public void set_int(int _int) {
			this._int = _int;
		}

		public long get_long() {
			return this._long;
		}

		public void set_long(long _long) {
			this._long = _long;
		}

		public String getString() {
			return this.string;
		}

		public void setString(String string) {
			this.string = string;
		}

		public Byte getoByte() {
			return this.oByte;
		}

		public void setoByte(Byte oByte) {
			this.oByte = oByte;
		}

		public Character getoChar() {
			return this.oChar;
		}

		public void setoChar(Character oChar) {
			this.oChar = oChar;
		}

		public Short getoShort() {
			return this.oShort;
		}

		public void setoShort(Short oShort) {
			this.oShort = oShort;
		}

		public Integer getoInt() {
			return this.oInt;
		}

		public void setoInt(Integer oInt) {
			this.oInt = oInt;
		}

		public Long getoLong() {
			return this.oLong;
		}

		public void setoLong(Long oLong) {
			this.oLong = oLong;
		}

		public String[] getArrayOneDimension() {
			return this.arrayOneDimension;
		}

		public void setArrayOneDimension(String[] arrayOneDimension) {
			this.arrayOneDimension = arrayOneDimension;
		}

		public Integer[][] getArrayTwoDimensions() {
			return this.arrayTwoDimensions;
		}

		public void setArrayTwoDimensions(Integer[][] arrayTwoDimensions) {
			this.arrayTwoDimensions = arrayTwoDimensions;
		}

		public List<Long> getList() {
			return this.list;
		}

		public void setList(List<Long> list) {
			this.list = list;
		}

		public List<CustomPojo> getCustomList() {
			return this.customList;
		}

		public void setCustomList(List<CustomPojo> customList) {
			this.customList = customList;
		}

		public Map<String, String> getMap() {
			return this.map;
		}

		public void setMap(Map<String, String> map) {
			this.map = map;
		}

		public Map<CustomPojo, CustomPojo> getCustomMap() {
			return this.customMap;
		}

		public void setCustomMap(Map<CustomPojo, CustomPojo> customMap) {
			this.customMap = customMap;
		}

		public Set<Character> getSet() {
			return this.set;
		}

		public void setSet(Set<Character> set) {
			this.set = set;
		}

		public Set<CustomPojo> getCustomSet() {
			return this.customSet;
		}

		public void setCustomSet(Set<CustomPojo> customSet) {
			this.customSet = customSet;
		}
	}

	/*
	 *
	 *
	 * private static final Pattern PATTERN_INTEGER =
	 * Pattern.compile("pojo\\.setCampo\\(([0-9]{1,100})\\)\\;\\n?");
	 *
	 * private static final Pattern PATTERN_INTEGER_FIDDER = Pattern .compile(
	 * "pojo\\.setCampo\\(instance\\.create\\(java\\.lang\\.Integer\\.class\\)\\)\\;\\n?"
	 * );
	 *
	 * private static final Pattern PATTERN_DOUBLE = Pattern
	 * .compile("pojo\\.setCampo\\(([0-9]{1,5})(\\.)?([0-9]{0,20})?\\)\\;\\n?");
	 *
	 * private static final Pattern PATTERN_DOUBLE_FIDDER = Pattern
	 * .compile("pojo\\.setCampo\\(instance\\.create\\(double\\.class\\)\\)\\;\\n?")
	 * ;
	 *
	 * private static final Pattern PATTERN_ENUM = Pattern .compile(
	 * "pojo\\.setCampo\\(java\\.lang\\.Enum\\.values\\(\\)\\[0\\]\\)\\;\\n?");
	 *
	 * private static final Pattern PATTERN_ENUM_FIDDER = Pattern .compile(
	 * "pojo\\.setCampo\\(instance\\.create\\(java\\.lang\\.Enum\\.class\\)\\)\\;\\n?"
	 * );
	 *
	 * private static final Pattern PATTERN_CUSTOM_INTERFACE = Pattern.compile(
	 * "pojo\\.setCampo\\(\\(net\\.psybit\\.feeder\\.simple\\.FakeFidderTest\\.CustomInterface\\)null\\)\\;\\n?"
	 * );
	 *
	 * private static final Pattern PATTERN_CUSTOM_INTERFACE_FIDDER =
	 * Pattern.compile(
	 * "pojo\\.setCampo\\(instance\\.create\\(net\\.psybit\\.feeder\\.simple\\.FakeFidderTest\\.CustomInterface\\.class\\)\\)\\;\\n?"
	 * );
	 *
	 * private static final Pattern PATTERN_CUSTOM_EMPTY_ENUM_FIDDER =
	 * Pattern.compile(
	 * "pojo\\.setCampo\\(instance\\.create\\(net\\.psybit\\.feeder\\.simple\\.FakeFidderTest\\.CustomEmptyEnum\\.class\\)\\)\\;\\n?"
	 * );
	 *
	 * private static final Pattern PATTERN_CUSTOM_EMPTY_ENUM = Pattern.compile(
	 * "pojo\\.setCampo\\(net\\.psybit\\.feeder\\.simple\\.FakeFidderTest\\.CustomEmptyEnum\\.values\\(\\)\\[0\\]\\)\\;\\n?"
	 * );
	 *
	 *
	 *
	 * @Test public void testInstanceIntDoubleDouble() { ComplexFidder _100_INSTANCE
	 * = ComplexFidder.instance(100, 100, 100); Assert.assertNotNull(_100_INSTANCE);
	 *
	 * LOGGER.info("testInstanceIntDoubleDouble {}", _100_INSTANCE); }
	 *
	 * @Test public void testCreatePrimitive() { CodeBlock stringBlock =
	 * DEFAULT_INSTANCE.create(double.class, NOMBRE_CAMPO, false);
	 * Assert.assertNotNull(stringBlock);
	 *
	 * String toMatch = stringBlock.toString();
	 *
	 * Assert.assertTrue(PATTERN_DOUBLE.matcher(toMatch).matches());
	 *
	 * LOGGER.info("testCreatePrimitive {}", stringBlock); }
	 *
	 * @Test public void testCreatePrimitiveCanIHazFidder() { CodeBlock stringBlock
	 * = DEFAULT_INSTANCE.create(double.class, NOMBRE_CAMPO, true);
	 * Assert.assertNotNull(stringBlock);
	 *
	 * String toMatch = stringBlock.toString();
	 *
	 * Assert.assertTrue(PATTERN_DOUBLE_FIDDER.matcher(toMatch).matches());
	 *
	 * LOGGER.info("testCreatePrimitive {}", stringBlock); }
	 *
	 * @Test public void testCreateEnum() { CodeBlock stringBlock =
	 * DEFAULT_INSTANCE.create(Enum.class, NOMBRE_CAMPO, false);
	 * Assert.assertNotNull(stringBlock);
	 *
	 * String toMatch = stringBlock.toString();
	 *
	 * Assert.assertTrue(PATTERN_ENUM.matcher(toMatch).matches());
	 *
	 * LOGGER.info("testCreateEnum {}", stringBlock); }
	 *
	 * @Test public void testCreateCustomEmptyEnum() { CodeBlock stringBlock =
	 * DEFAULT_INSTANCE.create(CustomEmptyEnum.class, NOMBRE_CAMPO, false);
	 * Assert.assertNotNull(stringBlock);
	 *
	 * String toMatch = stringBlock.toString();
	 *
	 * Assert.assertTrue(PATTERN_CUSTOM_EMPTY_ENUM.matcher(toMatch).matches());
	 *
	 * LOGGER.info("testCreateEnum {}", stringBlock); }
	 *
	 * @Test public void testCreateCustomEmptyEnumCanIHazFidder() { CodeBlock
	 * stringBlock = DEFAULT_INSTANCE.create(CustomEmptyEnum.class, NOMBRE_CAMPO,
	 * true); Assert.assertNotNull(stringBlock);
	 *
	 * String toMatch = stringBlock.toString();
	 *
	 * Assert.assertTrue(PATTERN_CUSTOM_EMPTY_ENUM_FIDDER.matcher(toMatch).matches()
	 * );
	 *
	 * LOGGER.info("testCreateEnum {}", stringBlock); }
	 *
	 * @Test public void testCreateEnumCanIHazFidder() { CodeBlock stringBlock =
	 * DEFAULT_INSTANCE.create(Enum.class, NOMBRE_CAMPO, true);
	 * Assert.assertNotNull(stringBlock);
	 *
	 * String toMatch = stringBlock.toString();
	 *
	 * Assert.assertTrue(PATTERN_ENUM_FIDDER.matcher(toMatch).matches());
	 *
	 * LOGGER.info("testCreateEnum {}", stringBlock); }
	 *
	 * @Test public void testCreateInteger() { CodeBlock stringBlock =
	 * DEFAULT_INSTANCE.create(Integer.class, NOMBRE_CAMPO, false);
	 * Assert.assertNotNull(stringBlock);
	 *
	 * String toMatch = stringBlock.toString();
	 *
	 * Assert.assertTrue(PATTERN_INTEGER.matcher(toMatch).matches());
	 *
	 * LOGGER.info("testCreateObject {}", stringBlock); }
	 *
	 * @Test public void testCreateObjectCanIHazFidder() { CodeBlock stringBlock =
	 * DEFAULT_INSTANCE.create(Integer.class, NOMBRE_CAMPO, true);
	 * Assert.assertNotNull(stringBlock);
	 *
	 * String toMatch = stringBlock.toString();
	 *
	 * Assert.assertTrue(PATTERN_INTEGER_FIDDER.matcher(toMatch).matches());
	 *
	 * LOGGER.info("testCreateObject {}", stringBlock); }
	 *
	 * @Test public void testCreateCustomInterface() { CodeBlock stringBlock =
	 * DEFAULT_INSTANCE.create(CustomInterface.class, NOMBRE_CAMPO, false);
	 * Assert.assertNotNull(stringBlock);
	 *
	 * String toMatch = stringBlock.toString();
	 *
	 * Assert.assertTrue(PATTERN_CUSTOM_INTERFACE.matcher(toMatch).matches());
	 *
	 * LOGGER.info("testCreateObject {}", stringBlock); }
	 *
	 * @Test public void testCreateCustomInterfaceCanIHazFidder() { CodeBlock
	 * stringBlock = DEFAULT_INSTANCE.create(CustomInterface.class, NOMBRE_CAMPO,
	 * true); Assert.assertNotNull(stringBlock);
	 *
	 * String toMatch = stringBlock.toString();
	 *
	 * Assert.assertTrue(PATTERN_CUSTOM_INTERFACE_FIDDER.matcher(toMatch).matches())
	 * ;
	 *
	 * LOGGER.info("testCreateCustomInterfaceCanIHazFidder {}", stringBlock); }
	 *
	 *
	 *
	 */

}