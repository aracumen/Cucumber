package com.rightster.apollo.stepdef.ingestanddelivery;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

import com.rightster.apollo.actions.ContentPageActions;
import com.rightster.apollo.actions.IngestAndDeliveryPageAction;
import com.rightster.apollo.actions.SignInPageActions;
import com.rightster.apollo.base.BaseRunnerClass;
import com.rightster.apollo.exception.MissingPropertyException;
import com.rightster.apollo.factory.TestDataFactory;
import com.rightster.apollo.pageactions.ContentPage;
import com.rightster.apollo.pageactions.IngestAndDeliveryPage;
import com.rightster.apollo.pageactions.NewMRSSIngest;
import com.rightster.apollo.pageactions.TabsPage;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * Step Definitions class to execute ingest features.
 * 
 * @feature_file: src/test/resources/features/ingestanddelivery/ingest.feature
 * @runner_class: src/test/java/com/rightster/apollo/runner/ingestanddelivery/
 *                IngestAndDeliveryRunner .class
 * @data_file: src/test/resources/testdata/ingestanddelivery.properties
 * @author Bikash Choudhary
 */
public class IngestStepDefs
{
    private static Logger               logger             = Logger.getLogger(IngestStepDefs.class.getName());

    private IngestAndDeliveryPage       ingestAndDeliveryPage;
    private ContentPageActions          contentPageActions;
    private IngestAndDeliveryPageAction ingestAndDeliveryPageAction;
    private ContentPage                 contentPage;
    private NewMRSSIngest               newMRSSIngest;
    private TabsPage                    tabsPage;
    private SignInPageActions           signInPageActions;

    private WebDriver                   driver;

    private static final int            LONG_STRING_LENGTH = 50;

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

    @Given("^I am logged in as Rightster admin$")
    public void i_am_logged_in_as_Rightster_admin() throws Throwable
    {
        SignInPageActions signInPageActions = new SignInPageActions(driver);

        signInPageActions.signIn();
    }

    @Given("^I am on the ingest and delivery tab$")
    public void i_am_on_the_ingest_and_delivery_tab() throws Throwable
    {
        tabsPage = new TabsPage(driver);
        contentPageActions = new ContentPageActions(driver);
        contentPageActions.deleteAllVideos();
        tabsPage.clickOnTab("ingestanddelivery");
    }

    @Given("^I am creating a new ingest profile$")
    public void i_am_creating_a_new_ingest_profile() throws Throwable
    {
        ingestAndDeliveryPage = new IngestAndDeliveryPage(driver);
        ingestAndDeliveryPageAction = new IngestAndDeliveryPageAction(driver);
        ingestAndDeliveryPageAction.deleteAllProfiles();
        ingestAndDeliveryPage.clickOnNewProfile();
    }

    @When("^I click on New ingest button$")
    public void i_click_on_New_ingest_button() throws Throwable
    {
        newMRSSIngest = ingestAndDeliveryPage.clickOnNewIngestLink();
    }

    @When("^I enter a valid profile name$")
    public void i_enter_a_valid_profile_name() throws Throwable
    {
        TestDataFactory.initializeFactory("valid");
        String validProfileName = TestDataFactory.getTestData("valid");

        newMRSSIngest.typeName(validProfileName);
    }

    @When("^I select the status to be Active$")
    public void i_select_the_status_to_be_Active() throws Throwable
    {
        newMRSSIngest.checkActiveOn();
    }

    @When("^I select default status as Active$")
    public void i_select_default_status_as_Active() throws Throwable
    {
        newMRSSIngest.selectDefaultContentStatusOption("Active");
    }

    @When("^I select the status to be active$")
    public void i_select_the_status_to_be_active() throws Throwable
    {
        newMRSSIngest.checkActiveOn();
    }

    @When("^I enter a valid unauthenticated feed url$")
    public void i_enter_a_valid_unauthenticated_feed_url() throws Throwable
    {
        newMRSSIngest.typeFeedUrl(TestDataFactory.getTestData("apollo.library.ingestanddelivery.URL_type.valid"));
    }

    @When("^I select Yahoo MRSS (.+) Translation translation$")
    public void i_select_Yahoo_MRSS_Translation_translation(String mrssTranslation) throws Throwable
    {
        newMRSSIngest.selectMetadataMappingOption(mrssTranslation);
    }

    @When("^I select Yahoo MRSS (.+) translation$")
    public void i_select_Yahoo_MRSS_translation(String mrssTranslation) throws Throwable
    {
        newMRSSIngest.selectMetadataMappingOption(mrssTranslation);
    }

    @When("^I select (\\d+) historical items for ingest$")
    public void i_select_historical_items_for_ingest(int historicalItemsForIngest) throws Throwable
    {
        newMRSSIngest.selectHistoricalItemsOption(historicalItemsForIngest);
    }

    @When("^I select default status as active$")
    public void i_select_default_status_as_active() throws Throwable
    {
        newMRSSIngest.selectDefaultContentStatusOption("Active");
    }

    @When("^I save the form$")
    public void i_save_the_form() throws Throwable
    {
        newMRSSIngest.clickOnCreateButton();
    }

    @Then("^new igest profile should be created$")
    public void new_igest_profile_should_be_created() throws Throwable
    {
        newMRSSIngest
                .assertTextPresent(TestDataFactory.getTestData("apollo.library.ingestanddelivery.success.message"));
    }

    @Then("^(\\d+) historical items should get ingested to the library$")
    public void historical_items_should_get_ingested_to_the_library(int historicalItemsInIngest) throws Throwable
    {
        tabsPage.clickOnTab("Content");
        newMRSSIngest.assertElementContainsText("content.library.content.count.text",
                Integer.toHexString(historicalItemsInIngest));
    }

    @When("^I enter \"(.*?)\" profile name$")
    public void i_enter_profile_name(String name_type) throws Throwable
    {
        if (name_type.equalsIgnoreCase("long"))
        {
            TestDataFactory.initializeFactory(name_type, LONG_STRING_LENGTH);
        } else
        {
            TestDataFactory.initializeFactory(name_type);
        }

        String profileName = TestDataFactory.getTestData(name_type);
        TestDataFactory.setTestData("name_type", profileName);

        newMRSSIngest.typeName(profileName);
    }

    @When("^I select  \"(.*?)\" profile status$")
    public void i_select_profile_status(String state) throws Throwable
    {
        TestDataFactory.setTestData("state", state);

        if (state.contains("Active"))
        {
            newMRSSIngest.checkActiveOn();
        } else if (state.contains("Inactive"))
        {
            newMRSSIngest.checkInactiveOn();
        }
    }

    @When("^I enter \"(.*?)\" feed URL$")
    public void i_enter_feed_URL(String URL_type) throws Throwable
    {
        newMRSSIngest.typeFeedUrl(TestDataFactory.getTestData("apollo.library.ingestanddelivery.URL_type."
                + URL_type.replace(" ", "_")));
    }

    @When("^I select \"(.*?)\" translation$")
    public void i_select_translation(String translation_type) throws Throwable
    {
        newMRSSIngest.selectMetadataMappingOption(translation_type);
    }

    @When("^I select \"(.*?)\" historical items$")
    public void i_select_historical_items(int item_number) throws Throwable
    {
        newMRSSIngest.selectHistoricalItemsOption(item_number);
    }

    @When("^I select \"(.*?)\"  status for ingested videos$")
    public void i_select_status_for_ingested_videos(String default_video_status) throws Throwable
    {
        newMRSSIngest.selectDefaultContentStatusOption(default_video_status);
    }

    @Then("^new ingest profile should get created \"(.*?)\"$")
    public void new_ingest_profile_should_get_created(String outcome) throws Throwable
    {
        TestDataFactory.setTestData("outcome", outcome);

        if (outcome.equalsIgnoreCase("success"))
        {
            newMRSSIngest.assertElementContainsText("ingestanddelivery.new.profile.success",
                    TestDataFactory.getTestData("apollo.library.ingestanddelivery.success.message"));
            newMRSSIngest.assertElementContainsText("ingestanddelivery.list.ingest.by.name",
                    TestDataFactory.getTestData("name_type"), TestDataFactory.getTestData("name_type"), "");
            newMRSSIngest.assertElementContainsText("ingestanddelivery.list.ingest.by.status",
                    TestDataFactory.getTestData("name_type"), TestDataFactory.getTestData("state"), "");
        } else
        {
            newMRSSIngest.assertElementContainsText("ingestanddelivery.new.profile.failure",
                    TestDataFactory.getTestData("apollo.library.ingestanddelivery.failure.message"));
        }
    }

    @Then("^\"(.*?)\" videos should be ingested in the library$")
    public void videos_should_be_ingested_in_the_library(String number_of_items_ingested) throws Throwable
    {
        if (TestDataFactory.getTestData("outcome").equalsIgnoreCase("success"))
        {
            tabsPage = new TabsPage(driver);
            Thread.sleep(30000);
            tabsPage.clickOnTab("Content");
            contentPage = new ContentPage(driver);

            contentPage.clickOnListViewIcon(true);
            // contentPageActions.assertElementContainsText("content.library.content.count.text",
            // number_of_items_ingested);
        }
    }

    @Then("^ingested items should have status \"(.*?)\"$")
    public void ingested_items_should_have_status(String vid_status) throws Throwable
    {
        if (TestDataFactory.getTestData("outcome").equalsIgnoreCase("success"))
        {
            contentPage.assertElementContainsText("content.library.view.title.text",
                    TestDataFactory.getTestData("apollo.library.ingestanddelivery.URL_type.long.firstVideoName"));
            // This verification is failing due ENG-872
            contentPage.assertElementContainsText("content.library.view.title.state", vid_status);
        }
    }
}