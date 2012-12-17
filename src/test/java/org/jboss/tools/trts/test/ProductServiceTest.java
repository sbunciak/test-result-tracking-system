package org.jboss.tools.trts.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.community.trts.model.Product;
import org.jboss.community.trts.model.ProductBuild;
import org.jboss.community.trts.model.ProductVersion;
import org.jboss.community.trts.service.ProductBuildService;
import org.jboss.community.trts.service.ProductService;
import org.jboss.community.trts.service.ProductVersionService;
import org.jboss.community.trts.util.Resources;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

/*
 * Integration tests using Arquillian according to the current Use Case diagram
 */
@RunWith(Arquillian.class)
public class ProductServiceTest {

	@Inject
	private ProductService productService;

	@Inject
	private ProductVersionService versionService;

	@Inject
	private ProductBuildService buildService;

	@Deployment
	public static Archive<?> createTestArchive() {
		return ShrinkWrap
				.create(WebArchive.class, "test.war")
				.addPackage(Product.class.getPackage())
				.addPackage(ProductService.class.getPackage())
				.addClass(Resources.class)
				.addAsResource("META-INF/test-persistence.xml",
						"META-INF/persistence.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
				.addAsWebInfResource("test-ds.xml", "test-ds.xml");
	}

	@Test
	public void canCreateUpdateDeleteProduct() {
		Product product = new Product();
		product.setName("JBoss Developer Studio");

		productService.persist(product);

		assertNotNull(product.getId());

		product.setDescription("Eclipse-base IDE by Red Hat");

		productService.update(product);

		product = productService.getProductById(product.getId());

		assertEquals(product.getDescription(), "Eclipse-base IDE by Red Hat");

		productService.delete(product);

		assertTrue(productService.getAllProducts().size() == 0);
	}

	@Test
	public void canCreateUpdateDeleteProductVersion() {
		Product product = new Product();
		product.setName("JBoss Developer Studio");

		productService.persist(product);

		ProductVersion version = new ProductVersion();
		version.setProduct(product);
		version.setProductVersion("6.0.0");

		versionService.persist(version);

		assertNotNull(version.getId());

		version.setDescription("Latest-greates version of JBDS");

		versionService.update(version);

		version = versionService.getProductVersionById(version.getId());

		assertEquals(version.getDescription(), "Latest-greates version of JBDS");

		versionService.delete(version);

		assertTrue(versionService.getProductVersionByProduct(product).size() == 0);

		productService.delete(product);
	}

	@Test
	public void canCreateUpdateDeleteProductBuild() {
		Product product = new Product();
		product.setName("JBoss Developer Studio");

		productService.persist(product);

		ProductVersion version = new ProductVersion();
		version.setProduct(product);
		version.setProductVersion("6.0.0");

		versionService.persist(version);

		ProductBuild build = new ProductBuild();
		build.setLabel("CR1");
		build.setProductVersion(version);

		buildService.persist(build);

		assertNotNull(build.getId());

		build.setLabel("Final");

		buildService.update(build);

		build = buildService.getProductBuildById(build.getId());

		assertEquals(build.getLabel(), "Final");

		buildService.delete(build);

		assertTrue(buildService.getProductBuildsByProductVersion(version)
				.size() == 0);

		versionService.delete(version);
		productService.delete(product);
	}

}
