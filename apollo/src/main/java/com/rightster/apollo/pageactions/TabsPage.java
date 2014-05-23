package com.rightster.apollo.pageactions;

import org.apache.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.rightster.apollo.exception.MissingPropertyException;
import com.rightster.apollo.utils.StackTraceUtils;
import com.rightster.apollo.base.BasePage;

/**
 * This page contains actions to navigate to specific tab.
 * 
 * @author Pradeepta Swain
 */
public class TabsPage extends BasePage
{
    private static Logger logger = Logger.getLogger(SignInPage.class.getName());

    public TabsPage(WebDriver driver)
    {
        super(driver);
    }

    public BasePage clickOnTab(String tabName) throws NoSuchElementException, MissingPropertyException
    {
        if (tabName.trim().equalsIgnoreCase("content"))
        {
            click("tabs.content");
            waitUntilPageTitleIs("Library Content");
            return PageFactory.initElements(driver, ContentPage.class);

        } else if (tabName.trim().equalsIgnoreCase("labels"))
        {
            click("tabs.labels");
            waitUntilPageTitleIs("Library Labels");
            return PageFactory.initElements(driver, LabelsPage.class);
        } else if (tabName.trim().equalsIgnoreCase("automation"))
        {
            click("tabs.automation");
            waitUntilPageTitleIs("Automation");
            return PageFactory.initElements(driver, AutomationPage.class);
        } else if (tabName.trim().equalsIgnoreCase("ingestanddelivery"))
        {
            click("tabs.ingestanddelivery");
            waitUntilPageTitleIs("Ingest & Delivery");
            return PageFactory.initElements(driver, IngestAndDeliveryPage.class);
        } else if (tabName.trim().equalsIgnoreCase("settings"))
        {
            click("tabs.settings");
            waitUntilPageTitleIs("Library Settings");
            return PageFactory.initElements(driver, SettingsPage.class);
        }

        return null;
    }

    public void waitUntilPageTitleIs(String title)
    {
        WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 300);

        try
        {
            wait.until(ExpectedConditions.titleContains(title));
        } catch (TimeoutException ex)
        {
            ex.printStackTrace();
            logger.debug(StackTraceUtils.stackTraceToString(ex));
        }
    }

    public SignInPage clickOnSignOut() throws NoSuchElementException, MissingPropertyException
    {
        click("tabs.logout");
        waitUntilPageTitleIs("Sign In");
        return PageFactory.initElements(driver, SignInPage.class);
    }
}
