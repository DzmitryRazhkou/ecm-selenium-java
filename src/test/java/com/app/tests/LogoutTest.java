package com.app.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.app.base.BaseTest;
import com.app.constants.PageInfo;
import com.app.pageObjects.LoginPage;

@Test(groups = {"RQ1:&nbsp;Connection"}, testName = "Logout", description = "Verify the logout functionality in order to disconnect from the application")
public class LogoutTest extends BaseTest {
	
	// CONFIGURATION METHODS
	////////////////////////////////////////////////////////////////////////////////////////////////////
	@BeforeMethod()
	public void setup() {
		loginPage = new LoginPage(driver);
		dashboardPage = loginPage.login(prop.getProperty("USERNAME"), prop.getProperty("PASSWORD"));
	}
	
	// TEST METHODS
	////////////////////////////////////////////////////////////////////////////////////////////////////
	@Test(groups = {"Smoke"}, description = "Verify the title and URL of the login page after logging out.")
	public void verifylogout() throws Exception {
		// STEPS
		loginPage = dashboardPage.pageHeader.logout();

		// VERIFICATION
		SoftAssert sa = new SoftAssert();
		sa.assertEquals(loginPage.getTitle(), PageInfo.LOGIN_PAGE_TITLE);
		sa.assertEquals(loginPage.getURL(), PageInfo.LOGIN_PAGE_URL);
		sa.assertAll();
	}
}