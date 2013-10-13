package org.jboss.community.trts.test;

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

/**
 * Base class for Integration tests of TRTS.
 * 
 * @author sbunciak
 * 
 */
public class TRTSIntTest {

	protected static WebArchive createTestWebArchive(boolean uiEnabled) {
		WebArchive archive = ShrinkWrap.create(WebArchive.class, "test.war");

		archive.addPackage(Product.class.getPackage())
				.addPackage(ProductService.class.getPackage())
				.addPackage(ProductREST.class.getPackage())
				.addClass(Resources.class)
				.addClass(TRTSIntTest.class)
				.addAsResource("META-INF/test-persistence.xml",
						"META-INF/persistence.xml")
				.addAsWebInfResource("test-ds.xml", "test-ds.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");

		if (uiEnabled) {
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
		}

		return archive;

	}

}
