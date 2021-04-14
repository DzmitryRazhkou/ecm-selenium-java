package com.app.constants;

/**
 * This class contains the title and URL of page object classes.
 * @author Muana Kimba
 */
public final class PageInfo {

	// CONSTRUCTOR
	private PageInfo() throws Exception {
		throw new Exception();
	}

	// LOGIN PAGE
	public static final String LOGIN_PAGE_TITLE = "Alfresco » Login";
	public static final String LOGIN_PAGE_URL = System.getProperty("url");
	public static final String LOGIN_ERROR_PAGE_URL = LOGIN_PAGE_URL + "?error=true";

	// DASHBOARD PAGE
	public static final String DASHBOARD_PAGE_TITLE = "Alfresco » User Dashboard";
	public static final String DASHBOARD_PAGE_URL = LOGIN_PAGE_URL + "user/admin/dashboard";
}