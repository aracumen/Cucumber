package com.rightster.apollo.runner.content;

import com.rightster.apollo.base.BaseRunnerClass;

import cucumber.api.CucumberOptions;

/**
 * Runner class to run upload_content.feature file.
 * 
 * @author Pradeepta Swain
 */
@CucumberOptions(features = { "src/test/resources/features/content/upload_content.feature" }, format = { "json:target/cucumber-reports/json/uploadcontentrunner.json" }, glue = { "com.rightster.apollo.stepdef.content" })
public class UploadContentRunner extends BaseRunnerClass
{}
