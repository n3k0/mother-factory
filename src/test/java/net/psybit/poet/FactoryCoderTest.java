package net.psybit.poet;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.squareup.javapoet.MethodSpec;

import net.psybit.pojo.FidderOptions;
import net.psybit.pojo.MojoSettings;
import net.psybit.utils.Constants;

public class FactoryCoderTest {

	private static FactoryCoder FACTORY_CODER;

	static FidderOptions fidderOptions;
	static MojoSettings mojoSettings;

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
		
		FACTORY_CODER = new FactoryCoder(fidderOptions);
	}

	@Test
	public void buildNameCustomEmptyPojoTest() {
		String s = FACTORY_CODER.buildName(CustomEmptyPojo.class);

		Assert.assertNotNull(s);
	}

	@Test
	public void buildNameCustomPojoTest() {
		String s = FACTORY_CODER.buildName(CustomPojo.class);

		Assert.assertNotNull(s);
	}

	@Test
	public void createPojoCustomPojoTest() {
		Constants.projectClasses().add(CustomPojo.class);
		MethodSpec m = FACTORY_CODER.createPojo(CustomPojo.class);

		Assert.assertNotNull(m);
	}

	static class CustomEmptyPojo {

	}

	static class CustomPojo {
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

		@Override
		public String toString() {
			return String.format(
					"CustomPojo [_byte=%s, _char=%s, _short=%s, _int=%s, _long=%s, string=%s, oByte=%s, oChar=%s, oShort=%s, oInt=%s, oLong=%s, arrayOneDimension=%s, arrayTwoDimensions=%s, list=%s, customList=%s, map=%s, customMap=%s, set=%s, customSet=%s]",
					this._byte, this._char, this._short, this._int, this._long, this.string, this.oByte, this.oChar,
					this.oShort, this.oInt, this.oLong, Arrays.toString(this.arrayOneDimension),
					Arrays.toString(this.arrayTwoDimensions), this.list, this.customList, this.map, this.customMap,
					this.set, this.customSet);
		}
	}
}
