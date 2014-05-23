package com.rightster.apollo.pageactions;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import com.rightster.apollo.base.BasePage;

/**
 * This page contains all the actions specific to Forgot Your Password page.
 * 
 * @author Pradeepta Swain
 */
public class ForgotYourPasswordPage extends BasePage
{
    private static Logger logger = Logger.getLogger(ForgotYourPasswordPage.class.getName());

    public ForgotYourPasswordPage(WebDriver driver)
    {
        super(driver);
    }
}
