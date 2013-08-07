package org.jboss.community.trts.test.ui;

import static org.junit.Assert.assertTrue;

import java.net.URL;

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
import org.junit.Test;
import org.junit.runner.RunWith;

import com.thoughtworks.selenium.DefaultSelenium;

@RunWith(Arquillian.class)
public class ProductUITest {

	@Drone
	private DefaultSelenium browser;

	@ArquillianResource
	private URL deploymentUrl;

	@Deployment(testable = false)
	public static WebArchive create() {
		return ShrinkWrap
				.create(WebArchive.class, "test.war")
				.addPackage(Product.class.getPackage())
				.addPackage(ProductService.class.getPackage())
				.addPackage(ProductREST.class.getPackage())
				.addClass(Resources.class)
				.merge(ShrinkWrap.create(GenericArchive.class)
						.as(ExplodedImporter.class)
						.importDirectory("src/main/webapp")
						.as(GenericArchive.class), "/",
						Filters.exclude(".*\\.xml$"))
				.addAsResource("META-INF/test-persistence.xml",
						"META-INF/persistence.xml")
				.addAsWebInfResource("test-jboss-web.xml", "jboss-web.xml")
				.addAsWebInfResource("test-roles.properties",
						"/classes/roles.properties")
				.addAsWebInfResource("test-users.properties",
						"/classes/users.properties")
				.addAsWebInfResource("test-ds.xml", "test-ds.xml")
				.addAsWebInfResource("test-web.xml", "web.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@Test
	@RunAsClient
	public void canAddProduct() {
		browser.setSpeed("2000"); // 2 seconds between each operation

		String applicationUrl = deploymentUrl.toString().replace("http://",
				"http://admin:admin@");

		browser.open(applicationUrl);

		browser.click("css=a[href='#/products']");

		browser.type("id=name", "JBoss Developer Studio");
		browser.click("css=input[value='Save']");

		assertTrue("Product should be created!",
				browser.isVisible("//h4[@class='alert_success']"));
	}
}
