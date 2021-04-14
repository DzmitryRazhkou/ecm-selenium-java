package com.app.base;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.app.reports.ExtentLogger;

/**
 * This class is inherited by the Page and page component classes
 * @author Muana Kimba
 */
public abstract class BasePage {

	//ATTRIBUTES
	protected static final long TIMEOUT = 15;
	protected static final long TRANSITION_TIMEOUT = 5;
	protected static final long MESSAGE_TIMEOUT = 3;
	protected WebDriver driver;

	// CONSTRUCTOR
	protected BasePage(WebDriver driver) {
		this.driver = driver;
	}

	// METHODS
	////////////////////////////////////////////////////////////////////////////////////////////////////
	//												CLICK
	protected void click(By locator, String elementName) {
		driver.findElement(locator).click();
		ExtentLogger.logClick(elementName);
	}
	protected void click(By locator, long timeout, String elementName) {
		elementToBeClickable(locator, timeout).click();
		ExtentLogger.logClick(elementName, timeout);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	//												ENTER TEXT
	protected void enterText(By locator, String text, String elementName) {
		if(text == null)	// for testdata to not enter any text in the field
			return;

		WebElement element = driver.findElement(locator);
		element.clear();
		element.sendKeys(text);
		ExtentLogger.logText(text, elementName);
	}
	protected void enterText(By locator, String text, long timeout, String elementName) {
		if(text == null)	// for testdata to not enter any text in the field
			return;

		WebElement element = visibilityOfElementLocated(locator, timeout);
		element.clear();
		element.sendKeys(text);
		ExtentLogger.logText(text, elementName, timeout);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	//											WAIT FOR LOCATOR
	//	VISIBILITY
	protected boolean isElementLocatedVisible(By locator, long timeout) {
		try {
			visibilityOfElementLocated(locator, timeout);
			return true;
		}
		catch(TimeoutException te) {
			return false;
		}
	}

	protected WebElement visibilityOfElementLocated(By locator, long timeout) {
		return new WebDriverWait(driver, timeout).until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	//	CLICKABLE
	protected WebElement elementToBeClickable(By locator, long timeout) {
		return new WebDriverWait(driver, timeout).until(ExpectedConditions.elementToBeClickable(locator));
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	//												ACTIONS
	protected Actions getActions() {
		return new Actions(driver);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	//												JAVASCRIPT
	protected void scrollIntoView(WebElement element) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true)", element);
	}
}