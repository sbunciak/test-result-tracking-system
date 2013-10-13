package org.jboss.community.trts.test.bdd;

import static org.junit.Assert.assertTrue;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.community.trts.model.Product;
import org.jboss.community.trts.rest.ProductREST;
import org.jboss.community.trts.service.ProductService;
import org.jboss.community.trts.util.Resources;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.Filters;
import org.jboss.shrinkwrap.api.GenericArchive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.importer.ExplodedImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.runtime.arquillian.ArquillianCucumber;
import cucumber.runtime.arquillian.api.Features;

@RunWith(ArquillianCucumber.class)
@Features("Features/acceptance.feature")
public class AcceptanceTest {

	@Drone
	private FirefoxDriver driver;

	@ArquillianResource
	private URL deploymentUrl;
	private String applicationUrl;

	@Deployment(testable = false)
	public static Archive<?> create() {
		WebArchive archive = ShrinkWrap.create(WebArchive.class, "test.war");

		archive.addPackage(Product.class.getPackage())
				.addPackage(ProductService.class.getPackage())
				.addPackage(ProductREST.class.getPackage())
				.addClass(Resources.class)
				.addAsResource("META-INF/test-persistence.xml",
						"META-INF/persistence.xml")
				.addAsWebInfResource("test-ds.xml", "test-ds.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
				.addAsResource("Features/acceptance.feature");
		archive.merge(
				ShrinkWrap.create(GenericArchive.class)
						.as(ExplodedImporter.class)
						.importDirectory("src/main/webapp")
						.as(GenericArchive.class), "/",
				Filters.exclude(".*\\.xml$"))
				.addAsWebInfResource("test-jboss-web.xml", "jboss-web.xml")
				.addAsWebInfResource("test-roles.properties",
						"/classes/roles.properties")
				.addAsWebInfResource("test-users.properties",
						"/classes/users.properties")
				.addAsWebInfResource("test-web.xml", "web.xml");
		return archive;
	}

	@Before
	public void setUp() {
		// set default timeout
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		// "auth" to the application
		applicationUrl = deploymentUrl.toString().replace("http://",
				"http://admin:admin@");

		// navigate to index
		driver.get(applicationUrl);
	}

	@Given("^I am on (.+) page$")
	public void setPage(String page) {
		driver.findElement(By.partialLinkText(page)).click();
	}

	@When("^I add (.+) product$")
	public void addProduct(String name) {
		driver.findElement(By.partialLinkText("Create new product")).click();
		driver.findElement(By.id("name")).sendKeys(name);
		driver.findElement(By.cssSelector("input[value='Save']")).click();
	}

	@Then("^I should have (.+) in (.+)$")
	public void assertResult(String entity, String page) {
		assertTrue(
				"Product should be available in the Products listing table!",
				driver.findElement(
						By.xpath("//table[@class='tablesorter']//tbody/tr[last()]/td[1]"))
						.getText().equals(entity));
	}
}