package com.rightster.apollo.pageactions;

import org.apache.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.rightster.apollo.exception.MissingPropertyException;
import com.rightster.apollo.base.BasePage;

/**
 * This page contains all the actions specific to Sign In page.
 * 
 * @author Pradeepta Swain
 */
public class SignInPage extends BasePage
{
    private static Logger logger = Logger.getLogger(SignInPage.class.getName());

    public SignInPage(WebDriver driver)
    {
        super(driver);
    }

    public void typeEmailAddress(String emailAddress) throws NoSuchElementException, MissingPropertyException
    {
        type("signin.email.address", emailAddress);
    }

    public void typePassword(String password) throws NoSuchElementException, MissingPropertyException
    {
        type("signin.password", password);
    }

    public ContentPage clickOnSignInButton() throws NoSuchElementException, MissingPropertyException
    {
        click("signin.signin.button");
        return PageFactory.initElements(driver, ContentPage.class);
    }

    public RegisterPage clickOnLookingToRegisterLink() throws NoSuchElementException, MissingPropertyException
    {
        click("");
        return PageFactory.initElements(driver, RegisterPage.class);
    }

    public ForgotYourPasswordPage clickOnForgottenYourPasswordLink() throws NoSuchElementException,
            MissingPropertyException
    {
        click("");
        return PageFactory.initElements(driver, ForgotYourPasswordPage.class);
    }
}
