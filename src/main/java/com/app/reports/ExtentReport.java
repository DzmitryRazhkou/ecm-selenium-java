package com.app.reports;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import org.ini4j.Ini;
import org.ini4j.Profile.Section;

import com.app.constants.FilePaths;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.ViewName;

/**
 * This class creates the test report based on configuration files.
 * @author Muana Kimba
 */
final class ExtentReport {

	// CONSTRUCTOR
	private ExtentReport() throws Exception {
		throw new Exception();
	}

	// METHODS
	/**
	 * This method creates and configures ExtentSparkReporter and ExtentReports
	 * @return The ExtentReports with the ExtentSparkReporter attached
	 */
	static ExtentReports initReport() {
		// TEST REPORT LAYOUT
		// ExtentSparkReporter initialization
		ExtentSparkReporter sparkReporter = new ExtentSparkReporter(FilePaths.REPORTS + getReportFilename());
		
		// ExtentSparkReporter viewport customization
		sparkReporter.viewConfigurer()
		.viewOrder()
		.as(new ViewName[] { 
				ViewName.DASHBOARD,
				ViewName.TEST,
				ViewName.CATEGORY,
				ViewName.DEVICE,
				ViewName.AUTHOR,
				ViewName.EXCEPTION,
				ViewName.LOG})
		.apply();

		// ExtentSparkReporter configuration from a .json file
		try {
			sparkReporter.loadJSONConfig(new File(FilePaths.REPORT_CONFIG + "spark-config.json"));
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
		
		// TEST REPORT CONTENT
		// ExtentReports initialization
		ExtentReports extent = new ExtentReports();
		extent.attachReporter(sparkReporter);

		// ExtentReports system info from a .ini file
		loadINIConfig(FilePaths.REPORT_CONFIG + "extent-config.ini", extent);
		
		return extent;
	}

	/**
	 * This method returns an unique report filename.
	 * @return The filename containing date, time and extension
	 */
	private static String getReportFilename() {
		return "Spark_" + new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date()) + ".html";
	}

	/**
	 * This method loads a .ini file content into the ExtentReports system info.
	 * @param filename The .ini file to load
	 * @param An object of the ExtentReports class
	 */
	private static void loadINIConfig(String filename, ExtentReports extent) {
		try {
			Ini ini = new Ini(new File(filename));

			Set<String> sectionNames = ini.keySet();	// retrieve section names
			for(String sectionName : sectionNames) {
				Section sectionOptions = ini.getAll(sectionName).get(0);	// retrieve options for the first given section name

				Set<String> optionNames = sectionOptions.keySet();	// retrieve option names

				for(String optionName : optionNames)
					extent.setSystemInfo(optionName, sectionOptions.get(optionName));	// add option with its value
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}