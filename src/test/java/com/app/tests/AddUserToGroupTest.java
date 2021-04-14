package com.app.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.app.base.BaseTest;
import com.app.constants.FilePaths;
import com.app.constants.PageInfo;
import com.app.entities.Group;
import com.app.pageObjects.LoginPage;
import com.app.pageObjects.admin.GroupPage;
import com.app.reports.ExtentLogger;
import com.app.utils.DataReader;

@Test(groups = {"RQ5:&nbsp;Group&nbsp;management"})
public class AddUserToGroupTest extends BaseTest {
	
	// ATTRIBUTES
	private GroupPage adminGroupPage;
	
	// CONFIGURATION METHODS
	////////////////////////////////////////////////////////////////////////////////////////////////////
	@BeforeMethod
	public void setup() {
		loginPage = new LoginPage(driver);
		dashboardPage = loginPage.login(prop.getProperty("USERNAME"), prop.getProperty("PASSWORD"));
	}
	
	@AfterMethod(alwaysRun = true)
	public void teardown() {
		driver.manage().deleteAllCookies();
		driver.get(PageInfo.LOGIN_PAGE_URL);
	}
	
	// TEST METHODS
	////////////////////////////////////////////////////////////////////////////////////////////////////
	@Test(groups = {"Smoke", "Regression"}, description = "Verify the add user to group functionality by adding a admin to an empty group",
			dataProvider = "validGroupDp")
	public void verifyAddAdminToEmptyGroup(Group group) throws Exception {
		// DATA
		String groupID = group.getGroupID();
		String groupName = group.getGroupName();
		String firstname = prop.getProperty("USER_FIRSTNAME");
		String lastname = prop.getProperty("USER_LASTNAME");
		String username = prop.getProperty("USERNAME");
		
		// SETUP (create group)
		adminGroupPage =
				dashboardPage.pageHeader.gotoAdminAppPage()
				.adminNavigation.gotoGroupPage()
				.gotoGroupCreatePage()
				.createGroup(groupID, groupName)

		// STEPS
		.browseGroup(groupID, groupName);
		boolean isUserPresent = adminGroupPage.isUserPresent(firstname, lastname, username);
		adminGroupPage.addUser(username);

		// CHECKPOINT
		SoftAssert sa = new SoftAssert();
		String details = "Fail prompt should not be present";
		if(isUserPresent) {
			sa.assertTrue(adminGroupPage.isFailPromptPresent());
			ExtentLogger.logVerificationAsFail(details);
		}
		else {
			sa.assertFalse(adminGroupPage.isFailPromptPresent());
			ExtentLogger.logVerificationAsPass(details);
		}

		// VERIFICATION
		adminGroupPage.refresh();
		adminGroupPage.browseGroup(groupID, groupName);
		details = "User should be present";
		if(adminGroupPage.isUserPresent(firstname, lastname, username)) {
			sa.assertTrue(true);
			ExtentLogger.logVerificationAsPass(details);
		}
		else {
			sa.assertFalse(false);
			ExtentLogger.logVerificationAsFail(details);
		}
		sa.assertAll();
		
		// TEARDOWN (delete group)
		adminGroupPage.deleteGroup(groupID, groupName);
	}
	
	// DATA PROVIDER METHODS
	////////////////////////////////////////////////////////////////////////////////////////////////////
	@DataProvider
	public Group[][] validGroupDp() {
		String[][] readTestdata = DataReader.readCSV(FilePaths.TEST_DATA + "validGroup.csv", ",");
		Group[][] testdata = new Group[readTestdata.length][1];
		
		for(int i = 0; i < readTestdata.length; i++)
			testdata[i][0] = new Group(readTestdata[i][0], readTestdata[i][1]);
		
		return testdata;
	}
}