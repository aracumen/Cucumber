package com.rightster.apollo.pageactions;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import com.rightster.apollo.base.BasePage;

/**
 * This page contains all the actions specific to Register page.
 * 
 * @author Pradeepta Swain
 */
public class RegisterPage extends BasePage
{
    private static Logger logger = Logger.getLogger(RegisterPage.class.getName());

    public RegisterPage(WebDriver driver)
    {
        super(driver);
    }
}
