package com.rightster.apollo.runner.ingestanddelivery;


import com.rightster.apollo.base.BaseRunnerClass;

import cucumber.api.CucumberOptions;


@CucumberOptions(features = { "src/test/resources/features/ingestanddelivery/ingest.feature" }, format = { "json:target/cucumber-reports/json/uploadcontentrunner.json" }, glue = { "com.rightster.apollo.stepdef.ingestanddelivery" })
public class IngestAndDeliveryRunner extends BaseRunnerClass
{}