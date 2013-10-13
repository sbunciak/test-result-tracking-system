package org.jboss.community.trts.test.integration;

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
import org.jboss.community.trts.test.TRTSIntTest;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Test;
import org.junit.runner.RunWith;

/*
 * Integration tests using Arquillian according to the current Use Case diagram
 */
@RunWith(Arquillian.class)
public class ProductServiceTest extends TRTSIntTest {

	@Inject
	private ProductService productService;

	@Inject
	private ProductVersionService versionService;

	@Inject
	private ProductBuildService buildService;

	@Deployment
	public static Archive<?> createTestArchive() {
		return createTestWebArchive(false);
	}

	@Test
	public void canCreateUpdateDeleteProduct() {
		Product product = new Product();
		product.setName("JBoss Developer Studio");

		productService.persist(product);

		assertNotNull(product.getId());

		product.setDescription("Eclipse-base IDE by Red Hat");

		productService.update(product);

		product = productService.getById(product.getId());

		assertEquals(product.getDescription(), "Eclipse-base IDE by Red Hat");

		productService.delete(product);

		assertTrue(productService.getAll().size() == 0);
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

		version = versionService.getById(version.getId());

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

		build = buildService.getById(build.getId());

		assertEquals(build.getLabel(), "Final");

		buildService.delete(build);

		assertTrue(buildService.getProductBuildsByProductVersion(version)
				.size() == 0);

		versionService.delete(version);
		productService.delete(product);
	}

}
