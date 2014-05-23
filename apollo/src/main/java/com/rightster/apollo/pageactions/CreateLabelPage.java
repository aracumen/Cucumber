package com.rightster.apollo.pageactions;

import org.apache.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.rightster.apollo.base.BasePage;
import com.rightster.apollo.exception.MissingPropertyException;

public class CreateLabelPage extends BasePage
{
    private static Logger logger = Logger.getLogger(CreateLabelPage.class.getName());

    public CreateLabelPage(WebDriver driver)
    {
        super(driver);
    }

    public void typeLabelName(String labelName) throws NoSuchElementException, MissingPropertyException
    {
        type("createlabel.label.name.text.box", labelName);
    }

    public void clickOnCreateButton() throws NoSuchElementException, MissingPropertyException
    {
        click("createlabel.label.create.button");
    }

    public LabelsPage clickOnCancelButton() throws NoSuchElementException, MissingPropertyException
    {
        click("createlabel.label.cancel.button");
        return PageFactory.initElements(driver, LabelsPage.class);
    }
}
