package org.jboss.community.trts.util;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.Stateless;
import javax.faces.bean.RequestScoped;
import javax.inject.Named;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.command.CommandFactory;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.jboss.community.trts.model.Axis;
import org.jboss.community.trts.model.AxisCriteria;
import org.jboss.community.trts.model.TestPlan;
import org.jboss.community.trts.model.TestRunCase;

@Named
@Stateless
@RequestScoped
public class RulesProcessor {

	@SuppressWarnings("unchecked")
	public List<TestRunCase> filterTestRunCases(TestPlan plan,
			List<TestRunCase> runCases, Map<Axis, Set<AxisCriteria>> axisMap) {

		if (plan.getRules().isEmpty())
			return runCases;

		// Create Knowledge builder
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory
				.newKnowledgeBuilder();

		// Add custom DSL
		kbuilder.add(ResourceFactory.newClassPathResource("default.dsl"),
				ResourceType.DSL);

		// Add custom rules
		kbuilder.add(ResourceFactory.newReaderResource(new StringReader(
				buildRules(plan.getRules()))), ResourceType.DSLR);

		//System.out.println(plan.getRules());

		// Check for errors
		if (kbuilder.hasErrors()) {
			System.err.println(kbuilder.getErrors().toString());
		}

		// Create Knowledge base
		KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
		kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
		StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();

		// Create global results list
		ksession.setGlobal("runCases", runCases);

		// Insert objects
		ksession.execute(CommandFactory.newInsert(plan));
		ksession.execute(CommandFactory.newInsertElements(runCases));
		ksession.execute(CommandFactory.newInsertElements(axisMap.keySet()));
		List<Set<AxisCriteria>> l = new ArrayList<Set<AxisCriteria>>(
				axisMap.values());
		for (Set<AxisCriteria> s : l) {
			ksession.execute(CommandFactory.newInsertElements(s));
		}

		ksession.fireAllRules();

		return runCases;
	}

	private String buildRules(String planRules) {
		
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("import org.jboss.community.trts.model.TestPlan;" + "\n");
		strBuilder.append("import org.jboss.community.trts.model.TestRunCase;" + "\n");
		strBuilder.append("import org.jboss.community.trts.model.AxisCriteria;" + "\n");
		strBuilder.append("import org.jboss.community.trts.model.Axis;" + "\n");

		strBuilder.append("global java.util.List runCases;" + "\n");
		strBuilder.append(planRules);
		
		return strBuilder.toString();
	}
	
}
