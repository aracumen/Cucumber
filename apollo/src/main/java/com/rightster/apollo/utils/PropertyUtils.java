package com.rightster.apollo.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import com.rightster.apollo.exception.MissingPropertyException;

/**
 * Utility class to load property files and fetch property value based on
 * property key.
 * 
 * @author Pradeepta Swain
 */
public class PropertyUtils
{
    private static Logger     logger = Logger.getLogger(PropertyUtils.class.getName());

    private static Properties props  = new Properties();
    private static boolean    isPropertyFileLoaded;

    static
    {
        DOMConfigurator.configureAndWatch(System.getProperty("user.dir") + File.separator + File.separator + "config"
                + File.separator + "log4j.xml");

        String file;
        String workingDir = System.getProperty("user.dir");

        if (!isPropertyFileLoaded)
        {
            try
            {
                loadPropertyFile(workingDir + File.separator + "project.properties");
            } catch (FileNotFoundException ex)
            {
                logger.debug(StackTraceUtils.stackTraceToString(ex));
            } catch (IOException ex)
            {
                logger.debug(StackTraceUtils.stackTraceToString(ex));
            }

            File propertyLocators = new File(workingDir + File.separator + "src/test/resources/locators");

            File[] listOfFiles = propertyLocators.listFiles();

            for (int i = 0; i < listOfFiles.length; i++)
            {
                if (listOfFiles[i].isFile())
                {
                    file = listOfFiles[i].getName();
                    if (file.endsWith(".properties") || file.endsWith(".PROPERTIES"))
                    {
                        logger.info("Loading property file: " + file);
                        try
                        {
                            loadPropertyFile(propertyLocators + File.separator + file);
                        } catch (FileNotFoundException e)
                        {
                            e.printStackTrace();
                        } catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
            }

            isPropertyFileLoaded = true;
        }
    }

    public static void loadPropertyFile(String propertyFileName) throws FileNotFoundException, IOException
    {
        props.load(new FileInputStream(propertyFileName));
    }

    public static String getProperty(String propertyKey) throws FileNotFoundException, IOException,
            MissingPropertyException
    {
        String propertyValue = props.getProperty(propertyKey.trim());
        if (propertyValue == null || propertyValue.trim().length() == 0)
        {
            throw new MissingPropertyException("Property Value is null or is empty for property Key: " + propertyKey);
        }

        return propertyValue;
    }
}
