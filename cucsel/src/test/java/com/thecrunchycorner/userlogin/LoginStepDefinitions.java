package com.thecrunchycorner.userlogin;

import static org.junit.Assert.assertTrue;







import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;


import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;



public class LoginStepDefinitions
{
  @Given("^I need to to login$")
  public void I_need_to_to_login() {

    WebDriver driver = null;
    try {
      driver = new RemoteWebDriver(new URL("http://127.0.0.1:19090"), DesiredCapabilities.chrome());
    } catch (MalformedURLException e) {
    }

    driver.get("http://www.google.com");

    // Find the text input element by its name
    WebElement element = driver.findElement(By.name("q"));

    // Enter something to search for
    element.sendKeys("Cheese!");

    // Now submit the form. WebDriver will find the form for us from the element
    element.submit();
    
    // Google's search is rendered dynamically with JavaScript.
    // Wait for the page to load, timeout after 10 seconds
    (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
        public Boolean apply(WebDriver d) {
            return d.getTitle().toLowerCase().startsWith("cheese!");
        }
    });

    assertTrue(driver.getTitle().toLowerCase().startsWith("cheese!"));
    
    driver.quit();
    
  }

  
  
  @When("^I call the login page on my browser$")
  public void I_call_the_login_page_on_my_browser() {
      // Express the Regexp above with the code you wish you had
//      throw new PendingException();
  }

  @Then("^It should display it$")
  public void It_should_display_it() {
      // Express the Regexp above with the code you wish you had
//      throw new PendingException();
  }


  
  
}
