package com.app.base;

import org.openqa.selenium.WebDriver;

import com.app.pageComponents.PageHeader;

/**
 * This class is inherited by every page class except the LoginPage
 * it contains shared components.
 * @author Muana Kimba
 */
public abstract class ReusablePage extends Page {
	
	// PAGE COMPONENTS
	public PageHeader pageHeader;
	
	// PAGE INITIALIZATION
	public ReusablePage(WebDriver driver) {
		super(driver);
		this.pageHeader = new PageHeader(driver);
	}
}