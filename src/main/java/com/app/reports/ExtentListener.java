package com.app.reports;

import java.util.Arrays;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.app.utils.DriverFactory;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.app.utils.ReportUtil;

/**
 * This class contains TestNG ITestListener methods for adding test cases to the report.
 * @author Muana Kimba
 */
public final class ExtentListener implements ITestListener {

	// ATTRIBUTES
	private final ExtentReports TEST_REPORT = ExtentReport.initReport();
	static final ThreadLocal<ExtentTest> TESTS = new ThreadLocal<>();
	
	// ITestListener METHODS
	@Override
	public synchronized void onTestStart(ITestResult testResult) {
		// retrieve the driver from the current thread
		WebDriver driver = DriverFactory.getDriver();

		// get the browser information from the driver capabilities
		Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
		String browserName = cap.getBrowserName();
		browserName = browserName.substring(0, 1).toUpperCase() + browserName.substring(1);
		String browserInfo = browserName + "<br>" + cap.getVersion();

		// create the test result node and register it to the current thread
		TESTS
		.set(TEST_REPORT
				.createTest(testResult.getMethod().getMethodName()
						+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + Arrays.toString(testResult.getParameters())
						, testResult.getMethod().getDescription())
				.assignAuthor(System.getProperty("author"))
				.assignCategory(testResult.getMethod().getGroups())
				.assignDevice(browserInfo));
	}

	@Override
	public synchronized void onTestSuccess(ITestResult testResult) {
		String logText = "<b>Test method " + testResult.getMethod().getMethodName() + " succeed</b>";
		TESTS.get().pass(MarkupHelper.createLabel(logText, ExtentColor.GREEN));
		TESTS.remove();
	}

	@Override
	public synchronized void onTestFailure(ITestResult testResult) {
		// log the defect (bug)
		TESTS.get().fail(testResult.getThrowable());
		
		// retrieve the driver from the current thread for the screenshot
		WebDriver driver = DriverFactory.getDriver();
		String screenshotPath = ReportUtil.takeScreenshot(driver, testResult.getMethod().getMethodName());

		TESTS.get().fail("<b><font color=red>Screenshot of failure</font></b>",
				MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());

		// mark the test as fail
		String logText = "<b>Test method " + testResult.getMethod().getMethodName() + " failed</b>";
		TESTS.get().fail(MarkupHelper.createLabel(logText, ExtentColor.RED));
		TESTS.remove();
	}

	@Override
	public synchronized void onTestSkipped(ITestResult testResult) {
		if(TESTS.get() == null)
			return;
		String logText = "<b>Test method " + testResult.getMethod().getMethodName() + " skipped</b>";
		TESTS.get().skip(MarkupHelper.createLabel(logText, ExtentColor.YELLOW));
		TESTS.remove();
	}

	@Override
	public synchronized void onTestFailedButWithinSuccessPercentage(ITestResult testResult) {
	}

	@Override
	public synchronized void onStart(ITestContext context) {
	}

	@Override
	public synchronized void onFinish(ITestContext context) {
		TEST_REPORT.flush();
	}
}