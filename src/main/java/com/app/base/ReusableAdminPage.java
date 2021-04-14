package com.app.base;
import org.openqa.selenium.WebDriver;

import com.app.pageComponents.AdminNavigation;

/**
 * This class is inherited by every admin page class
 * it contains shared components.
 * @author Muana Kimba
 */
public abstract class ReusableAdminPage extends ReusablePage {
	
	// PAGE COMPONENTS
	public AdminNavigation adminNavigation;
	
	// PAGE INITIALIZATION
	public ReusableAdminPage(WebDriver driver) {
		super(driver);
		this.adminNavigation = new AdminNavigation(driver);
	}
}