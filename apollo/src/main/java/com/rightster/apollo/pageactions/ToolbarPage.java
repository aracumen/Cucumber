package com.rightster.apollo.pageactions;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import com.rightster.apollo.base.BasePage;

/**
 * This page contains actions to click on different icons on toolbar. ex.
 * Library,Intelligence,Account and Build.
 * 
 * @author Pradeepta Swain
 */
public class ToolbarPage extends BasePage
{
    private static Logger logger = Logger.getLogger(SignInPage.class.getName());

    public ToolbarPage(WebDriver driver)
    {
        super(driver);
    }
}
