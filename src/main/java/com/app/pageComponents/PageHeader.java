package com.app.pageComponents;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.app.base.BasePage;
import com.app.pageObjects.LoginPage;
import com.app.pageObjects.admin.AppPage;

/**
 * This class implements the page header.
 * @author Muana Kimba
 */
public class PageHeader extends BasePage {
	
	// STATIC WEB ELEMENTS
	private static final By USER_TEXT = By.id("HEADER_USER_MENU_POPUP_text");
	private static final By USER_MENU = By.id("HEADER_USER_MENU_BAR");
	private static final By ADMIN_LINK = By.id("HEADER_ADMIN_CONSOLE");
	// DYNAMIC WEB ELEMENTS
	private static final By USER_MENU_LOGOUT = By.id("HEADER_USER_MENU_LOGOUT_text");
	
	// PAGE COMPONENT INITIALIZATION
	public PageHeader(WebDriver driver) {
		super(driver);
	}
	
	// PAGE TRANSITIONS
	public AppPage gotoAdminAppPage() {
		click(ADMIN_LINK, TIMEOUT, "Admin header link");
		return new AppPage(driver);
	}
	
	public LoginPage logout() {
		click(USER_MENU, TIMEOUT, "User header menu");
		click(USER_MENU_LOGOUT, TIMEOUT, "Logout header menu item");
		return new LoginPage(driver);
	}
	
	// GET
	public String getUserText() {
		return visibilityOfElementLocated(USER_TEXT, TIMEOUT).getText();
	}
}