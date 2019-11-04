package com.thecrunchycorner.userlogin;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
 
@RunWith(Cucumber.class)
@CucumberOptions(format={"pretty", "html:target/cucumber-html-reports", "json:target/cucumber.json", "junit:target/junit-report"})
public class RunTest
{

}
