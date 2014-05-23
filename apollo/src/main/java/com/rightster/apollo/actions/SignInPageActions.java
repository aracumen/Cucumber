package com.rightster.apollo.actions;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.rightster.apollo.base.BasePage;
import com.rightster.apollo.exception.MissingPropertyException;
import com.rightster.apollo.pageactions.ContentPage;
import com.rightster.apollo.pageactions.SignInPage;
import com.rightster.apollo.pageactions.TabsPage;
import com.rightster.apollo.utils.PropertyUtils;

/**
 * SignIn page reflects the first login screen appears when an user hits the
 * apollo url. This page contains methods specific to a single task like, typing
 * into the email address field or clicking on signin button etc.
 * 
 * @author Pradeepta Swain
 */
public class SignInPageActions extends BasePage
{
    private static Logger logger = Logger.getLogger(SignInPageActions.class.getName());

    private SignInPage    signInPage;

    public SignInPageActions(WebDriver driver)
    {
        super(driver);

        signInPage = PageFactory.initElements(driver, SignInPage.class);
    }

    public ContentPage signIn() throws NoSuchElementException, MissingPropertyException, FileNotFoundException,
            IOException
    {
        String userName = "";
        String password = "";

        userName = PropertyUtils.getProperty("apollo.username");

        password = PropertyUtils.getProperty("apollo.password");

        if (userName == null || password == null)
        {
            throw new MissingPropertyException(
                    "apollo.username and/or apollo.password property is/are empty. Can not login to application!");
        }

        signInPage.typeEmailAddress(userName);
        signInPage.typePassword(password);
        signInPage.clickOnSignInButton();

        waitForPageToBeLoaded(PropertyUtils.getProperty("apollo.content.page.title"));

        logger.info("Successfully signed in to application using username: " + userName + " and password: " + password);

        return PageFactory.initElements(driver, ContentPage.class);
    }

    public ContentPage signInAs(String userName, String password) throws NoSuchElementException,
            MissingPropertyException, FileNotFoundException, IOException
    {
        if (userName == null || password == null)
        {
            throw new MissingPropertyException("username and/or password is/are empty. Can not login to application!");
        }

        signInPage.typeEmailAddress(userName);
        signInPage.typePassword(password);
        signInPage.clickOnSignInButton();

        waitForPageToBeLoaded(PropertyUtils.getProperty("apollo.content.page.title"));

        logger.info("Successfully signed in to application As username: " + userName + " and password: " + password);

        return PageFactory.initElements(driver, ContentPage.class);
    }

    public SignInPage signOut() throws NoSuchElementException, MissingPropertyException, FileNotFoundException,
            IOException
    {
        TabsPage tabsPage = PageFactory.initElements(driver, TabsPage.class);

        tabsPage.clickOnSignOut();

        waitForPageToBeLoaded(PropertyUtils.getProperty("apollo.sign.in.page.title"));

        logger.info("Successfully signed out of application");

        return PageFactory.initElements(driver, SignInPage.class);
    }
}
