package org.jboss.community.trts.test;

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
public class AxisServiceTest {

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
		return ShrinkWrap
				.create(WebArchive.class, "test.war")
				.addPackage(Axis.class.getPackage())
				.addPackage(AxisService.class.getPackage())
				.addClass(Resources.class)
				.addAsResource("META-INF/test-persistence.xml",
						"META-INF/persistence.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
				.addAsWebInfResource("test-ds.xml", "test-ds.xml");
	}


	@Test
	public void canCreateUpdateDeleteAxis() {
		Axis axis = new Axis();
		axis.setCategory("Java");
		axis.setDescription("Test axis to meet Java requirements.");
		
		axisService.persist(axis);
		
		assertNotNull(axis.getId());
		
		Axis axisFromDB = axisService.getAxisById(axis.getId());
		
		assertEquals(axis, axisFromDB);
		
		axis.setCategory("OS");
		axis.setDescription("Test axis to meet OS requirements.");
		
		axisService.update(axis);
		
		axisFromDB = axisService.getAxisById(axis.getId());
		assertEquals(axis.getCategory(), axisFromDB.getCategory());
		assertEquals(axis.getDescription(), axisFromDB.getDescription());
		
		axisService.delete(axisFromDB);
		
		assertTrue(axisService.getAllAxis().size() == 0);
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
		val1.setAxis(axis);
		val1.setValue("7");
		
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
		
		AxisConfig dbconfig = configService.getAxisConfigById(config.getId());
		
		assertEquals(dbconfig, config);
		
		config.setPriority(AxisPriority.DISABLED);
		
		configService.update(config);
		
		dbconfig = configService.getAxisConfigById(config.getId());
		
		assertEquals(dbconfig.getPriority(), config.getPriority());
		
		configService.delete(dbconfig);
		
		assertTrue(configService.getAxisConfigs(axis).size() == 0);
	}
}
