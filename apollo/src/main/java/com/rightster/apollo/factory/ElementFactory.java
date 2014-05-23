package com.rightster.apollo.factory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.rightster.apollo.exception.MissingPropertyException;
import com.rightster.apollo.utils.PropertyUtils;
import com.rightster.apollo.utils.StackTraceUtils;

/**
 * Class to create a web element with the supplied element properties. Element
 * must be specified in the below format in properties file.
 * module.element.name=identifier,locator value ex.
 * content.create.button=xpath,//button[@class='form-button dropdown
 * ng-binding'] , content.upload.videos.link=id,fielupload etc.
 * 
 * @author Pradeepta Swain
 */
public class ElementFactory
{
    private static Logger     logger = Logger.getLogger(ElementFactory.class.getName());

    private static WebElement element;

    /**
     * Create a web element based on element locator.
     * 
     * @param driver
     *            - WebDriver
     * @param elementLocator
     *            - property key name used in locator files
     * @return - WebElement if fetched, return null otherwise
     * @throws NoSuchElementException
     * @throws MissingPropertyException
     * @throws NumberFormatException
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static WebElement createElement(final WebDriver driver, final String elementLocator)
            throws NoSuchElementException, MissingPropertyException
    {
        return createElement(driver, elementLocator, "");
    }

    public static WebElement createElement(final WebDriver driver, final String elementLocator, final String replacement)
            throws NoSuchElementException, MissingPropertyException
    {
        Long timeValue = null;

        try
        {
            timeValue = Long.valueOf(PropertyUtils.getProperty("apollo.element.fetch.timeout")).longValue();
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

        String propertyValue = "";

        try
        {
            propertyValue = PropertyUtils.getProperty(elementLocator);
        } catch (FileNotFoundException ex)
        {
            logger.debug(StackTraceUtils.stackTraceToString(ex));
        } catch (IOException ex)
        {
            logger.debug(StackTraceUtils.stackTraceToString(ex));
        } catch (MissingPropertyException e)
        {
            if (elementLocator.matches("(xpath|css[sS]elector|id|name|class)\\W+(\\w.*)"))
            {
                propertyValue = elementLocator;
            } else
            {
                logger.error("Property key: '" + elementLocator + "' is not found in properties file!");
                Assert.fail("Property key: '" + elementLocator + "' is not found in properties file!");
            }
        }

        final String PROPERTY_VALUE = propertyValue;

        WebDriverWait wait = new WebDriverWait(driver, timeValue);

        try
        {
            wait.until(new ExpectedCondition<Boolean>()
            {
                public Boolean apply(WebDriver webDriver)
                {
                    element = webDriver.findElement(getProperties(driver, PROPERTY_VALUE, elementLocator, replacement));
                    return element != null;
                }
            });
        } catch (TimeoutException ex)
        {
            logger.error("Timed out after " + timeValue + " seconds waiting for " + elementLocator + "::"
                    + propertyValue);
        }

        if (element != null)
        {
            return element;
        } else
        {
            logger.error("Element could not be found using locator: " + elementLocator + "::" + propertyValue);
        }

        return null;
    }

    /**
     * Return a By locator identified by the locator during runtime
     * 
     * @param driver
     *            - WebDriver
     * @param propertyValue
     *            - property value corresponding to property key in locators
     *            file
     * @param elementLocator
     *            - property key in locators file
     * @return - By locator
     */
    public static By getProperties(WebDriver driver, String propertyValue, String elementLocator, String replacement)
    {
        String[] params = null;

        params = propertyValue.split(",");

        String byLocator = params[0] + ",";
        String strippedLocator = propertyValue.replace(byLocator, "");
        String locatorAfterReplacement = "";

        if (strippedLocator.contains("$value") && replacement.trim().length() != 0)
        {
            locatorAfterReplacement = strippedLocator.replace("$value", replacement);
        } else
        {
            locatorAfterReplacement = strippedLocator;
        }

        if (params[0] != null || locatorAfterReplacement != null)
        {
            Class<By> cl = By.class;
            Method m1 = null;

            try
            {
                m1 = cl.getMethod(params[0], new Class[] { String.class });
            } catch (NoSuchMethodException e)
            {
                Assert.fail("Format of property value: " + propertyValue + " for property key: " + elementLocator
                        + " is not proper in the property file!");
            } catch (SecurityException ex)
            {
                logger.debug(StackTraceUtils.stackTraceToString(ex));
            }

            By s1 = null;

            try
            {
                s1 = (By) m1.invoke(null, new Object[] { locatorAfterReplacement });
            } catch (IllegalAccessException ex)
            {
                logger.debug(StackTraceUtils.stackTraceToString(ex));
            } catch (IllegalArgumentException ex)
            {
                logger.debug(StackTraceUtils.stackTraceToString(ex));
            } catch (InvocationTargetException ex)
            {
                logger.debug(StackTraceUtils.stackTraceToString(ex));
            }

            return s1;
        } else
        {
            logger.error("Format of property value: " + propertyValue + " for property key: " + elementLocator
                    + " is not proper in the property file!");
            Assert.fail("Format of property value: " + propertyValue + " for property key: " + elementLocator
                    + " is not proper in the property file!");
        }

        return null;
    }
}
