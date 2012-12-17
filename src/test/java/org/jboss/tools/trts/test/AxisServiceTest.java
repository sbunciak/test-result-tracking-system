package org.jboss.tools.trts.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.community.trts.model.Axis;
import org.jboss.community.trts.model.AxisCriteria;
import org.jboss.community.trts.model.AxisPriority;
import org.jboss.community.trts.service.AxisCriteriaService;
import org.jboss.community.trts.service.AxisService;
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
	private AxisCriteriaService criteriaService;

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
	public void canCreateUpdateDeleteAxisCriteria() {
		Axis axis = new Axis();
		axis.setCategory("Java");
		axis.setDescription("Test axis to meet Java requirements.");
		
		axisService.persist(axis);
		
		assertNotNull(axis.getId());
		
		AxisCriteria criteria = new AxisCriteria();
		criteria.setAxis(axis);
		criteria.setPriority(AxisPriority.P1);
		criteria.setValue("1.6");
		
		criteriaService.persist(criteria);
		
		assertNotNull(criteria.getId());
		
		AxisCriteria dbCriteria = criteriaService.getAxisCriteriaById(criteria.getId());
		
		assertEquals(dbCriteria, criteria);
		
		criteria.setPriority(AxisPriority.DISABLED);
		
		criteriaService.update(criteria);
		
		dbCriteria = criteriaService.getAxisCriteriaById(criteria.getId());
		
		assertEquals(dbCriteria.getPriority(), criteria.getPriority());
		
		criteriaService.delete(dbCriteria);
		
		assertTrue(criteriaService.getAxisCriterias(axis).size() == 0);
	}
}
