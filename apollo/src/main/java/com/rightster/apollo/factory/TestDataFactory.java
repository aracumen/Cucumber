package com.rightster.apollo.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import org.apache.log4j.Logger;

import com.rightster.apollo.utils.StackTraceUtils;

/**
 * Factory class to store and provide test data to step definition steps.
 * 
 * @author Pradeepta Swain
 */
public class TestDataFactory
{
    private static Logger              logger = Logger.getLogger(TestDataFactory.class.getName());
    private static Properties          props  = new Properties();

    private static Map<String, String> testDataMap;

    static
    {
        testDataMap = new LinkedHashMap<String, String>();

        String file;
        String workingDir = System.getProperty("user.dir");

        File testDataDirectory = new File(workingDir + File.separator + "src/test/resources/testdata");

        File[] listOfFiles = testDataDirectory.listFiles();

        for (int i = 0; i < listOfFiles.length; i++)
        {
            if (listOfFiles[i].isFile())
            {
                file = listOfFiles[i].getName();
                if (file.endsWith(".properties") || file.endsWith(".PROPERTIES"))
                {
                    logger.info("Loading testdata file: " + file);
                    try
                    {
                        loadPropertyFile(testDataDirectory + File.separator + file);
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
    }

    public static void loadPropertyFile(String propertyFileName) throws FileNotFoundException, IOException
    {
        props.load(new FileInputStream(propertyFileName));
    }

    public static String getTestData(String testDataKey) throws InvalidParameterException
    {
        String testDataValue = "";
        if (testDataKey == null || testDataKey.trim().length() == 0)
        {
            throw new InvalidParameterException("Test Data is null!");
        }

        try
        {
            testDataValue = props.getProperty(testDataKey);
            if (testDataValue == null)
            {
                testDataValue = testDataMap.get(testDataKey);
            }
        } catch (Exception ex)
        {
            logger.debug(StackTraceUtils.stackTraceToString(ex));
        }

        return testDataValue;
    }

    public static void setTestData(String testDataKey, String testDataValue) throws InvalidParameterException
    {
        if (testDataKey == null || testDataKey.trim().length() == 0)
        {
            throw new InvalidParameterException("Test data key can not be null!");
        }

        testDataMap.put(testDataKey, testDataValue);
    }

    public static Map<String, String> getTestDataMap()
    {
        return testDataMap;
    }

    public static void initializeFactory(String key)
    {
        initializeFactory(key, 0);
    }

    public static void initializeFactory(String key, int length)
    {
        if (length == 0)
        {
            length = 16;
        }

        if (key.equalsIgnoreCase("empty"))
        {
            setTestData(key, "");
        } else
        {
            setTestData(key, new RandomString(key, length).nextString());
        }
    }
}

class RandomString
{
    private static char[] symbols;

    private final Random  random = new Random();

    private final char[]  buf;

    public RandomString(String identifier, int length)
    {
        StringBuilder tmp = new StringBuilder();

        if (identifier.equalsIgnoreCase("valid"))
        {
            for (char ch = '0'; ch <= '9'; ++ch)
                tmp.append(ch);
            for (char ch = 'a'; ch <= 'z'; ++ch)
                tmp.append(ch);
            symbols = tmp.toString().toCharArray();
        } else if (identifier.equalsIgnoreCase("numeric"))
        {
            for (char ch = '0'; ch <= '9'; ++ch)
                tmp.append(ch);
            symbols = tmp.toString().toCharArray();
        } else if (identifier.equalsIgnoreCase("long"))
        {
            for (char ch = 'a'; ch <= 'z'; ++ch)
                tmp.append(ch);
            symbols = tmp.toString().toCharArray();
        } else if (identifier.equalsIgnoreCase("special_characters"))
        {
            String splChar = "~!@#$%^&*()-_<>,{}[]`:;";
            char[] splCharArr = splChar.toCharArray();

            for (char ch : splCharArr)
                tmp.append(ch);
            symbols = tmp.toString().toCharArray();
        }

        if (length < 1)
            throw new IllegalArgumentException("length < 1: " + length);
        buf = new char[length];
    }

    public String nextString()
    {
        for (int idx = 0; idx < buf.length; ++idx)
            buf[idx] = symbols[random.nextInt(symbols.length)];
        return new String(buf);
    }
}