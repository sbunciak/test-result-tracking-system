package org.jboss.tools.trts.test.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.community.trts.model.Product;
import org.jboss.community.trts.rest.JaxRsActivator;
import org.jboss.community.trts.rest.ProductRESTService;
import org.jboss.community.trts.service.ProductService;
import org.jboss.community.trts.util.Resources;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.util.GenericType;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class ProductRESTServiceTest {

	@Deployment(testable = false)
	public static WebArchive create() {
		return ShrinkWrap
				.create(WebArchive.class, "test.war")
				.addPackage(Product.class.getPackage())
				.addClasses(ProductService.class, ProductRESTService.class,
						JaxRsActivator.class, Resources.class)
				.addAsResource("import.sql")
				.addAsResource("META-INF/test-persistence.xml",
						"META-INF/persistence.xml")
				.addAsWebInfResource("test-ds.xml", "test-ds.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	/*
	 * https://github.com/arquillian/arquillian-extension-rest
	 */
	//@Test
	@GET
	@Path("rest/products")
	@Consumes(MediaType.APPLICATION_JSON)
	public void shouldBeAbleToListAllCustomers(
			ClientResponse<List<Product>> response) {
		Assert.assertEquals(Status.OK.getStatusCode(), response.getStatus());

		List<Product> customers = response
				.getEntity(new GenericType<List<Product>>() {
				});
		Assert.assertEquals(0, customers.size());
	}
}
