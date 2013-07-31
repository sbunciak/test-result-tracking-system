package org.jboss.community.trts.test.bdd;

import java.net.URL;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.test.api.ArquillianResource;

import com.thoughtworks.selenium.DefaultSelenium;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;


public class Steps {
	@Drone
	private DefaultSelenium browser;
	
	@ArquillianResource
	private URL deploymentUrl;
	
	@Given("^I am on products page$")
	@RunAsClient
    public void setPage() {
		browser.setSpeed("2000"); // 2 seconds between each operation
		//browser.open(deploymentUrl+"#"+"products");
		browser.open("http://tester:jboss@localhost:8080/test/#products");
        browser.waitForPageToLoad("5000");
    }
	
	@When("^I create Switchyard product$")
	@RunAsClient
    public void createProduct() {
		browser.setSpeed("2000"); // 2 seconds between each operation
		browser.click("css=a[href='#/products/new']");
		browser.type("id=name", "Switchyard");
		browser.click("css=input[value='Save']");
    }
  
    @Then("^I should have Switchyard in products$")
    @RunAsClient
    public void shouldHaveBeenCreated() {
    	browser.setSpeed("2000"); // 2 seconds between each operation
    	browser.click("css=a[href='#/products']");
    	browser.isTextPresent("Switchyard");
    }
}
