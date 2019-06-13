package net.psybit.feeder;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

/**
 * basic basic data type generator
 *
 * @author morsetvite
 *
 */
public class SimpleFidder {

	private static SimpleFidder instance;

	private static final Random RANDOM = new Random();
	private static final char UPPER_A = 'A';
	private static final char LOWER_A = 'a';

	private int stringsLength = 20;
	private int integerLimit = 10;
	private double floatPointFromLimit = 9.5;
	private double floatPointToLimit = 100.3;
	private int dataStructureSize = 2;

	public static final synchronized SimpleFidder getInstance() {
		if (instance == null) {
			instance = new SimpleFidder();
		}
		return instance;
	}

	public static final synchronized SimpleFidder getInstance(final int stringsLength, final int integerLimit,
			final double floatPointFromLimit, final double floatPointToLimit, final int dataStructureSize) {
		if (instance == null) {
			instance = new SimpleFidder(stringsLength, integerLimit, floatPointFromLimit, floatPointToLimit,
					dataStructureSize);
		}
		return instance;
	}

	private SimpleFidder() {}

	private SimpleFidder(final int stringsLength, final int integerLimit, final double floatPointFromLimit,
			final double floatPointToLimit, final int dataStructureSize) {
		this.stringsLength = stringsLength;
		this.integerLimit = integerLimit;
		this.floatPointFromLimit = floatPointFromLimit;
		this.floatPointToLimit = floatPointToLimit;
		this.dataStructureSize = dataStructureSize;
	}

	public Date date() {
		Calendar gregorianCalendar = new GregorianCalendar();

		int year = this.doubleValue(1900, gregorianCalendar.get(Calendar.YEAR)).intValue();
		int month = this.doubleValue(1, 12).intValue();

		gregorianCalendar.set(Calendar.YEAR, year);
		gregorianCalendar.set(Calendar.MONTH, month);

		int day = this.doubleValue(gregorianCalendar.getMinimum(Calendar.DAY_OF_MONTH),
				gregorianCalendar.getMaximum(Calendar.DAY_OF_MONTH)).intValue();

		gregorianCalendar.set(Calendar.DAY_OF_YEAR, day);

		int hour = this.doubleValue(0, 23).intValue();
		int minute = this.doubleValue(0, 59).intValue();
		int second = this.doubleValue(0, 59).intValue();
		int milli = this.doubleValue(0, 999).intValue();

		gregorianCalendar.set(Calendar.HOUR_OF_DAY, hour);
		gregorianCalendar.set(Calendar.MINUTE, minute);
		gregorianCalendar.set(Calendar.SECOND, second);
		gregorianCalendar.set(Calendar.MILLISECOND, milli);

		return gregorianCalendar.getTime();
	}

	public String date(String patternFormat) {
		if (patternFormat == null) {
			patternFormat = "yyyy-MM-dd";
		}
		final DateFormat dateFormat = new SimpleDateFormat(patternFormat);
		return dateFormat.format(this.date());
	}

	public Timestamp timestamp() {
		return new Timestamp(this.date().getTime());
	}

	public Character charValue() {
		return (char) RANDOM.nextInt(Character.MAX_VALUE);
	}

	public Byte byteValue() {
		return (byte) RANDOM.nextInt(Byte.MAX_VALUE);
	}

	public Short shortValue() {
		return (short) RANDOM.nextInt(Short.MAX_VALUE);
	}

	public Long longValue() {
		return RANDOM.nextLong();
	}

	public Boolean boolValue() {
		return this.intValue(integerLimit) % 2 == 0;
	}

	public Double doubleValue() {
		return RANDOM.nextDouble() * floatPointToLimit;
	}

	Double doubleValue(double start, double end) {
		return start + Math.round(RANDOM.nextDouble() * (end - start));
	}

	public Float floatValue() {
		return (float) (RANDOM.nextFloat() * floatPointToLimit);
	}

	Float floatValue(final float start, final float end) {
		return start + Math.round(RANDOM.nextFloat() * (end - start));
	}

	public Integer intValue() {
		return RANDOM.nextInt(integerLimit);
	}

	public Integer intValue(final int limit) {
		return RANDOM.nextInt(limit);
	}

	public BigDecimal bigDecimal() {
		return this.bigDecimal(floatPointFromLimit, floatPointToLimit);
	}

	public BigDecimal bigDecimal(double start, double end) {
		return BigDecimal.valueOf(this.doubleValue(start, end));
	}

	public BigInteger bigInteger() {
		return BigInteger.valueOf(this.intValue());
	}

	public String formattedNumber(final Number number, final String format) {
		if (format == null) {
			throw new NullPointerException("The format number should not be null");
		}
		if (number == null) {
			throw new NullPointerException("The number sent should not be null");
		}
		NumberFormat numberFormat = new DecimalFormat(format);
		return numberFormat.format(number);
	}

	public String string() {
		return this.string(25, stringsLength, LOWER_A, UPPER_A);
	}

	public String string(final int length) {
		return this.string(25, length, LOWER_A, UPPER_A);
	}

	public String string(final int limit, final int length, final char marker, final char secondMarker) {
		final StringBuilder builder = new StringBuilder(length);

		for (int i = 0; i < length; i++) {
			if (i % 2 == 0) {
				builder.append((char) (RANDOM.nextInt(limit) + marker));
			}
			else {
				builder.append((char) (RANDOM.nextInt(limit) + secondMarker));
			}
		}
		return builder.toString();
	}

	@SuppressWarnings("unchecked")
	public <T> T create(final Class<T> klazz) {
		if (String.class.isAssignableFrom(klazz)) {
			return (T) this.string();
		}
		else if (BigInteger.class.isAssignableFrom(klazz)) {
			return (T) this.bigInteger();
		}
		else if (BigDecimal.class.isAssignableFrom(klazz)) {
			return (T) this.bigDecimal();
		}
		else if (Long.class.isAssignableFrom(klazz) || Long.TYPE.isAssignableFrom(klazz)) {
			return (T) this.longValue();
		}
		else if (Integer.TYPE.isAssignableFrom(klazz) || Integer.class.isAssignableFrom(klazz)) {
			return (T) this.intValue();
		}
		else if (Double.TYPE.isAssignableFrom(klazz) || Double.class.isAssignableFrom(klazz)) {
			return (T) this.doubleValue();
		}
		else if (Float.TYPE.isAssignableFrom(klazz) || Float.class.isAssignableFrom(klazz)) {
			return (T) this.floatValue();
		}
		else if (Byte.TYPE.isAssignableFrom(klazz) || Byte.class.isAssignableFrom(klazz)) {
			return (T) this.byteValue();
		}
		else if (Short.TYPE.isAssignableFrom(klazz) || Short.class.isAssignableFrom(klazz)) {
			return (T) this.shortValue();
		}
		else if (Character.TYPE.isAssignableFrom(klazz) || Character.class.isAssignableFrom(klazz)) {
			return (T) this.charValue();
		}
		else if (Boolean.TYPE.isAssignableFrom(klazz) || Boolean.class.isAssignableFrom(klazz)) {
			return (T) this.boolValue();
		}
		else if (Timestamp.class.isAssignableFrom(klazz)) {
			return (T) this.timestamp();
		}
		else if (Date.class.isAssignableFrom(klazz)) {
			return (T) this.date();
		}
		else if (klazz.isEnum()) {
			Object[] enumValues = klazz.getEnumConstants();
			if (enumValues == null || enumValues.length == 0) {
				return (T) (klazz.getSimpleName() + ".values()[0]");
			}
			return (T) enumValues[RANDOM.nextInt(enumValues.length)];
		}
		else if (klazz.isArray()) {
			Class<?> componentType = klazz.getComponentType();
			Object array = Array.newInstance(componentType, dataStructureSize);

			for (int i = 0; i < dataStructureSize; i++) {
				Object value = create(componentType);
				Array.set(array, i, value);
			}
			return (T) array;
		}
		else if (Object.class.equals(klazz)) {
			return (T) new Object();
		}
		return null;
	}
}