package com.rightster.apollo.factory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.rightster.apollo.exception.MissingPropertyException;
import com.rightster.apollo.utils.PropertyUtils;
import com.rightster.apollo.utils.StackTraceUtils;

/**
 * Class to create appropriate driver based on the capability passed through
 * test methods. If apollo.use.grid is set to false, it will try to run the
 * tests on locally running selenium server, else it will run the tests on the
 * grid specified in apollo.grid.ip property. When setting apollo.use.grid
 * property to false, set the apollo.local.selenium.server.port to the port
 * where local selenium server is running.
 * 
 * @author Pradeepta Swain
 */
public class DriverFactory
{
    private static Logger logger = Logger.getLogger(DriverFactory.class.getName());
    private static URL    remoteWebDriverUrl;

    public static WebDriver createDriver(DesiredCapabilities capabilities) throws MissingPropertyException,
            FileNotFoundException, IOException
    {
        final String USE_GRID = PropertyUtils.getProperty("apollo.use.grid");
        if (USE_GRID == null || USE_GRID.length() == 0
                || !((USE_GRID.trim().equalsIgnoreCase("true") || USE_GRID.trim().equalsIgnoreCase("false"))))
        {
            logger.error("Invalid value supplied for apollo.use.grid property. It can only be'true' or 'false'");
            throw new MissingPropertyException(
                    "Invalid value supplied for apollo.use.grid property. It can only be'true' or 'false'");
        }

        boolean useGrid = Boolean.valueOf(PropertyUtils.getProperty("apollo.use.grid").trim());

        String serverIp = null;
        int serverPort = 0;

        /*
         * If apollo.use.grid property is set to false, the driver will try to
         * connect to locally running selenium stand alone server. Else it will
         * try to connect to the Grid.
         */
        if (!useGrid)
        {
            serverIp = "localhost";
            final String LOCAL_SELENIUM_SERVER_PORT = PropertyUtils.getProperty("apollo.local.selenium.server.port");
            if (LOCAL_SELENIUM_SERVER_PORT == null || LOCAL_SELENIUM_SERVER_PORT.trim().length() == 0)
            {
                logger.error("apollo.local.selenium.server.port property has to be specified when apollo.use.grid property is set to 'false'");
                throw new MissingPropertyException(
                        "apollo.local.selenium.server.port property has to be specified when apollo.use.grid property is set to 'false'");
            }

            serverPort = Integer.parseInt(PropertyUtils.getProperty("apollo.local.selenium.server.port"));

        } else
        {
            /*
             * Try to get the GRID_IP parameter from Jenkins environment
             * variable. If it's null then try to take the apollo.use.grid
             * parameter from properties file.
             */
            serverIp = System.getenv("grid_ip");
            if (serverIp == null || serverIp.trim().length() == 0)
            {
                serverIp = PropertyUtils.getProperty("apollo.grid.ip");
                if (serverIp == null || serverIp.trim().length() == 0)
                {
                    logger.error("apollo.grid.ip has to be specified when apollo.use.grid property is set to 'true'");
                    throw new MissingPropertyException(
                            "apollo.grid.ip has to be specified when apollo.use.grid property is set to 'true'");
                }
            }

            /*
             * Try to get the GRID_PORT parameter from Jenkins environment
             * variable. If it's null then try to take the apollo.grid.port
             * parameter from properties file.
             */
            String gridPort = System.getenv("grid_port");
            if (gridPort == null || gridPort.trim().length() == 0)
            {
                gridPort = PropertyUtils.getProperty("apollo.grid.port");
                if (gridPort == null || gridPort.trim().length() == 0)
                {
                    logger.error("apollo.grid.port has to be specified when apollo.use.grid property is set to 'true'");
                    throw new MissingPropertyException(
                            "apollo.grid.port has to be specified when apollo.use.grid property is set to 'true'");
                }
            }

            serverPort = Integer.parseInt(gridPort);
        }

        try
        {
            remoteWebDriverUrl = new URL("http://" + serverIp + ":" + serverPort + "/wd/hub");
        } catch (MalformedURLException ex)
        {
            logger.debug(StackTraceUtils.stackTraceToString(ex));
        }

        return new RemoteWebDriver(remoteWebDriverUrl, capabilities);
    }
}
