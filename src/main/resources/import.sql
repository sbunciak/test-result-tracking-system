-- You can use this file to load seed data into the database using SQL statements
-- Import initial (testing) data
-- Insert Product
insert into Product (id, name, description) values (0, 'JBDS', 'JBoss Developer Studio');
insert into Product (id, name, description) values (1, 'SOA-P', 'SOA Platform');
-- Insert Product versions
insert into ProductVersion (id, description, productVersion, product_id) values (0, 'Latest stable' , '6.0.0' ,0);
insert into ProductVersion (id, description, productVersion, product_id) values (1, 'Latest nightly' , '7.0.0' ,0);
-- Insert Product version builds
insert into ProductBuild (id, label, productVersion_id) values (0, 'Alpha1' , 0);
insert into ProductBuild (id, label, productVersion_id) values (1, 'Alpha2' , 0);
insert into ProductBuild (id, label, productVersion_id) values (2, 'Beta1' , 0);
insert into ProductBuild (id, label, productVersion_id) values (3, 'Beta2' , 0);
insert into ProductBuild (id, label, productVersion_id) values (4, 'CR1' , 0);
insert into ProductBuild (id, label, productVersion_id) values (5, 'GA' , 0);
-- Insert Test axis
insert into Axis (id, category, description) values (0, 'Java', 'Platform indepentend environment');
insert into Axis (id, category, description) values (1, 'OS', 'Operation system type');
-- Insert Axis values
insert into AxisValue (id, value, axis_id) values (0, '1.6', 0);
insert into AxisValue (id, value, axis_id) values (1, '1.7', 0);
insert into AxisValue (id, value, axis_id) values (2, 'Linux', 1);
insert into AxisValue (id, value, axis_id) values (3, 'Windows', 1);
-- Insert Test plan with rules
insert into TestPlan (id, description, name, rules, type, productVersion_id) values (0, 'Dummy description', 'Test plan for JBDS 6', 
'rule "Filter Java 1.6"
when
There is a TestPlan
There is a TestRunCase with
	- title "Manual Tests"
There is an Axis with
	- category "Java"
There is an AxisConfig with
	- appropriate axis
	- value "1.6"
The AxisConfig has been used
then
filter this TestRunCase
end', 'SMOKE', 0);
-- Insert Test plan without rules
insert into TestPlan (id, description, name, rules, type, productVersion_id) values (1, 'Dummy description', 'Test plan for JBDS 7', '', 'SMOKE', 1);
-- Insert Test cases for test plan with rules
insert into TestCase (id, ciLink, defaultTester, title, testPlan_id) values (0, 'http://jenkins.brq.redhat.com', 'sbunciak', 'Manual Tests', 0);
insert into TestCase (id, ciLink, defaultTester, title, testPlan_id) values (1, 'http://jenkins.brq.redhat.com', 'sbunciak', 'Auto Tests', 0);
-- Insert Test cases for test plan without rules
insert into TestCase (id, ciLink, defaultTester, title, testPlan_id) values (2, 'http://jenkins.brq.redhat.com', 'sbunciak', 'JBDS can be installed without errors', 1);
insert into TestCase (id, ciLink, defaultTester, title, testPlan_id) values (3, 'http://jenkins.brq.redhat.com', 'sbunciak', 'JBDS can deploy app to OpenShift', 1);