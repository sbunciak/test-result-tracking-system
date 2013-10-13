package org.jboss.community.trts.test.rest;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.community.trts.model.Product;
import org.jboss.community.trts.service.ProductService;
import org.jboss.community.trts.test.TRTSIntTest;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class ProductRESTServiceTest extends TRTSIntTest {

	private String REST_URL_PRODUCTS = "http://localhost:8080/test/rest/products/";

	@Inject
	private ProductService productService;

	@Deployment
	public static WebArchive create() {
		return createTestWebArchive(false);
	}

	@Test
	public void canObtainProduct() {
		Product product = new Product();
		product.setName("JBoss Developer Studio");

		productService.persist(product);

		assertNotNull(product.getId());

		// Using the RESTEasy libraries, initiate a client request
		ClientRequest request = new ClientRequest(REST_URL_PRODUCTS
				+ product.getId());

		// Be sure to set the mediatype of the request
		request.accept(MediaType.APPLICATION_JSON);

		// Request has been made, now let's get the response
		try {
			ClientResponse<Product> response = request.get(Product.class);
			assertTrue("Failed request", response.getStatus() == 200);

			Product responseEntity = response.getEntity(Product.class);
			assertTrue("Object are not equal", responseEntity.equals(product));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
