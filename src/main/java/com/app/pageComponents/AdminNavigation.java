package com.app.pageComponents;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.app.base.BasePage;
import com.app.pageObjects.admin.GroupPage;

/**
 * This class implements the admin navigation menu.
 * @author Muana Kimba
 */
public class AdminNavigation extends BasePage {
	
	// STATIC WEB ELEMENTS
	// Tools
	// Users and groups
	private final static By GROUP_MANAGER = By.cssSelector("a[href=groups]");

	// PAGE COMPONENT INITIALIZATION
	public AdminNavigation(WebDriver driver) {
		super(driver);
	}
	
	// PAGE TRANSITIONS
	public GroupPage gotoGroupPage() {
		click(GROUP_MANAGER, "Groups Admin Tool link");
		return new GroupPage(driver);
	}
}