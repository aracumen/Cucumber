package com.rightster.apollo.stepdef.content;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.rightster.apollo.actions.SignInPageActions;
import com.rightster.apollo.base.BaseRunnerClass;
import com.rightster.apollo.exception.MissingPropertyException;
import com.rightster.apollo.factory.TestDataFactory;
import com.rightster.apollo.pageactions.ContentPage;
import com.rightster.apollo.pageactions.TabsPage;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * Step Definitions class to execute upload content features.
 * 
 * @feature_file: src/test/resources/features/content/upload_content.feature
 * @runner_class: 
 *                src/test/java/com/rightster/apollo/runner/content/UploadContentRunner
 *                .class
 * @data_file: src/test/resources/testdata/content.properties
 * @author Pradeepta Swain
 */
public class UploadContentStepDefs
{
    private static Logger       logger                 = Logger.getLogger(UploadContentStepDefs.class.getName());

    private ContentPage         contentPage;
    private SignInPageActions   signInPageActions;

    private WebDriver           driver;

    private static final String UPLOAD_SUCCESS_MESSAGE = "All Changes Saved";
    private static final String VIDEO_TITLE            = "Test10_AVI.avi";

    /*
     * Get the driver instance and load SignInPageActions class. TO-DO: Clean
     * all the content before test run. For this we need separate accounts.
     */
    @Before
    public void setUp() throws Exception
    {
        driver = BaseRunnerClass.getDriverInstance();
        signInPageActions = new SignInPageActions(driver);
    }

    /*
     * Sign out after all test scenarios to make sure next test scenario runs
     * with a clean state.
     */
    @After
    public void logOut() throws NoSuchElementException, MissingPropertyException, FileNotFoundException, IOException
    {
        signInPageActions.signOut();
    }

    @Given("^I am logged in as Rightster account Admin$")
    public void i_am_logged_in_as_Rightster_account_Admin() throws Throwable
    {
        signInPageActions.signIn();
    }

    @Given("^I am on the content tab$")
    public void i_am_on_the_content_tab() throws Throwable
    {
        contentPage = PageFactory.initElements(driver, ContentPage.class);
    }

    @Then("^upload success prompt message is shown$")
    public void upload_success_prompt_message_is_shown() throws Throwable
    {
        contentPage.assertTextPresent(UPLOAD_SUCCESS_MESSAGE);
    }

    @Then("^video is uploaded to library$")
    public void video_is_uploaded_to_library() throws Throwable
    {
        TabsPage tabsPage = PageFactory.initElements(driver, TabsPage.class);
        tabsPage.clickOnTab("content");
        contentPage.assertTextPresent(VIDEO_TITLE);
    }

    @When("^I click on create new video$")
    public void i_click_on_create_new_video() throws Throwable
    {
        contentPage.clickOnCreateButton();
        contentPage.clickOnCreateVideoLink();
    }

    @When("^upload a video of valid format$")
    public void upload_a_video_of_valid_format() throws Throwable
    {
        contentPage.uploadVideo(TestDataFactory.getTestData("AVI"));
    }

    @When("^I upload a ‘(.+)’ file$")
    public void i_upload_a_file_(String videoFile) throws Throwable
    {
        String videoFilePath = TestDataFactory.getTestData(videoFile);
        TestDataFactory.setTestData(videoFile, videoFilePath);

        logger.info("Uploading file: " + videoFilePath);
        contentPage.uploadVideo(videoFilePath);
    }

    @Then("^file is uploaded ‘(.+)’$")
    public void file_is_uploaded_successfully(String outcome) throws Throwable
    {
        TabsPage tabsPage = PageFactory.initElements(driver, TabsPage.class);
        contentPage = (ContentPage) tabsPage.clickOnTab("content");

        /*
         * Currently the videoPath is set as first element in data hashmap. Need
         * to find an elegant way of fetching the value.
         */
        File videoFileFullPath = new File((String) TestDataFactory.getTestDataMap().values().toArray()[0]);

        // Get the file name from absolute file path
        String videoFileName = videoFileFullPath.getName();

        logger.info("Verifying if video file is uploaded: " + videoFileName);

        if (outcome.equalsIgnoreCase("successfully"))
        {
            contentPage.assertTextPresent(videoFileName);
        } else
        {
            contentPage.assertTextNotPresent(videoFileName);
        }
    }
}
