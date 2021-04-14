package com.app.base;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * This class is inherited by the LoginPage and the ReusablePage classes.
 * @author Muana Kimba
 */
public abstract class Page extends BasePage {
	
	// ATTRIBUTES
	private static final long PAGE_LOAD_TIMEOUT = Long.parseLong(System.getProperty("page.load.timeout"));
	
	// CONSTRUCTOR
	public Page(WebDriver driver) {
		super(driver);
		waitForReadyState();
	}
	
	/**
	 * This method is useful to wait for a particular page to load
	 * once it gets instantiated.
	 */
	private void waitForReadyState() {
		WebDriverWait wait = new WebDriverWait(driver, PAGE_LOAD_TIMEOUT);

	    wait.until(new ExpectedCondition<Boolean>() {
	        public Boolean apply(WebDriver wdriver) {
	            return ((JavascriptExecutor) driver).executeScript(
	                "return document.readyState"
	            ).equals("complete");
	        }
	    });
	}

	// METHODS
	public String getTitle() {
		return driver.getTitle();
	}
	public String getURL() {
		return driver.getCurrentUrl();
	}
	
	public void navigateTo(String url) {
		driver.navigate().to(url);
	}
	
	public void refresh() {
		driver.navigate().refresh();
	}
}