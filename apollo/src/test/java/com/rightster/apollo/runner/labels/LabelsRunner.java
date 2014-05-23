package com.rightster.apollo.runner.labels;

import com.rightster.apollo.base.BaseRunnerClass;

import cucumber.api.CucumberOptions;

/**
 * Runner class to run labels.feature file.
 * 
 * @author Pradeepta Swain
 */
@CucumberOptions(features = { "src/test/resources/features/labels/labels.feature" }, format = { "json:target/cucumber-reports/json/labelsrunner.json" }, glue = { "com.rightster.apollo.stepdef.labels" })
public class LabelsRunner extends BaseRunnerClass
{

}
