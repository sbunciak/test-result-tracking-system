package org.jboss.community.trts.test.ui;

import static org.junit.Assert.assertTrue;

import java.net.URL;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.community.trts.model.Product;
import org.jboss.community.trts.rest.JaxRsActivator;
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

	private static final String WEBAPP_SRC = "src/main/webapp";

	@Deployment(testable = false)
	public static WebArchive create() {
		return ShrinkWrap
				.create(WebArchive.class, "test.war")
				.addPackage(Product.class.getPackage())
				.addPackage(ProductService.class.getPackage())
				.addClasses(ProductREST.class, JaxRsActivator.class,
						Resources.class)
				.merge(ShrinkWrap.create(GenericArchive.class)
						.as(ExplodedImporter.class).importDirectory(WEBAPP_SRC)
						.as(GenericArchive.class), "/",
						Filters.exclude(".*\\.xml$"))
				.addAsResource("META-INF/test-persistence.xml",
						"META-INF/persistence.xml")
				.addAsWebInfResource("test-ds.xml", "test-ds.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@Test
	@RunAsClient
	public void canAddProduct() {
		browser.setSpeed("2000"); // 2 seconds between each operation
		
		browser.open(deploymentUrl.toString());

		browser.click("css=a[href='#products']");

		browser.type("id=name", "JBoss Developer Studio");
		browser.click("css=input[type='submit']");

		assertTrue("Product should be created!",
				browser.isVisible("//h4[@class='alert_success']"));
	}
}