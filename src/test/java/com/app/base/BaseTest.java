package com.app.base;

import org.testng.annotations.BeforeClass;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.app.constants.FilePaths;
import com.app.constants.PageInfo;
import com.app.pageObjects.DashboardPage;
import com.app.pageObjects.LoginPage;
import com.app.utils.DriverFactory;
import com.app.utils.DataReader;

/**
 * This class is inherited by every test class
 * @author Muana Kimba
 */
public abstract class BaseTest {
	
	// ATTRIBUTES
	protected static Properties prop;			// contains test configuration
	protected WebDriver driver;					// automate web pages
	protected LoginPage loginPage;				// every test starts on the LoginPage
	protected DashboardPage dashboardPage;		// every test lands on the DashboardPage
	
	// METHODS
	/**
	 * This method is executed once per suite to read the .properties file
	 */
	@BeforeSuite
	public void suiteSetup() throws Exception {
		System.out.println("Configuration reader");
		prop = DataReader.readProperties(FilePaths.TEST_CONFIG + "test-config.properties");
	}
	
	/**
	 * This method configures the driver for the session
	 * @param browser the name of the browser provided by the testng.xml file
	 * @throws InvalidArgumentException is thrown if the browser is not supported
	 */
	@BeforeClass
	@Parameters({"browser"})
	public void classSetup(@Optional("chrome") String browser) throws Exception {
		System.out.println("Current browser: " + browser);
		
		// create the driver instance for the current thread
		DriverFactory.setDriver(browser, Boolean.parseBoolean(prop.getProperty("HEADLESS")));
		
		// retrieve the driver instance for the current thread
		driver = DriverFactory.getDriver();
		
		// configure the driver
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(Long.parseLong(System.getProperty("page.load.timeout")), TimeUnit.SECONDS);
		
		// open AUT
		driver.get(PageInfo.LOGIN_PAGE_URL);
	}
	
	/**
	 * This method is executed after each test class to terminate the session
	 * and to free the driver instance of the current thread
	 */
	@AfterClass(alwaysRun = true)
	public void classTeardown() throws Exception {
		System.out.println("Bye until next time...");
		
		// quit and release the driver
		if(driver != null) {
			driver.quit();
			DriverFactory.removeDriver();
		}
	}
}