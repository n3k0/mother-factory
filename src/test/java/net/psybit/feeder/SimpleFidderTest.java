package net.psybit.feeder;

import java.sql.Timestamp;
import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.Test;

public class SimpleFidderTest {

	private static final SimpleFidder SIMPLE_FIDDER = SimpleFidder.getInstance();

	@Test
	public void createOutBoxTest() {
		Object object = SIMPLE_FIDDER.create(Byte.class);

		Assert.assertNotNull(object);
		Assert.assertTrue(Byte.class.isAssignableFrom(object.getClass()));

		object = SIMPLE_FIDDER.create(Character.class);

		Assert.assertNotNull(object);
		Assert.assertTrue(Character.class.isAssignableFrom(object.getClass()));

		object = SIMPLE_FIDDER.create(Short.class);

		Assert.assertNotNull(object);
		Assert.assertTrue(Short.class.isAssignableFrom(object.getClass()));

		object = SIMPLE_FIDDER.create(Integer.class);

		Assert.assertNotNull(object);
		Assert.assertTrue(Integer.class.isAssignableFrom(object.getClass()));

		object = SIMPLE_FIDDER.create(Long.class);

		Assert.assertNotNull(object);
		Assert.assertTrue(Long.class.isAssignableFrom(object.getClass()));

		object = SIMPLE_FIDDER.create(Double.class);

		Assert.assertNotNull(object);
		Assert.assertTrue(Double.class.isAssignableFrom(object.getClass()));

		object = SIMPLE_FIDDER.create(Float.class);

		Assert.assertNotNull(object);
		Assert.assertTrue(Float.class.isAssignableFrom(object.getClass()));

		object = SIMPLE_FIDDER.create(Date.class);

		Assert.assertNotNull(object);
		Assert.assertTrue(Date.class.isAssignableFrom(object.getClass()));

		object = SIMPLE_FIDDER.create(Timestamp.class);

		Assert.assertNotNull(object);
		Assert.assertTrue(Timestamp.class.isAssignableFrom(object.getClass()));

		object = SIMPLE_FIDDER.create(Object[].class);

		Assert.assertNotNull(object);
		Assert.assertTrue(Object[].class.isAssignableFrom(object.getClass()));

		object = SIMPLE_FIDDER.create(Object[][].class);

		Assert.assertNotNull(object);
		Assert.assertTrue(Object[][].class.isAssignableFrom(object.getClass()));

		object = SIMPLE_FIDDER.create(String.class);

		Assert.assertNotNull(object);
		Assert.assertTrue(String.class.isAssignableFrom(object.getClass()));

		object = SIMPLE_FIDDER.create(EnumEmptyDemo.class);

		Assert.assertNotNull(object);
		Assert.assertEquals("EnumEmptyDemo.values()[0]",object);

		object = SIMPLE_FIDDER.create(EnumOneDemo.class);

		Assert.assertNotNull(object);
		Assert.assertTrue(EnumOneDemo.class.isAssignableFrom(object.getClass()));
		Assert.assertEquals(EnumOneDemo.ELEMENT_1 , object);

		object = SIMPLE_FIDDER.create(EnumDemo.class);

		Assert.assertNotNull(object);
		Assert.assertTrue(EnumDemo.class.isAssignableFrom(object.getClass()));

	}

	//enums to test
	enum EnumEmptyDemo {}

	enum EnumOneDemo {
		ELEMENT_1
	}

	enum EnumDemo {
		ELEMENT_1, ELEMENT_2, ELEMENT_3
	}
	//enums to test
}