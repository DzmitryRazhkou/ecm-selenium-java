package com.app.utils;

import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * This class creates and returns unique drivers (browsers) for each thread.
 * @author Muana Kimba
 */
public final class DriverFactory {

	// CONSTRUCTOR
	private DriverFactory() throws Exception {
		throw new Exception();
	}

	// ATTRIBUTES
	private static final ThreadLocal<WebDriver> DRIVERS = new ThreadLocal<>();

	// METHODS
	public static void setDriver(String browser, boolean headless) {
		switch(browser.trim().toLowerCase()) {
		case "chrome":
			setChromeDriver(headless);
			break;
		case "edge":
			setEdgeDriver();
			break;
		case "firefox":
			setFirefoxDriver(headless);
			break;
		default:
			throw new InvalidArgumentException(browser + " is not a valid browser in the factory!");
		}
	}

	private synchronized static void setChromeDriver(boolean headless) {
		WebDriverManager.chromedriver().setup();
		DRIVERS.set(new ChromeDriver(new ChromeOptions().setHeadless(headless)));
	}
	
	private synchronized static void setEdgeDriver() {
		WebDriverManager.edgedriver().setup();
		DRIVERS.set(new EdgeDriver());
	}

	private synchronized static void setFirefoxDriver(boolean headless) {
		WebDriverManager.firefoxdriver().setup();
		DRIVERS.set(new FirefoxDriver(new FirefoxOptions().setHeadless(headless)));
	}

	public synchronized static WebDriver getDriver() {
		return DRIVERS.get();
	}

	public synchronized static void removeDriver() {
		DRIVERS.remove();
	}
}