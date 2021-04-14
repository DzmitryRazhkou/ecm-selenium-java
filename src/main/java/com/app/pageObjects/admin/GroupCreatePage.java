package com.app.pageObjects.admin;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.app.base.ReusableAdminPage;

/**
 * This class implements the Create a Group page on the within the Group page.
 * @author Muana Kimba
 */
public class GroupCreatePage extends ReusableAdminPage {
	
	// STATIC WEB ELEMENTS
	private static final By GROUP_ID_FIELD = By.xpath("//input[contains(@id, 'shortname')]");
	private static final By GROUP_NAME_FIELD = By.xpath("//input[contains(@id, 'displayname')]");
	private static final By CREATE_GROUP_BUTTON = By.xpath("//button[contains(text(), 'Create Group')]");
	
	// PAGE INITIALIZATION
	public GroupCreatePage(WebDriver driver) {
		super(driver);
	}
	
	// USER ACTIONS
	public GroupPage createGroup(String groupID, String groupName) {
		enterText(GROUP_ID_FIELD, groupID, "Identifier text field");
		enterText(GROUP_NAME_FIELD, groupName, "Display Name text field");
		click(CREATE_GROUP_BUTTON, "Create Group button");
		return new GroupPage(driver);
	}
}