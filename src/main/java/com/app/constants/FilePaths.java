package com.app.constants;

/**
 * This class contains path for directories and files (report, configuration, test data, etc).
 * @author Muana Kimba
 */
public final class FilePaths {

	// CONSTRUCTOR
	private FilePaths() throws Exception {
		throw new Exception();
	}
	
	// SYSTEM PROPERTIES
	private static final String USER_DIR = System.getProperty("user.dir");
	
	// DIRECTORIES
	private static final String MAIN_RESOURCES = "src/main/resources/";
	private static final String TEST_RESOURCES = "src/test/resources/";
	private static final String CONFIG = "com/app/config/";
	private static final String TEST_OUTPUT = "test-output/";
	
	public static final String REPORTS = TEST_OUTPUT + "reports/";
	public static final String REPORT_CONFIG = MAIN_RESOURCES + CONFIG;
	public static final String TEST_CONFIG = TEST_RESOURCES + CONFIG;
	public static final String TEST_DATA = TEST_RESOURCES + "com/app/testdata/";
	public static final String SCREENSHOTS = USER_DIR + '/' + TEST_OUTPUT + "screenshots/";
}