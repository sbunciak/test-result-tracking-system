package org.jboss.community.trts.test.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.community.trts.model.Axis;
import org.jboss.community.trts.model.AxisConfig;
import org.jboss.community.trts.model.AxisPriority;
import org.jboss.community.trts.model.AxisValue;
import org.jboss.community.trts.model.Product;
import org.jboss.community.trts.model.ProductVersion;
import org.jboss.community.trts.service.AxisConfigService;
import org.jboss.community.trts.service.AxisService;
import org.jboss.community.trts.service.AxisValueService;
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
public class AxisServiceTest extends TRTSIntTest {

	@Inject
	private AxisService axisService;
	
	@Inject
	private AxisValueService valueService;
	
	@Inject
	private AxisConfigService configService;

	@Inject
	private ProductService productService;
	
	@Inject
	private ProductVersionService versionService;
	
	@Deployment
	public static Archive<?> createTestArchive() {
		return createTestWebArchive(false);
	}

	@Test
	public void canCreateUpdateDeleteAxis() {
		Axis axis = new Axis();
		axis.setCategory("Java");
		axis.setDescription("Test axis to meet Java requirements.");
		
		axisService.persist(axis);
		
		assertNotNull(axis.getId());
		
		Axis axisFromDB = axisService.getById(axis.getId());
		
		assertEquals(axis, axisFromDB);
		
		axis.setCategory("OS");
		axis.setDescription("Test axis to meet OS requirements.");
		
		axisService.update(axis);
		
		axisFromDB = axisService.getById(axis.getId());
		assertEquals(axis.getCategory(), axisFromDB.getCategory());
		assertEquals(axis.getDescription(), axisFromDB.getDescription());
		
		axisService.delete(axisFromDB);
		
		assertTrue(axisService.getAll().size() == 0);
	}
	
	@Test
	public void canCreateUpdateDeleteAxisConfig() {
		Axis axis = new Axis();
		axis.setCategory("Java");
		axis.setDescription("Test axis to meet Java requirements.");
		
		axisService.persist(axis);
		
		assertNotNull(axis.getId());
		
		AxisValue val1 = new AxisValue();
		val1.setAxis(axis);
		val1.setValue("6");
		
		AxisValue val2 = new AxisValue();
		val2.setAxis(axis);
		val2.setValue("7");
		
		valueService.persist(val1);
		valueService.persist(val2);
		
		assertNotNull(val1.getId());
		assertNotNull(val2.getId());
		
		Product product = new Product();
		product.setName("JBoss Developer Studio");

		productService.persist(product);
		
		ProductVersion version = new ProductVersion();
		version.setProduct(product);
		version.setProductVersion("6.0.0");

		versionService.persist(version);
		
		AxisConfig config = new AxisConfig();
		config.setAxisValue(val1);
		config.setPriority(AxisPriority.P1);
		config.setProductVersion(version);
		
		configService.persist(config);
		
		assertNotNull(config.getId());
		
		AxisConfig dbconfig = configService.getById(config.getId());
		
		assertEquals(dbconfig, config);
		
		config.setPriority(AxisPriority.DISABLED);
		
		configService.update(config);
		
		dbconfig = configService.getById(config.getId());
		
		assertEquals(dbconfig.getPriority(), config.getPriority());
		
		configService.delete(dbconfig);
		
		assertTrue(configService.getById(config.getId()) == null);
	}
}
