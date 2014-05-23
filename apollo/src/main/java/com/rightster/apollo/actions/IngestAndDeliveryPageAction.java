package com.rightster.apollo.actions;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.rightster.apollo.base.BaseExpectedConditions;
import com.rightster.apollo.base.BasePage;
import com.rightster.apollo.exception.MissingPropertyException;
import com.rightster.apollo.factory.TestDataFactory;
import com.rightster.apollo.pageactions.IngestAndDeliveryPage;
import com.rightster.apollo.utils.PropertyUtils;
import com.rightster.apollo.utils.StackTraceUtils;

public class IngestAndDeliveryPageAction extends BasePage
{
    private static Logger         logger = Logger.getLogger(IngestAndDeliveryPage.class.getName());
    private static Long           timeout;

    private IngestAndDeliveryPage ingestAndDeliveryPage;

    public IngestAndDeliveryPageAction(WebDriver driver)
    {
        super(driver);

        ingestAndDeliveryPage = PageFactory.initElements(driver, IngestAndDeliveryPage.class);

        try
        {
            timeout = Long.valueOf(PropertyUtils.getProperty("apollo.element.fetch.timeout"));
        } catch (NumberFormatException ex)
        {
            logger.debug(StackTraceUtils.stackTraceToString(ex));
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

    public void deleteAllProfiles() throws NoSuchElementException, MissingPropertyException
    {
        while (ingestAndDeliveryPage.isElementVisible("ingestanddelivery.list.ingest.delete.button"))
        {
            ingestAndDeliveryPage.clickOnRemoveButtonOnFirstIngestAndDeliveryProfile();
            ingestAndDeliveryPage.clickOnOkForIngestDeletionConfirmation();
            new WebDriverWait(driver, timeout).until(BaseExpectedConditions.pageContainsText(TestDataFactory
                    .getTestData("apollo.library.ingestanddelivery.ingest.deleted.message")));
        }
    }
}
