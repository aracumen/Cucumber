package com.rightster.apollo.pageactions;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.rightster.apollo.base.BasePage;
import com.rightster.apollo.exception.MissingPropertyException;
import com.rightster.apollo.utils.PropertyUtils;
import com.rightster.apollo.utils.StackTraceUtils;

/**
 * This page contains all the actions specific to Content page.
 * 
 * @author Pradeepta Swain
 */
public class ContentPage extends BasePage
{
    private static Logger logger = Logger.getLogger(ContentPage.class.getName());

    public ContentPage(WebDriver driver)
    {
        super(driver);
    }

    public void clickOnCreateButton() throws NoSuchElementException, MissingPropertyException
    {
        click("content.create.button");
    }

    public void clickOnCreateVideoLink() throws NoSuchElementException, MissingPropertyException
    {
        click("content.create.video.link");
    }

    public void uploadVideo(String videoPath) throws NoSuchElementException, MissingPropertyException
    {
        driver.findElement(By.id("fileupload")).sendKeys(videoPath);

        Long timeout = null;

        try
        {
            timeout = Long.valueOf(PropertyUtils.getProperty("apollo.video.upload.timeout")).longValue();
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

        WebDriverWait wait = new WebDriverWait(driver, timeout);

        try
        {
            wait.until(new ExpectedCondition<Boolean>()
            {
                public Boolean apply(WebDriver webDriver)
                {
                    try
                    {
                        return (driver.getPageSource().contains("File type not allowed") || driver.getPageSource()
                                .contains("All Changes Saved"));
                    } catch (StaleElementReferenceException e)
                    {
                        return false;
                    }
                }
            });
        } catch (TimeoutException ex)
        {
            logger.error("Timed out after " + timeout + " seconds waiting for file '" + videoPath + "' to be uploaded");
        }
    }

    public void clickOnDoneButton() throws NoSuchElementException, MissingPropertyException
    {
        click("content.video.upload.done.button");
    }

    public void checkOnSelectAllVideo() throws NoSuchElementException, MissingPropertyException
    {
        click("content.library.select.all.checkbox");
    }

    public void clickOnBulkToolDelete() throws NoSuchElementException, MissingPropertyException
    {
        click("content.library.select.all.mark.as.deleted");
    }

    public void clickOnOkDeleteConfirmation() throws NoSuchElementException, MissingPropertyException
    {
        click("content.library.select.all.mark.as.deleted.ok");
    }

    public void clickOnListViewIcon() throws NoSuchElementException, MissingPropertyException
    {
        clickOnListViewIcon(false);
    }

    public void clickOnListViewIcon(boolean ignoreNoSuchElementException) throws NoSuchElementException,
            MissingPropertyException
    {
        click("content.library.view.as.list.icon.inactive", ignoreNoSuchElementException);
    }
}
