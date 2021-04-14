package com.app.pageObjects.admin;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.app.base.ReusableAdminPage;

/**
 * This class implements the Group page on the admin navigation menu. 
 * @author Muana Kimba
 */
public class GroupPage extends ReusableAdminPage {

	// STATIC WEB ELEMENTS
	private static final By BROWSE_BUTTON = By.xpath("//button[contains(text(), 'Browse')]");
	private static final By CREATE_GROUP_ICON1 = By.xpath("//span[contains(@title, 'New Group')]");
	// DYNAMIC WEB ELEMENTS
	private static final By ADD_USER_ICON = By.xpath("//span[contains(@title, 'Add User')]");
	private static final By PEOPLE_FINDER_FIELD = By.xpath("//input[contains(@id, 'peoplefinder')]");
	private static final By PEOPLE_FINDER_BUTTON = By.xpath("//button[contains(@id, 'peoplefinder')]");
	private static final By ADD_USER_BUTTON = By.xpath("//button[contains(text(), 'Add')]");
	private static final By DELETE_GROUP_BUTTON = By.xpath("//button[@id and contains(text(), 'Delete')]");
	private static final By FAIL_PROMPT = By.id("prompt_c");

	// PAGE INITIALIZATION
	public GroupPage(WebDriver driver) {
		super(driver);
	}

	// PAGE TRANSITIONS
	public GroupCreatePage gotoGroupCreatePage() {
		click(BROWSE_BUTTON, TIMEOUT, "Browse button");
		click(CREATE_GROUP_ICON1, TIMEOUT, "Create Group first icon");
		return new GroupCreatePage(driver);
	}

	// USER ACTIONS
	public GroupPage browseGroup(String groupID, String groupName) {
		String groupLabel = getGroupLabel(groupID, groupName);
		click(BROWSE_BUTTON, TIMEOUT, "Browse button");
		WebElement groupElement = scrollGroupIntoView(By.xpath("//span[text()='" + groupLabel + "']"));
		groupElement.click();
		return this;
	}

	public GroupPage addUser(String username) {
		click(ADD_USER_ICON, TIMEOUT, "Add User icon");
		enterText(PEOPLE_FINDER_FIELD, username, TIMEOUT, "Search for users... text field");
		click(PEOPLE_FINDER_BUTTON, "Search button");
		click(ADD_USER_BUTTON, TIMEOUT, "Add button");
		return this;
	}

	public GroupPage deleteGroup(String groupID, String groupName) {
		String groupLabel = getGroupLabel(groupID, groupName);
		WebElement groupElement = scrollGroupIntoView(By.xpath("//span[text()='" + groupLabel + "']"));
		
		getActions()
		.moveToElement(groupElement)
		.click(visibilityOfElementLocated(By.xpath("//span[text()='" + groupLabel + "']/following-sibling::span//span[contains(@title, 'Delete')]"), 1))
		.build()
		.perform();
		click(DELETE_GROUP_BUTTON, 5, "Delete Group X button");
		
		return this;
	}

	// HELPER METHODS
	private String getGroupLabel(String groupID, String groupName) {
		return groupID.equals(groupName) ? groupID : groupName + " (" + groupID + ")";
	}

	private String getUserLabel(String firstname, String lastname, String username) {
		return (firstname + " " + lastname + " (" + username + ")").trim();
	}

	private WebElement scrollGroupIntoView(By locator) {
		WebElement groupElement = visibilityOfElementLocated(locator, TIMEOUT);
		scrollIntoView(groupElement);
		return groupElement;
	}

	// VERIFICATION
	public boolean isFailPromptPresent() {
		return isElementLocatedVisible(FAIL_PROMPT, 2);
	}

	public boolean isUserPresent(String firstname, String lastname, String username) {
		return isElementLocatedVisible(By.xpath("//a[contains(@class, 'user')]//span[text()='"
				+ getUserLabel(firstname, lastname, username) + "']"), 5);
	}
}