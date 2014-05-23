package com.rightster.apollo.base;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidParameterException;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.rightster.apollo.exception.CapabilityNotPresentException;
import com.rightster.apollo.exception.MissingPropertyException;
import com.rightster.apollo.factory.CapabilitiesFactory;
import com.rightster.apollo.factory.DriverFactory;
import com.rightster.apollo.utils.PropertyUtils;
import com.rightster.apollo.utils.StackTraceUtils;

import cucumber.api.junit.Cucumber;

/**
 * BaseRunnerClass is the super class of all runner classes which run the
 * cucumber feature files. Primary responsibility of this class is to launch the
 * browser and open the url before executing any feature file. Once all the
 * scenarios have been executed, it will close the browser and will quit the
 * driver instance.
 * 
 * @author Pradeepta Swain
 */
@RunWith(Cucumber.class)
public class BaseRunnerClass
{
    private static Logger              logger = Logger.getLogger(BaseRunnerClass.class.getName());

    protected static WebDriver         driver;
    private static DesiredCapabilities capabilities;

    private static String              browser;
    private static String              version;
    private static String              platform;

    /**
     * If browser,browser version and platform parameters can not be obtained
     * from System property, then fetch them from project.properties file.
     */
    static
    {
        try
        {
            if (System.getProperty("browser") == null)
            {
                browser = PropertyUtils.getProperty("apollo.default.browser");
            } else
            {
                browser = System.getProperty("browser");
            }

            if (System.getProperty("version") == null)
            {
                version = PropertyUtils.getProperty("apollo.default.browser.version");
            } else
            {
                version = System.getProperty("version");
            }

            if (System.getProperty("platform") == null)
            {
                platform = PropertyUtils.getProperty("apollo.default.platform");
            } else
            {
                platform = System.getProperty("platform");
            }
        } catch (FileNotFoundException ex)
        {
            logger.debug(StackTraceUtils.stackTraceToString(ex));
        } catch (IOException ex)
        {
            logger.debug(StackTraceUtils.stackTraceToString(ex));
        } catch (MissingPropertyException ex)
        {
            logger.debug(StackTraceUtils.stackTraceToString(ex));
        }
    }

    /*
     * Initialize logging system. Launch the browser and navigate to the
     * application url.
     */
    @BeforeClass
    public static void init() throws CapabilityNotPresentException, FileNotFoundException, MissingPropertyException,
            IOException
    {
        String workingDir = System.getProperty("user.dir");

        DOMConfigurator.configureAndWatch(workingDir + File.separator + "config" + File.separator + "log4j.xml");

        String debug_mode = PropertyUtils.getProperty("apollo.debug.mode");

        if (debug_mode != null && debug_mode.equalsIgnoreCase("on"))
        {
            LogManager.getRootLogger().setLevel(Level.DEBUG);
        } else
        {
            LogManager.getRootLogger().setLevel(Level.INFO);
        }

        String applicationUrl = PropertyUtils.getProperty("apollo.url");
        String signInPageTitle = PropertyUtils.getProperty("apollo.signin.page.title");

        if (applicationUrl == null || applicationUrl.trim().length() == 0)
        {
            throw new InvalidParameterException("Application url is null or is empty!");
        }

        capabilities = CapabilitiesFactory.createCapability(browser, version, platform);

        driver = DriverFactory.createDriver(capabilities);

        driver.get(applicationUrl);

        driver.manage().window().maximize();

        WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 300);

        try
        {
            wait.until(ExpectedConditions.titleContains(signInPageTitle));
        } catch (TimeoutException ex)
        {
            ex.printStackTrace();
            logger.debug(StackTraceUtils.stackTraceToString(ex));
        }
    }

    /*
     * Returns a driver instance.
     */
    public static WebDriver getDriverInstance()
    {
        return driver;
    }

    /*
     * After all the scenarios have been executed, close the browser and quit
     * the driver.
     */
    @AfterClass
    public static void terminate()
    {
        driver.close();
        driver.quit();
    }
}
