package com.app.reports;

/**
 * This class only logs test method steps.
 * @author Muana Kimba
 */
public final class ExtentLogger {
	
	// CONSTRUCTOR
	private ExtentLogger() throws Exception {
		throw new Exception();
	}
	
	// ATTRIBUTES
	private enum Status {
		INFO,
		PASS,
		FAIL
	}
	
	// METHODS
	// info
	public static void logClick(String element) {
		log(Status.INFO, "Clicked on the " + element);
	}
	public static void logClick(String element, long timeout) {
		log(Status.INFO, "Clicked on the " + element + " with a " + timeout + " second(s) timeout");
	}
	
	public static void logText(String text, String element) {
		log(Status.INFO, "Entered " + "\"" + text + "\"" + " into the " + element);
	}
	public static void logText(String text, String element, long timeout) {
		log(Status.INFO, "Entered " + "\"" + text + "\"" + " into the " + element + " with a " + timeout + " second(s) timeout");
	}
	
	// pass
	public static void logVerificationAsPass(String details) {
		log(Status.PASS, "Verified that " + details);
	}
	
	// fail
	public static void logVerificationAsFail(String details) {
		log(Status.FAIL, "Verified that " + details);
	}
	
	// generic log
	/**
	 * This method logs test status and details.
	 * @param status The status to log
	 * @param details The details to log
	 */
	private static synchronized void log(Status status, String details) {
		if(ExtentListener.TESTS.get() != null) {
			switch(status) {
			case INFO:
				ExtentListener.TESTS.get().info(details);
				break;
			case PASS:
				ExtentListener.TESTS.get().pass(details);
				break;
			default:
				ExtentListener.TESTS.get().fail(details);
			}
		}
	}
}