package com.app.tests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.app.base.BaseTest;
import com.app.constants.PageInfo;
import com.app.constants.FilePaths;
import com.app.pageObjects.LoginPage;
import com.app.utils.DataProviderUtil;
import com.app.utils.DataReader;

@Test(groups = {"RQ1:&nbsp;Connection"})
public class LoginTest extends BaseTest {
	
	// CONFIGURATION METHODS
	////////////////////////////////////////////////////////////////////////////////////////////////////
	@BeforeMethod
	public void setup() {
		loginPage = new LoginPage(driver);
	}
	
	@AfterMethod(alwaysRun = true)
	public void teardown() {
		driver.manage().deleteAllCookies();
		driver.get(PageInfo.LOGIN_PAGE_URL);
	}
	
	// TEST METHODS
	////////////////////////////////////////////////////////////////////////////////////////////////////
	@Test(groups = {"Smoke"}, description = "Verify the title of the login page after opening the application.")
	public void verifyLoginPageTitle() throws Exception {
		// VERIFICATION
		Assert.assertEquals(loginPage.getTitle(), PageInfo.LOGIN_PAGE_TITLE);
	}

	@Test(groups = {"Smoke", "Regression"}, description = "User with valid credentials should be able to log into the application.")
	public void verifyLoginWithValidCredentials() throws Exception {
		// STEPS
		dashboardPage = loginPage.login(prop.getProperty("USERNAME"), prop.getProperty("PASSWORD"));

		// VERIFICATION
		SoftAssert sa = new SoftAssert();
		sa.assertEquals(dashboardPage.getTitle(), PageInfo.DASHBOARD_PAGE_TITLE);
		sa.assertEquals(dashboardPage.getURL(), PageInfo.DASHBOARD_PAGE_URL);
		sa.assertEquals(dashboardPage.pageHeader.getUserText(), prop.getProperty("USER"));
		sa.assertAll();
	}
	
	@Test(groups = {"Smoke", "Regression"}, description = "User with an invalid username, but valid password should not be able to log into the application.",
			dataProvider = "invalidUsernameDp")
	public void verifyLoginWithInvalidUsername(String username) throws Exception {
		// STEPS
		loginPage.enterUsername(username)
		.enterPassword(prop.getProperty("PASSWORD"))
		.clickLogin();

		// VERIFICATION
		SoftAssert sa = new SoftAssert();
		sa.assertTrue(loginPage.isErrorMessageDisplayed());
		sa.assertEquals(loginPage.getTitle(), PageInfo.LOGIN_PAGE_TITLE);
		sa.assertEquals(loginPage.getURL(), PageInfo.LOGIN_ERROR_PAGE_URL);
		sa.assertAll();
	}
	
	@Test(groups = {"Smoke", "Regression"}, description = "User with a valid username, but invalid password should not be able to log into the application.",
			dataProvider = "invalidPasswordDp")
	public void verifyLoginWithInvalidPassword(String password) throws Exception {
		// STEPS
		loginPage.enterUsername(prop.getProperty("USERNAME"))
		.enterPassword(password)
		.clickLogin();

		// VERIFICATION
		SoftAssert sa = new SoftAssert();
		sa.assertTrue(loginPage.isErrorMessageDisplayed());
		sa.assertEquals(loginPage.getTitle(), PageInfo.LOGIN_PAGE_TITLE);
		sa.assertEquals(loginPage.getURL(), PageInfo.LOGIN_ERROR_PAGE_URL);
		sa.assertAll();
	}
	
	@Test(groups = {"Smoke", "Regression"}, description = "User with invalid credentials should not be able to log into the application.",
			dataProvider = "invalidCredentialsDp")
	public void verifyLoginWithInvalidCredentials(String username, String password) throws Exception {
		// STEPS
		loginPage.enterUsername(username)
		.enterPassword(password)
		.clickLogin();

		// VERIFICATION
		SoftAssert sa = new SoftAssert();
		sa.assertTrue(loginPage.isErrorMessageDisplayed());
		sa.assertEquals(loginPage.getTitle(), PageInfo.LOGIN_PAGE_TITLE);
		sa.assertEquals(loginPage.getURL(), PageInfo.LOGIN_ERROR_PAGE_URL);
		sa.assertAll();
	}
	
	// DATA PROVIDER METHODS
	////////////////////////////////////////////////////////////////////////////////////////////////////
	@DataProvider
	public String[] invalidUsernameDp() throws Exception {
		return DataProviderUtil.addNullValueToTestData(DataReader.readTXT(FilePaths.TEST_DATA + "invalidLoginUsername.txt"));
	}
	
	@DataProvider
	public String[] invalidPasswordDp() throws Exception {
		return DataProviderUtil.addNullValueToTestData(DataReader.readTXT(FilePaths.TEST_DATA + "invalidLoginPassword.txt"));
	}
	
	@DataProvider
	public String[][] invalidCredentialsDp() {
		return DataProviderUtil.addNullValueToTestData(DataReader.readCSV(FilePaths.TEST_DATA + "invalidLoginCredentials.csv", ","));
	}
}