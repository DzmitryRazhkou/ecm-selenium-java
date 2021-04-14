package com.app.utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.app.constants.FilePaths;

/**
 * This class contains utility methods for taking screenshots.
 * @author Muana Kimba
 */
public final class ReportUtil {
	
	// CONSTRUCTOR
	private ReportUtil() throws Exception {
		throw new Exception();
	}
	
	// METHODS
	/**
	 * This method takes a screenshot of the web page.
	 * @param driver The driver session
	 * @param methodName The method name without parameter
	 * @return The screenshot file path
	 */
	public static String takeScreenshot(WebDriver driver, String methodName) {
		// screenshot directory (create if doesn't exist)
		String directory = FilePaths.SCREENSHOTS;
		new File(directory).mkdirs();
		
		// screenshot file path
		String filePath = directory + getScreenshotFileName(methodName);
		
		// screenshot capture and file creation with the given path + name
		try {
			File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(screenshot, new File(filePath));
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
		
		return filePath;
	}
	
	/**
	 * This method returns an unique screenshot name.
	 * @param methodName The method name without parameter
	 * @return The screenshot filename containing date, time and extension
	 */
	private static String getScreenshotFileName(String methodName) {
		return methodName + "_" + new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date()) + ".png";
	}
}