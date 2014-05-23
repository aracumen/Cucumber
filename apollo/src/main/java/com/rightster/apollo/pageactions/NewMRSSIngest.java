package com.rightster.apollo.pageactions;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.rightster.apollo.base.BasePage;
import com.rightster.apollo.exception.MissingPropertyException;

/**
 * This page contains all the actions specific to New MRSS Ingest page.
 * 
 * @author Pradeepta Swain
 */
public class NewMRSSIngest extends BasePage
{
    private static Logger logger = Logger.getLogger(IngestAndDeliveryPage.class.getName());

    public NewMRSSIngest(WebDriver driver)
    {
        super(driver);
    }

    public void typeName(String name) throws NoSuchElementException, MissingPropertyException
    {
        type("ingestanddelivery.new.profile.form.name", name);
    }

    public void checkActiveOn() throws NoSuchElementException, MissingPropertyException
    {
        click("ingestanddelivery.new.profile.form.status.active");
    }

    public void checkInactiveOn() throws NoSuchElementException, MissingPropertyException
    {
        click("ingestanddelivery.new.profile.form.status.inactive");
    }

    public void typeFeedUrl(String feedUrl) throws NoSuchElementException, MissingPropertyException
    {
        type("ingestanddelivery.new.profile.form.feedUrl", feedUrl);
    }

    public void typeUsername(String username) throws NoSuchElementException, MissingPropertyException
    {
        type("ingestanddelivery.new.profile.form.username", username);
    }

    public void typePassword(String password) throws NoSuchElementException, MissingPropertyException
    {
        type("ingestanddelivery.new.profile.form.password", password);
    }

    public void selectMetadataMappingOption(String option) throws NoSuchElementException, MissingPropertyException
    {
        click("ingestanddelivery.new.profile.form.metadata.mapping.option");
        type("ingestanddelivery.new.profile.form.metadata.mapping.option.element", option);
        click("ingestanddelivery.new.profile.form.metadata.mapping.option.Firstelement");
    }

    public void selectHistoricalItemsOption(int option) throws NoSuchElementException, MissingPropertyException,
            FileNotFoundException, IOException
    {
        click("ingestanddelivery.new.profile.form.ingest.historical.items.option");
        click("ingestanddelivery.new.profile.form.ingest.historical.items.option.element",Integer.toString(option));
    }

    public void selectDefaultContentStatusOption(String option) throws NoSuchElementException, MissingPropertyException
    {
        click("ingestanddelivery.new.profile.form.default.content.status.option");
        if (option.equals("Draft"))
            click("ingestanddelivery.new.profile.form.default.content.status.option.element.draft");
        else if (option.equals("Active"))
            click("ingestanddelivery.new.profile.form.default.content.status.option.element.active");
    }

    public IngestAndDeliveryPage clickOnCreateButton() throws NoSuchElementException, MissingPropertyException
    {
        click("ingestanddelivery.new.profile.form.create");
        return PageFactory.initElements(driver, IngestAndDeliveryPage.class);
    }

    public IngestAndDeliveryPage clickOnCreateCancel() throws NoSuchElementException, MissingPropertyException
    {
        click("ingestanddelivery.new.profile.form.cancel");
        return PageFactory.initElements(driver, IngestAndDeliveryPage.class);
    }
}