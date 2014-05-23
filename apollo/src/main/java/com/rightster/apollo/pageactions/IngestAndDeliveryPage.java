package com.rightster.apollo.pageactions;

import org.apache.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.rightster.apollo.exception.MissingPropertyException;
import com.rightster.apollo.base.BasePage;

/**
 * This page contains all the actions specific to Ingest & Delivery page.
 * 
 * @author Pradeepta Swain
 */
public class IngestAndDeliveryPage extends BasePage
{
    private static Logger logger = Logger.getLogger(IngestAndDeliveryPage.class.getName());

    public IngestAndDeliveryPage(WebDriver driver)
    {
        super(driver);
    }

    public void typeProfileName(String profileName) throws NoSuchElementException, MissingPropertyException
    {
        type("signin.email.address", profileName);
    }

    public void clickOnNewProfile() throws NoSuchElementException, MissingPropertyException
    {
        click("ingestanddelivery.new.profile");
    }

    public NewMRSSIngest clickOnNewIngestLink() throws NoSuchElementException, MissingPropertyException
    {
        click("ingestanddelivery.new.ingest.profile");
        return PageFactory.initElements(driver, NewMRSSIngest.class);
    }

    public void clickOnRemoveButtonOnFirstIngestAndDeliveryProfile() throws NoSuchElementException, MissingPropertyException
    {
        // TODO Auto-generated method stub
        click("ingestanddelivery.list.ingest.delete.button");
        
    }

    public void clickOnOkForIngestDeletionConfirmation() throws NoSuchElementException, MissingPropertyException
    {
        // TODO Auto-generated method stub
        click("ingestanddelivery.list.ingest.delete.button.ok");      
    }
}
