package com.rightster.apollo.pageactions;

import org.apache.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.rightster.apollo.base.BasePage;
import com.rightster.apollo.exception.MissingPropertyException;

/**
 * This page contains all the actions specific to Labels page.
 * 
 * @author Pradeepta Swain
 */
public class LabelsPage extends BasePage
{
    private static Logger logger = Logger.getLogger(LabelsPage.class.getName());

    public LabelsPage(WebDriver driver)
    {
        super(driver);
    }

    public CreateLabelPage clickOnCreateLabelButton() throws NoSuchElementException, MissingPropertyException
    {
        click("labels.create.label.button");
        return PageFactory.initElements(driver, CreateLabelPage.class);
    }

    public void mouseOverOnLabel(String labelName) throws NoSuchElementException, MissingPropertyException
    {
        mouseOver("labels.content.label.text", labelName);
    }

    public void clickOnLabelDeleteMouseOverIcon(String labelName) throws NoSuchElementException,
            MissingPropertyException
    {
        click("labels.content.footer.mouse.over.delete.icon", labelName);
    }
}
