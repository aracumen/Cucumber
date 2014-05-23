package com.rightster.apollo.stepdef.labels;

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
import com.rightster.apollo.pageactions.CreateLabelPage;
import com.rightster.apollo.pageactions.LabelsPage;
import com.rightster.apollo.pageactions.TabsPage;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * Step Definitions class to execute labels features.
 * 
 * @feature_file: src/test/resources/features/labels/labels.feature
 * @runner_class: src/test/java/com/rightster/apollo/runner/lables/LabelsRunner
 *                .class
 * @data_file: src/test/resources/testdata/labels.properties
 * @author Pradeepta Swain
 */
public class LabelsStepDefs
{
    private static Logger       logger                 = Logger.getLogger(LabelsStepDefs.class.getName());

    private SignInPageActions   signInPageActions;
    private TabsPage            tabsPage;
    private LabelsPage          labelsPage;
    private CreateLabelPage     createLabelsPage;

    private WebDriver           driver;

    private static final int    LONG_STRING_LENGTH     = 100;
    private static final int    NORMAL_STRING_LENGTH   = 16;

    private static final String ASSERT_SUCCESS_MESSAGE = "Your label has been successfully created";

    /*
     * Get the driver instance and load SignInPageActions class. TO-DO: Clean
     * all the labels before test run. Currently deleting labels is not working.
     */
    @Before
    public void setUp() throws Exception
    {
        driver = BaseRunnerClass.getDriverInstance();
        signInPageActions = new SignInPageActions(driver);
        signInPageActions.signIn();
    }

    /*
     * If label is not created, then create label pop up will remain on the
     * page. Click on cancel button on create label page to make sure, next
     * scenario runs fine with out any pop up being displayed at the beginning.
     * Signout once the scenario is complete.
     */
    @After
    public void logOut() throws NoSuchElementException, MissingPropertyException, FileNotFoundException, IOException
    {
        try
        {
            createLabelsPage.clickOnCancelButton();
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }

        signInPageActions.signOut();
    }

    @Given("^I am on the Labels page$")
    public void i_am_on_the_Labels_page() throws Throwable
    {
        tabsPage = PageFactory.initElements(driver, TabsPage.class);
        labelsPage = (LabelsPage) tabsPage.clickOnTab("labels");
    }

    @When("^I click on the New Label button$")
    public void i_click_on_the_New_Label_button() throws Throwable
    {
        createLabelsPage = labelsPage.clickOnCreateLabelButton();
    }

    /*
     * If label type is long, then initialize the data factory with long string
     * length. For all label types, initilaize the factory with normal length
     * string.
     */
    @When("^enter a name for label of '(.+)'$")
    public void enter_a_name_for_label_of_(String labelName) throws Throwable
    {
        logger.info("Initializing Data Factory with label name: " + labelName);

        if (labelName.equalsIgnoreCase("long"))
        {
            TestDataFactory.initializeFactory(labelName, LONG_STRING_LENGTH);
        } else
        {
            TestDataFactory.initializeFactory(labelName, NORMAL_STRING_LENGTH);
        }

        logger.info("Getting test data from factory: " + TestDataFactory.getTestData(labelName));
        createLabelsPage.typeLabelName(TestDataFactory.getTestData(labelName));
    }

    @When("^I click on create button$")
    public void i_click_on_create_button() throws Throwable
    {
        createLabelsPage.clickOnCreateButton();
    }

    @Then("^label gets created '(.+)'$")
    public void label_gets_created_(String outcome) throws Throwable
    {
        logger.info("Getting Outcome: " + outcome);
        if (outcome.equalsIgnoreCase("successfully"))
        {
            createLabelsPage.assertTextPresent(ASSERT_SUCCESS_MESSAGE);
        } else
        {
            createLabelsPage.assertTextNotPresent(ASSERT_SUCCESS_MESSAGE);
        }
    }

    @When("^I click on cancel button$")
    public void i_click_on_cancel_button() throws Throwable
    {
        createLabelsPage.clickOnCancelButton();
    }
}
