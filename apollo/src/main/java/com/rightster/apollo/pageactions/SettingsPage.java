package com.rightster.apollo.pageactions;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import com.rightster.apollo.base.BasePage;

/**
 * This page contains all the actions specific to Settings page.
 * 
 * @author Pradeepta Swain
 */
public class SettingsPage extends BasePage
{
    private static Logger logger = Logger.getLogger(SettingsPage.class.getName());

    public SettingsPage(WebDriver driver)
    {
        super(driver);
    }
}
