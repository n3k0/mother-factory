package net.psybit.utils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class CustomFileFilterTest {

	private CustomFileFilter customFileFilter;

	private List<String> inCriterias;
	private List<String> exCriterias;

	private File fileValidPrefix;
	private File fileInvalidPrefix;

	private File fileValidSuffix;
	private File fileInvalidSuffix;

	private File fileValidCompleteName;
	private File fileInvalidCompleteName;

	String with12 = "this.is.a.complete.waste.of.time.please.go.back.to.your.work";

	@BeforeTest
	public void BeforeTest() throws IOException {
		fileValidPrefix = File.createTempFile("PojoValid", "yep");
		fileInvalidPrefix = File.createTempFile("TransactionInvalid", "nope");

		fileValidSuffix = File.createTempFile("yep", "ValidSuffix.txt");
		fileInvalidSuffix = File.createTempFile("nope", "Invalid.class");

		fileValidCompleteName = File.createTempFile("validPrefix", "validSuffix");
		fileInvalidCompleteName = File.createTempFile("invalidPrefix", "invalidSuffix");

		inCriterias = Arrays.asList("^PojoValid", "ValidSuffix.txt$", fileValidCompleteName.getName());
		exCriterias = Arrays.asList("Invalid.class$", "^TransactionInvalid", fileInvalidCompleteName.getName());

		customFileFilter = new CustomFileFilter(inCriterias, exCriterias);
	}

	@AfterTest
	public void afterTest() {
		fileValidPrefix.deleteOnExit();
		fileInvalidPrefix.deleteOnExit();

		fileValidSuffix.deleteOnExit();
		fileInvalidSuffix.deleteOnExit();

		fileValidCompleteName.deleteOnExit();
		fileInvalidCompleteName.deleteOnExit();
	}

	@Test
	public void acceptValidPrefix() throws IOException {
		boolean accepted = customFileFilter.accept(fileValidPrefix);
		Assert.assertTrue(accepted);
	}

	@Test
	public void acceptValidSuffix() throws IOException {
		boolean accepted = customFileFilter.accept(fileValidSuffix);
		Assert.assertTrue(accepted);
	}

	@Test
	public void acceptValidExactName() throws IOException {
		boolean accepted = customFileFilter.accept(fileValidCompleteName);
		Assert.assertTrue(accepted);
	}

	@Test
	public void acceptInvalidPrefix() throws IOException {
		boolean accepted = customFileFilter.accept(fileInvalidPrefix);
		Assert.assertFalse(accepted);
	}

	@Test
	public void acceptInvalidSuffix() throws IOException {
		boolean accepted = customFileFilter.accept(fileInvalidSuffix);
		Assert.assertFalse(accepted);
	}

	@Test
	public void acceptInvalidExactName() throws IOException {
		boolean accepted = customFileFilter.accept(fileInvalidCompleteName);
		Assert.assertFalse(accepted);
	}

	@Test
	public void countMatchesMatch() {
		int count = customFileFilter.countMatches(with12);
		Assert.assertEquals(count, 12);
	}

	@Test
	public void countMatchesNoMatch() {
		int count = customFileFilter.countMatches(with12);
		Assert.assertFalse(count == 7);
	}

	@Test
	public void isPackageTrue() {
		boolean isPackage = customFileFilter.isPackage(with12);
		Assert.assertTrue(isPackage);
	}

	@Test
	public void isPackageFalse() {
		boolean isPackage = customFileFilter.isPackage("thisisnotapackage");
		Assert.assertFalse(isPackage);
	}

	@Test
	public void matchDirHierarchyTrue() throws IOException {
		boolean matched = customFileFilter.matchDirHierarchy(fileValidCompleteName,
				fileValidCompleteName.getCanonicalPath());

		Assert.assertTrue(matched);
	}

	@Test
	public void matchDirHierarchyFalse() throws IOException {
		boolean matched = customFileFilter.matchDirHierarchy(fileValidCompleteName, "other.distinct.path");
		Assert.assertFalse(matched);
	}

	@Test
	public void searchInCriteriasTrue() {
		boolean found = customFileFilter.searchInCriterias(inCriterias, fileValidCompleteName);
		Assert.assertTrue(found);
	}

	@Test
	public void searchInCriteriasFalse() {
		boolean found = customFileFilter.searchInCriterias(inCriterias, fileInvalidCompleteName);
		Assert.assertFalse(found);
	}
}
