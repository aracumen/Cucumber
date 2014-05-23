package com.rightster.apollo.base;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.rightster.apollo.exception.MissingPropertyException;
import com.rightster.apollo.factory.ElementFactory;
import com.rightster.apollo.utils.PropertyUtils;
import com.rightster.apollo.utils.StackTraceUtils;

/**
 * All pages must extend BasePage. BasePage uses wrappers around raw webdriver
 * commands to gain more control on timeouts and other exceptions.
 * 
 * @author Pradeepta Swain
 */
public class BasePage
{
    protected WebDriver   driver;

    private static Logger logger = Logger.getLogger(BasePage.class.getName());
    protected static long timeout;

    public BasePage(WebDriver driver)
    {
        this.driver = driver;

        try
        {
            timeout = Long.valueOf(PropertyUtils.getProperty("apollo.element.fetch.timeout"));
        } catch (Exception ex)
        {
            logger.debug(StackTraceUtils.stackTraceToString(ex));
        }
    }

    /**
     * Type into element.
     * 
     * @param locator
     * @param textToType
     * @throws NoSuchElementException
     * @throws MissingPropertyException
     */
    public void type(String locator, String textToType) throws NoSuchElementException, MissingPropertyException
    {
        type(locator, textToType, "", false);
    }

    public void type(String locator, String textToType, String replacement) throws NoSuchElementException,
            MissingPropertyException
    {
        type(locator, textToType, replacement, false);
    }

    public void type(String locator, String textToType, boolean ignoreNoSuchElementException)
            throws NoSuchElementException, MissingPropertyException
    {
        type(locator, textToType, "", ignoreNoSuchElementException);
    }

    public void type(String locator, String textToType, String replacement, boolean ignoreNoSuchElementException)
            throws NoSuchElementException, MissingPropertyException
    {
        WebElement element = ElementFactory.createElement(driver, locator, replacement);

        if (ignoreNoSuchElementException)
        {
            try
            {
                element.sendKeys(textToType);
                logger.info("Successfully typed text '" + textToType + "' on element '" + element + "'.");
            } catch (NoSuchElementException ex)
            {
                logger.debug(StackTraceUtils.stackTraceToString(ex));
            }
        } else
        {
            element.sendKeys(textToType);
            logger.info("Successfully typed text '" + textToType + "' on element '" + element + "'.");
        }
    }

    /**
     * Click on element.
     * 
     * @param locator
     * @throws NoSuchElementException
     * @throws MissingPropertyException
     */
    public void click(String locator) throws NoSuchElementException, MissingPropertyException
    {
        click(locator, "", false);
    }

    public void click(String locator, String replacement) throws NoSuchElementException, MissingPropertyException
    {
        click(locator, replacement, false);
    }

    public void click(String locator, boolean ignoreNoSuchElementException) throws NoSuchElementException,
            MissingPropertyException
    {
        click(locator, "", ignoreNoSuchElementException);
    }

    public void click(String locator, String replacement, boolean ignoreNoSuchElementException)
            throws NoSuchElementException, MissingPropertyException
    {
        WebElement element = ElementFactory.createElement(driver, locator, replacement);

        if (ignoreNoSuchElementException)
        {
            try
            {
                element.click();
            } catch (NoSuchElementException ex)
            {
                logger.debug(StackTraceUtils.stackTraceToString(ex));
            }
        } else
        {
            element.click();
        }

        logger.info("Successfully performed click on element '" + element + "'.");
    }

    /**
     * Mouseover on element.
     * 
     * @param locator
     * @throws NoSuchElementException
     * @throws MissingPropertyException
     */
    public void mouseOver(String locator) throws NoSuchElementException, MissingPropertyException
    {
        mouseOver(locator, "", false);
    }

    public void mouseOver(String locator, String replacement) throws NoSuchElementException, MissingPropertyException
    {
        mouseOver(locator, replacement, false);
    }

    public void mouseOver(String locator, boolean ignoreNoSuchElementException) throws NoSuchElementException,
            MissingPropertyException
    {
        mouseOver(locator, "", ignoreNoSuchElementException);
    }

    public void mouseOver(String locator, String replacement, boolean ignoreNoSuchElementException)
            throws NoSuchElementException, MissingPropertyException
    {
        WebElement element = ElementFactory.createElement(driver, locator, replacement);

        if (ignoreNoSuchElementException)
        {
            try
            {
                Actions builder = new Actions(driver);
                Action hoveraction = builder.moveToElement(element).build();
                hoveraction.perform();

                logger.info("MouseOver Action performed on element: " + element.toString());

            } catch (NoSuchElementException ex)
            {
                logger.debug(StackTraceUtils.stackTraceToString(ex));
            }
        } else
        {
            Actions builder = new Actions(driver);
            Action hoveraction = builder.moveToElement(element).build();
            hoveraction.perform();

            logger.info("MouseOver Action performed on element: " + element.toString());
        }
    }

    /**
     * Right click on element.
     * 
     * @param locator
     * @throws NoSuchElementException
     * @throws MissingPropertyException
     */
    public void rightClick(String locator) throws NoSuchElementException, MissingPropertyException
    {
        rightClick(locator, "");
    }

    public void rightClick(String locator, String replacement) throws NoSuchElementException, MissingPropertyException
    {
        rightClick(locator, replacement, false);
    }

    public void rightClick(String locator, boolean ignoreNoSuchElementException) throws NoSuchElementException,
            MissingPropertyException
    {
        rightClick(locator, "", ignoreNoSuchElementException);
    }

    public void rightClick(String locator, String replacement, boolean ignoreNoSuchElementException)
            throws NoSuchElementException, MissingPropertyException
    {
        WebElement element = ElementFactory.createElement(driver, locator, replacement);

        if (ignoreNoSuchElementException)
        {
            try
            {
                Actions builder = new Actions(driver);
                builder.contextClick(element).perform();

                logger.info("Successfully performed right click on element '" + element + "'.");

            } catch (NoSuchElementException ex)
            {
                logger.debug(StackTraceUtils.stackTraceToString(ex));
            }
        } else
        {
            Actions builder = new Actions(driver);
            builder.contextClick(element).perform();

            logger.info("Successfully performed right click on element '" + element + "'.");
        }
    }

    /**
     * Clear the element. Used for clearing the text boxes.
     * 
     * @param locator
     * @throws NoSuchElementException
     * @throws MissingPropertyException
     */
    public void clear(String locator) throws NoSuchElementException, MissingPropertyException
    {
        clear(locator, "", false);
    }

    public void clear(String locator, String replacement) throws NoSuchElementException, MissingPropertyException
    {
        clear(locator, replacement, false);
    }

    public void clear(String locator, boolean ignoreNoSuchElementException) throws NoSuchElementException,
            MissingPropertyException
    {
        clear(locator, "", ignoreNoSuchElementException);
    }

    public void clear(String locator, String replacement, boolean ignoreNoSuchElementException)
            throws NoSuchElementException, MissingPropertyException
    {
        WebElement element = ElementFactory.createElement(driver, locator, replacement);

        if (ignoreNoSuchElementException)
        {
            try
            {
                element.clear();
                logger.info("Successfully cleared element '" + element + "'.");

            } catch (NoSuchElementException ex)
            {
                logger.debug(StackTraceUtils.stackTraceToString(ex));
            }
        } else
        {
            element.clear();
            logger.info("Successfully cleared element '" + element + "'.");
        }
    }

    /**
     * Returns element text.
     * 
     * @param locator
     * @return
     * @throws NoSuchElementException
     * @throws MissingPropertyException
     */
    public String getText(String locator) throws NoSuchElementException, MissingPropertyException
    {
        return getText(locator, "", false);
    }

    public String getText(String locator, String replacement) throws NoSuchElementException, MissingPropertyException
    {
        return getText(locator, replacement, false);
    }

    public String getText(String locator, boolean ignoreNoSuchElementException) throws NoSuchElementException,
            MissingPropertyException
    {
        return getText(locator, "", ignoreNoSuchElementException);
    }

    public String getText(String locator, String replacement, boolean ignoreNoSuchElementException)
            throws NoSuchElementException, MissingPropertyException
    {
        WebElement element = ElementFactory.createElement(driver, locator, replacement);

        if (ignoreNoSuchElementException)
        {
            try
            {
                logger.info("Get Text Returned: " + element.getText());
                return element.getText();

            } catch (NoSuchElementException ex)
            {
                logger.debug(StackTraceUtils.stackTraceToString(ex));
            }
        } else
        {
            logger.info("Get Text Returned: " + element.getText());
            return element.getText();
        }

        return null;
    }

    /**
     * Returns element tag name. Ex: getElementTagName(locator) will return
     * corresponding tag like <h1><div> etc..
     * 
     * @param locator
     * @param replacement
     * @return
     * @throws NoSuchElementException
     * @throws MissingPropertyException
     */
    public String getElementTagName(String locator) throws NoSuchElementException, MissingPropertyException
    {
        return getElementTagName(locator, "", false);
    }

    public String getElementTagName(String locator, String replacement) throws NoSuchElementException,
            MissingPropertyException
    {
        return getElementTagName(locator, replacement, false);
    }

    public String getElementTagName(String locator, boolean ignoreNoSuchElementException)
            throws NoSuchElementException, MissingPropertyException
    {
        return getElementTagName(locator, "", ignoreNoSuchElementException);
    }

    public String getElementTagName(String locator, String replacement, boolean ignoreNoSuchElementException)
            throws NoSuchElementException, MissingPropertyException
    {
        WebElement element = ElementFactory.createElement(driver, locator, replacement);

        if (ignoreNoSuchElementException)
        {
            try
            {
                logger.info("Get Element Tag Name Returned: " + element.getTagName());
                return element.getTagName();

            } catch (NoSuchElementException ex)
            {
                logger.debug(StackTraceUtils.stackTraceToString(ex));
            }
        } else
        {
            logger.info("Get Element Tag Name Returned: " + element.getTagName());
            return element.getTagName();
        }

        return null;
    }

    /**
     * Returns element attribute value. Ex:
     * getElementAttributeValue(locator,"class") will return class attribute.
     * 
     * @param locator
     * @param attributeName
     * @return
     * @throws NoSuchElementException
     * @throws MissingPropertyException
     */
    public String getElementAttributeValue(String locator, String attributeName) throws NoSuchElementException,
            MissingPropertyException
    {
        return getElementAttributeValue(locator, attributeName, "", false);
    }

    public String getElementAttributeValue(String locator, String attributeName, String replacement)
            throws NoSuchElementException, MissingPropertyException
    {
        return getElementAttributeValue(locator, attributeName, replacement, false);
    }

    public String getElementAttributeValue(String locator, String attributeName, boolean ignoreNoSuchElementException)
            throws NoSuchElementException, MissingPropertyException
    {
        return getElementAttributeValue(locator, attributeName, "", ignoreNoSuchElementException);
    }

    public String getElementAttributeValue(String locator, String attributeName, String replacement,
            boolean ignoreNoSuchElementException) throws NoSuchElementException, MissingPropertyException
    {
        WebElement element = ElementFactory.createElement(driver, locator, replacement);

        if (ignoreNoSuchElementException)
        {
            try
            {
                logger.info("Get Element Attribute Returned: " + element.getAttribute(attributeName));
                return element.getAttribute(attributeName);

            } catch (NoSuchElementException ex)
            {
                logger.debug(StackTraceUtils.stackTraceToString(ex));
            }
        } else
        {
            logger.info("Get Element Attribute Returned: " + element.getAttribute(attributeName));
            return element.getAttribute(attributeName);
        }

        return null;
    }

    /**
     * Open url.
     * 
     * @param url
     */
    public void openUrl(String url)
    {
        logger.info("Opening URL: " + url);
        driver.get(url);
    }

    /**
     * Switch to frame by frame number.
     * 
     * @param frameNumber
     */
    public void switchToFrameByFrameNumber(int frameNumber)
    {
        logger.info("Switching to frame based on frame number: " + frameNumber);
        driver.switchTo().frame(frameNumber);
    }

    /**
     * Switch to frame by frame name.
     * 
     * @param frameName
     */
    public void switchToFrameByFrameName(String frameName)
    {
        logger.info("Switching to frame based on frame name: " + frameName);
        driver.switchTo().frame(frameName);
    }

    /**
     * Switch to frame by element.
     * 
     * @param locator
     * @param replacement
     * @throws NoSuchElementException
     * @throws MissingPropertyException
     */
    public void switchToFrameByElement(String locator) throws NoSuchElementException, MissingPropertyException
    {
        switchToFrameByElement(locator, "");
    }

    public void switchToFrameByElement(String locator, String replacement) throws NoSuchElementException,
            MissingPropertyException
    {
        WebElement element = ElementFactory.createElement(driver, locator, replacement);
        logger.info("Switching to frame based on element: " + locator);
        driver.switchTo().frame(element);
    }

    /**
     * Switch to window by window name.
     * 
     * @param windowName
     */
    public void switchToWindowByWindowName(String windowName)
    {
        logger.info("Switching to window based on window name: " + windowName);
        driver.switchTo().window(windowName);
    }

    /**
     * Switch to window by window number.
     * 
     * @param windowNumber
     */
    public void switchToWindowByWindowNumber(int windowNumber)
    {
        Object[] winhandles = driver.getWindowHandles().toArray();
        logger.info("Switching to window based on window number: " + windowNumber);
        driver.switchTo().window((String) winhandles[windowNumber]);
    }

    /**
     * Perform submit on element.
     * 
     * @param locator
     * @param replacement
     * @throws NoSuchElementException
     * @throws MissingPropertyException
     */
    public void submit(String locator) throws NoSuchElementException, MissingPropertyException
    {
        submit(locator, "");
    }

    public void submit(String locator, String replacement) throws NoSuchElementException, MissingPropertyException
    {
        WebElement element = ElementFactory.createElement(driver, locator, replacement);
        element.submit();
        logger.info("Successfully performed submit on element '" + element + "'.");
    }

    /**
     * Click on browser refresh button.
     */
    public void refreshBrowser()
    {
        driver.navigate().refresh();
        logger.info("Successfully refreshed page");
    }

    /**
     * Click on browser back button.
     */
    public void clickOnBrowserBackButton()
    {
        driver.navigate().back();
        logger.info("Successfully navigated back");
    }

    /**
     * Click on browser forward button.
     */
    public void clickOnBrowserForwardButton()
    {
        driver.navigate().forward();
        logger.info("Successfully navigated forward");
    }

    /**
     * Assert text is present on page.
     * 
     * @param textToBeVerified
     */
    public void assertTextPresent(String textToBeVerified)
    {
        WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, timeout);

        boolean isTextPresentOnPage = true;

        try
        {
            wait.until(BaseExpectedConditions.pageContainsText(textToBeVerified));
        } catch (TimeoutException ex)
        {
            isTextPresentOnPage = false;
        }

        Assert.assertTrue(textToBeVerified + " is not present on page", isTextPresentOnPage);
    }

    /**
     * Assert text is not present on page.
     * 
     * @param textToBeVerified
     */
    public void assertTextNotPresent(String textToBeVerified)
    {
        WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, timeout);

        boolean isTextPresentOnPage = true;

        try
        {
            wait.until(BaseExpectedConditions.pageContainsText(textToBeVerified));
        } catch (TimeoutException ex)
        {
            isTextPresentOnPage = false;
        }

        Assert.assertFalse(textToBeVerified + " is present on page", isTextPresentOnPage);
    }

    /**
     * Assert element is present on page.
     * 
     * @param locator
     * @throws NoSuchElementException
     * @throws MissingPropertyException
     */
    public void assertElementPresent(String locator) throws NoSuchElementException, MissingPropertyException
    {
        assertElementPresent(locator, "", "");
    }

    public void assertElementPresent(String locator, String replacement, String errorMessage)
            throws NoSuchElementException, MissingPropertyException
    {
        WebElement element = ElementFactory.createElement(driver, locator, replacement);

        Assert.assertTrue(errorMessage, (element != null) ? true : false);
    }

    /**
     * Assert element is not present on page.
     * 
     * @param locator
     * @throws NoSuchElementException
     * @throws MissingPropertyException
     */
    public void assertElementNotPresent(String locator) throws NoSuchElementException, MissingPropertyException
    {
        assertElementNotPresent(locator, "", "");
    }

    public void assertElementNotPresent(String locator, String replacement, String errorMessage)
            throws NoSuchElementException, MissingPropertyException
    {
        WebElement element = ElementFactory.createElement(driver, locator, replacement);

        Assert.assertFalse(errorMessage, (element == null) ? true : false);
    }

    /**
     * Assert an element contains expected text.
     * 
     * @param locator
     * @param textToVerify
     * @throws NoSuchElementException
     * @throws MissingPropertyException
     */
    public void assertElementContainsText(String locator, String textToVerify) throws NoSuchElementException,
            MissingPropertyException
    {
        assertElementContainsText(locator, "", textToVerify, "");
    }

    public void assertElementContainsText(String locator, String replacement, String textToVerify, String errorMessage)
            throws NoSuchElementException, MissingPropertyException
    {
        Assert.assertTrue(errorMessage, getText(locator, replacement).contains(textToVerify));
    }

    /**
     * Assert an element does not contain expected text.
     * 
     * @param locator
     * @param replacement
     * @param textToVerify
     * @param errorMessage
     * @throws NoSuchElementException
     * @throws MissingPropertyException
     */
    public void assertElementDoesNotContainText(String locator, String textToVerify) throws NoSuchElementException,
            MissingPropertyException
    {
        assertElementDoesNotContainText(locator, "", textToVerify, "");
    }

    public void assertElementDoesNotContainText(String locator, String replacement, String textToVerify,
            String errorMessage) throws NoSuchElementException, MissingPropertyException
    {
        Assert.assertFalse(errorMessage, getText(locator, replacement).contains(textToVerify));
    }

    /**
     * Wait until the page is loaded. Currently it checks whether the expected
     * page title matches the actual page title or not!
     * 
     * @param pageTitle
     * @throws MissingPropertyException
     */
    public void waitForPageToBeLoaded(String pageTitle) throws MissingPropertyException
    {
        Long timeout = null;

        try
        {
            timeout = Long.valueOf(PropertyUtils.getProperty("apollo.element.page.timeout")).longValue();
        } catch (NumberFormatException ex)
        {
            logger.debug(StackTraceUtils.stackTraceToString(ex));
        } catch (FileNotFoundException ex)
        {
            logger.debug(StackTraceUtils.stackTraceToString(ex));
        } catch (IOException ex)
        {
            logger.debug(StackTraceUtils.stackTraceToString(ex));
        }

        new WebDriverWait(driver, timeout).until(ExpectedConditions.titleContains(pageTitle));
    }

    /**
     * Select all options that display text matching the argument. That is, when
     * given "Bar" this would select an option like: <option
     * value="foo">Bar</option>
     * 
     * @param locator
     * @param optionText
     * @throws NoSuchElementException
     * @throws MissingPropertyException
     */
    public void selectByText(String locator, String optionText) throws NoSuchElementException, MissingPropertyException
    {
        selectByText(locator, "", optionText);
    }

    public void selectByText(String locator, String replacement, String optionText) throws NoSuchElementException,
            MissingPropertyException
    {
        WebElement selectDropdown = ElementFactory.createElement(driver, locator);
        Select optionList = new Select(selectDropdown);
        optionList.selectByVisibleText(optionText);
    }

    /**
     * Select the option at the given index. This is done by examing the "index"
     * attribute of an element, and not merely by counting.
     * 
     * @param locator
     * @param index
     * @throws NoSuchElementException
     * @throws MissingPropertyException
     */
    public void selectByIndex(String locator, int index) throws NoSuchElementException, MissingPropertyException
    {
        selectByIndex(locator, "", index);
    }

    public void selectByIndex(String locator, String replacement, int index) throws NoSuchElementException,
            MissingPropertyException
    {
        WebElement selectDropdown = ElementFactory.createElement(driver, locator);
        Select optionList = new Select(selectDropdown);
        optionList.selectByIndex(index);
    }

    /**
     * Select all options that have a value matching the argument. That is, when
     * given "foo" this would select an option like: <option
     * value="foo">Bar</option>
     * 
     * @param locator
     * @param optionValue
     * @throws NoSuchElementException
     * @throws MissingPropertyException
     */
    public void selectByOptionValue(String locator, String optionValue) throws NoSuchElementException,
            MissingPropertyException
    {
        selectByOptionValue(locator, "", optionValue);
    }

    public void selectByOptionValue(String locator, String replacement, String optionValue)
            throws NoSuchElementException, MissingPropertyException
    {
        WebElement selectDropdown = ElementFactory.createElement(driver, locator);
        Select optionList = new Select(selectDropdown);
        optionList.selectByValue(optionValue);
    }

    /**
     * Returns true or false based on whether element is present!
     * 
     * @param locator
     * @return
     * @throws NoSuchElementException
     * @throws MissingPropertyException
     */
    public boolean isElementPresent(String locator) throws NoSuchElementException, MissingPropertyException
    {
        WebElement element = ElementFactory.createElement(driver, locator);
        if (element == null)
        {
            return false;
        } else
        {
            return true;
        }
    }

    /**
     * Returns true or false based on whether element is visible!
     * 
     * @param locator
     * @return
     * @throws NoSuchElementException
     * @throws MissingPropertyException
     */
    public boolean isElementVisible(String locator) throws NoSuchElementException, MissingPropertyException
    {
        WebElement element = ElementFactory.createElement(driver, locator);
        try
        {
            if (element.isDisplayed())
            {
                return true;
            } else
            {
                return false;
            }
        } catch (StaleElementReferenceException ex)
        {
            return false;
        }
    }

    /**
     * Returns true or false based on whether element is enabled!
     * 
     * @param locator
     * @return
     * @throws NoSuchElementException
     * @throws MissingPropertyException
     */
    public boolean isElementEnabled(String locator) throws NoSuchElementException, MissingPropertyException
    {
        WebElement element = ElementFactory.createElement(driver, locator);
        if (element.isEnabled())
        {
            return true;
        } else
        {
            return false;
        }
    }

    /**
     * @return - Current Page URL.
     */
    public String getCurrentUrl()
    {
        return driver.getCurrentUrl();
    }
}
