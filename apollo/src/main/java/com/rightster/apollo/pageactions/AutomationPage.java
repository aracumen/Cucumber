package com.rightster.apollo.pageactions;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import com.rightster.apollo.base.BasePage;

/**
 * This page contains all the actions specific to Automation page.
 * 
 * @author Pradeepta Swain
 */
public class AutomationPage extends BasePage
{
    private static Logger logger = Logger.getLogger(AutomationPage.class.getName());

    public AutomationPage(WebDriver driver)
    {
        super(driver);
    }
}
