package com.rightster.apollo.factory;

import org.apache.log4j.Logger;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.rightster.apollo.exception.CapabilityNotPresentException;

/**
 * Class to create appropriate capability based on the capability passed through
 * test methods. If specified capability is not found then it throws
 * CapabilityNotPresentException.
 * 
 * @author Pradeepta Swain
 */
public class CapabilitiesFactory
{
    private static Logger              logger = Logger.getLogger(CapabilitiesFactory.class.getName());

    private static DesiredCapabilities capability;

    public static DesiredCapabilities createCapability(String browserName, String browserVersion, String platformName)
            throws CapabilityNotPresentException
    {
        if (browserName.trim().equalsIgnoreCase("firefox"))
        {
            capability = DesiredCapabilities.firefox();
        } else if (browserName.trim().equalsIgnoreCase("internet explorer"))
        {
            capability = DesiredCapabilities.internetExplorer();
        } else if (browserName.trim().equalsIgnoreCase("chrome"))
        {
            capability = DesiredCapabilities.chrome();
        } else if (browserName.trim().equalsIgnoreCase("safari"))
        {
            capability = DesiredCapabilities.safari();
        } else
        {
            logger.error("Matching capability is not found for browser: " + browserName);
            throw new CapabilityNotPresentException("Matching capability is not found for browser: " + browserName);
        }

        if (platformName.trim().equalsIgnoreCase("xp"))
        {
            capability.setPlatform(Platform.XP);
        } else if (platformName.trim().equalsIgnoreCase("windows7"))
        {
            capability.setPlatform(Platform.VISTA);
        } else if (platformName.trim().equalsIgnoreCase("mac"))
        {
            capability.setPlatform(Platform.MAC);
        } else if (browserName.trim().equalsIgnoreCase("windows8"))
        {
            capability.setPlatform(Platform.WIN8);
        } else
        {
            logger.error("Matching capability is not found for platform: " + platformName);
            throw new CapabilityNotPresentException("Matching capability is not found for platform: " + platformName);
        }

        capability.setVersion(browserVersion);

        return capability;
    }

}
