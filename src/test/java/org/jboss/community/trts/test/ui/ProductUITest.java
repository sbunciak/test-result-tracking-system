package org.jboss.community.trts.test.ui;

import static org.junit.Assert.assertTrue;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.community.trts.model.Product;
import org.jboss.community.trts.rest.ProductREST;
import org.jboss.community.trts.service.ProductService;
import org.jboss.community.trts.util.Resources;
import org.jboss.shrinkwrap.api.Filters;
import org.jboss.shrinkwrap.api.GenericArchive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.importer.ExplodedImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;

@RunWith(Arquillian.class)
public class ProductUITest {

	@Drone
	private FirefoxDriver driver;

	@ArquillianResource
	private URL deploymentUrl;
	private String applicationUrl = null;

	@Deployment(testable = false)
	public static WebArchive create() {
		WebArchive archive = ShrinkWrap.create(WebArchive.class, "test.war");

		archive.addPackage(Product.class.getPackage())
				.addPackage(ProductService.class.getPackage())
				.addPackage(ProductREST.class.getPackage())
				.addClass(Resources.class)
				.addAsResource("META-INF/test-persistence.xml",
						"META-INF/persistence.xml")
				.addAsWebInfResource("test-ds.xml", "test-ds.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
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
	}

	@Test
	@RunAsClient
	public void canAddProduct() {
		driver.get(applicationUrl);

		driver.findElement(By.partialLinkText("Product")).click();

		driver.findElement(By.partialLinkText("Create new product")).click();

		driver.findElement(By.id("name")).sendKeys("JBoss Developer Studio");

		driver.findElement(By.cssSelector("input[value='Save']")).click();

		assertTrue("Product should be created!",
				driver.findElement(By.className("alert_success")) != null);
	}
}
