package org.jboss.community.trts.test.bdd;

import java.net.URL;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.drone.api.annotation.Drone;
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
import org.junit.runner.RunWith;

import com.thoughtworks.selenium.DefaultSelenium;

import cucumber.runtime.arquillian.ArquillianCucumber;
import cucumber.runtime.arquillian.api.Features;
import cucumber.runtime.arquillian.api.Glues;

@Glues(Steps.class)
@Features({ "org/jboss/community.trts.bdd.acceptance.feature" })
@RunWith(ArquillianCucumber.class)
public class AcceptanceTest {

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
				.addPackage(ProductREST.class.getPackage())
				.addClasses(JaxRsActivator.class,Resources.class)
				.merge(ShrinkWrap.create(GenericArchive.class)
						.as(ExplodedImporter.class).importDirectory(WEBAPP_SRC)
						.as(GenericArchive.class), "/",
						Filters.exclude(".*\\.xml$"))
				.addAsResource("META-INF/test-persistence.xml",
						"META-INF/persistence.xml")
				.addAsWebInfResource("test-ds.xml", "test-ds.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}
}