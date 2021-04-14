package com.app.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.app.base.Page;

/**
 * This class implements the login page.
 * @author Muana Kimba
 */
public class LoginPage extends Page {
	
	// STATIC WEB ELEMENTS
	private static final By USERNAME_FIELD = By.name("username");
	private static final By PASSWORD_FIELD = By.name("password");
	private static final By LOGIN_BUTTON = By.tagName("button");
	// DYNAMIC WEB ELEMENTS
	private static final By AUTHENTICATION_ERROR_MESSAGE = By.cssSelector(".error");
	
	// PAGE INITIALIZATION
	public LoginPage(WebDriver driver) {
		super(driver);
		isElementLocatedVisible(LOGIN_BUTTON, TRANSITION_TIMEOUT);
	}
	
	// USER ACTIONS
	public LoginPage enterUsername(String username) {
		enterText(USERNAME_FIELD, username, TIMEOUT, "User Name text field");
		return this;
	}
	public LoginPage enterPassword(String password) {
		enterText(PASSWORD_FIELD, password, TIMEOUT, "Password text field");
		return this;
	}
	// for invalid scenarios to not unnecessarily trigger the page transition wait
	public LoginPage clickLogin() {
		click(LOGIN_BUTTON, TIMEOUT, "Sign In button");
		return new LoginPage(driver);
	}
	
	// USER ACTIONS THAT TRIGGER A PAGE TRANSITION
	public DashboardPage login(String username, String password) {
		enterUsername(username)
		.enterPassword(password)
		.click(LOGIN_BUTTON, TIMEOUT, "Sign In button");
		return new DashboardPage(driver);
	}
	
	// VERIFICATION
	public boolean isErrorMessageDisplayed() {
		return isElementLocatedVisible(AUTHENTICATION_ERROR_MESSAGE, MESSAGE_TIMEOUT);
	}
}