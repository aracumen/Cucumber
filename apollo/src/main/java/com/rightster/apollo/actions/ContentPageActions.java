package com.rightster.apollo.actions;

import org.apache.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.rightster.apollo.base.BasePage;
import com.rightster.apollo.exception.MissingPropertyException;
import com.rightster.apollo.pageactions.ContentPage;

public class ContentPageActions extends BasePage
{
    private static Logger logger = Logger.getLogger(SignInPageActions.class.getName());

    private ContentPage   contentPage;

    public ContentPageActions(WebDriver driver)
    {
        super(driver);

        contentPage = PageFactory.initElements(driver, ContentPage.class);
    }

    public void uploadSingleVideo(String absoluteVideoPath) throws NoSuchElementException, MissingPropertyException
    {
        contentPage.clickOnCreateButton();
        contentPage.clickOnCreateVideoLink();
        contentPage.uploadVideo(absoluteVideoPath);
    }

    public void deleteAllVideos() throws NoSuchElementException, MissingPropertyException
    {
        if (contentPage.isElementPresent("content.library.select.all.checkbox")
                && contentPage.isElementVisible("content.library.select.all.checkbox"))
        {
            contentPage.checkOnSelectAllVideo();
            contentPage.clickOnBulkToolDelete();
            contentPage.clickOnOkDeleteConfirmation();
        }
    }
}
