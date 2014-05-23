package com.rightster.apollo.base;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

/**
 * Apart from the general explicit conditions provided by the WebDriver, this
 * class contains other expected conditions that can be used throughout the
 * framework.
 * 
 * @author Pradeepta Swain
 */
public class BaseExpectedConditions
{
    public static ExpectedCondition<WebElement> elementToBeClickable(final WebElement element)
    {
        return new ExpectedCondition<WebElement>()
        {
            public ExpectedCondition<WebElement> visibilityOfElementLocated = BaseExpectedConditions
                                                                                    .visibilityOfElementLocated(element);

            public WebElement apply(WebDriver driver)
            {
                WebElement element = visibilityOfElementLocated.apply(driver);
                try
                {
                    if (element != null && element.isEnabled())
                    {
                        return element;
                    } else
                    {
                        return null;
                    }
                } catch (StaleElementReferenceException e)
                {
                    return null;
                }
            }

            @Override
            public String toString()
            {
                return "element to be clickable: " + element;
            }
        };
    }

    public static ExpectedCondition<WebElement> visibilityOfElementLocated(final WebElement element)
    {
        return new ExpectedCondition<WebElement>()
        {
            public WebElement apply(WebDriver driver)
            {
                try
                {
                    if (element != null && element.isDisplayed())
                    {
                        return element;
                    } else
                    {
                        return null;
                    }
                } catch (StaleElementReferenceException e)
                {
                    return null;
                }
            }

            @Override
            public String toString()
            {
                return "visibility of element located by " + element;
            }
        };
    }

    public static ExpectedCondition<Boolean> signOutRedirectUrlContains(final String redirectUrl)
    {
        return new ExpectedCondition<Boolean>()
        {
            public Boolean apply(WebDriver driver)
            {
                try
                {
                    return driver.getCurrentUrl().contains(redirectUrl);
                } catch (StaleElementReferenceException e)
                {
                    return false;
                }
            }
        };
    }

    public static ExpectedCondition<Boolean> pageContainsText(final String textTobePresent)
    {
        return new ExpectedCondition<Boolean>()
        {
            public Boolean apply(WebDriver driver)
            {
                try
                {
                    return driver.getPageSource().contains(textTobePresent);
                } catch (StaleElementReferenceException e)
                {
                    return false;
                }
            }
        };
    }
}
