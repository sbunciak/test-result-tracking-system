test-result-tracking-system
===========================

Diploma thesis implementation using JBoss open-source projects and JEE 6 technologies
---------------------------


The goal of this thesis is to develop a test result tracking application using JBoss open-source projects and JEE 6 technologies. A student is supposed to learn basic JEE 6 technologies (e.g. Java Security, REST, Java Persistence API, Enterprise Java Beans, Servlets, Java Server Faces). The application should provide configuration of products, product versions and builds. Based on Java EE 6 webapp project for use on JBoss AS 7.1 / EAP 6, generated from the jboss-javaee6-webapp archetype

New DSL (Domain Specific Language) for JBoss Drools was introduced during implementation of TestRunCase generation. Here is current syntax (will be modified/enriched):

	rule 'Filter Java 1.6'
		when
			There is a TestPlan
		
			There is a TestRunCase with
				- title 'Manual Tests'
		
			There is an Axis with
				- category 'Java'
		
			There is an AxisConfig with
				- appropriate axis
				- value '1.6'
			
			The AxisConfig has been used
		then
			filter this TestRunCase
	end

To Run:

Add mysql module to eap, MysqlDS Datasource to eap.

<datasource jndi-name="java:jboss/datasources/MysqlDS"
		pool-name="{artifactId}" enabled="true" jta="true" use-java-context="true"
		use-ccm="true">
		<connection-url>
			jdbc:mysql://localhost:3306/trts
		</connection-url>
		<driver>mysql</driver>
		<security>
			<user-name>root</user-name>
			<password></password>
		</security>
		<statement>
			<prepared-statement-cache-size>100</prepared-statement-cache-size>
			<share-prepared-statements />
		</statement>
	</datasource>

add security domain to standalone.xml
	
				<security-domain name="trts-realm">
                    <authentication>
                        <login-module code="UsersRoles" flag="required">
                            <module-option name="usersProperties" value="users.properties"/>
                            <module-option name="rolesProperties" value="roles.properties"/>
                        </login-module>
                    </authentication>
                </security-domain>
                
