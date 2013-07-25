-- You can use this file to load seed data into the database using SQL statements
-- Import initial (testing) data
-- Insert Product
insert into Product (id, name, description) values (1, 'JBDS', 'JBoss Developer Studio');
insert into Product (id, name, description) values (2, 'SOA-P', 'SOA Platform');
-- Insert Product versions
insert into ProductVersion (id, description, productVersion, product_id) values (1, 'Latest stable' , '6.0.0' ,1);
insert into ProductVersion (id, description, productVersion, product_id) values (2, 'Latest nightly' , '7.0.0' ,1);
insert into ProductVersion (id, description, productVersion, product_id) values (3, 'CAN BE DELETED' , '7.0.1' ,1);
-- Insert Product version builds
insert into ProductBuild (id, label, productVersion_id) values (1, 'Alpha1' , 1);
insert into ProductBuild (id, label, productVersion_id) values (2, 'Alpha2' , 1);
insert into ProductBuild (id, label, productVersion_id) values (3, 'Beta1' , 1);
insert into ProductBuild (id, label, productVersion_id) values (4, 'Beta2' , 1);
insert into ProductBuild (id, label, productVersion_id) values (5, 'CR1' , 1);
insert into ProductBuild (id, label, productVersion_id) values (6, 'GA' , 1);
-- Insert Test axis
insert into Axis (id, category, description) values (1, 'Java', 'Platform indepentend environment');
insert into Axis (id, category, description) values (2, 'OS', 'Operation system type');
-- Insert Axis values
insert into AxisValue (id, value, axis_id) values (1, '1.6', 1);
insert into AxisValue (id, value, axis_id) values (2, '1.7', 1);
insert into AxisValue (id, value, axis_id) values (3, 'Linux', 2);
insert into AxisValue (id, value, axis_id) values (4, 'Windows', 2);
-- Insert Axis configs
insert into AxisConfig (id, axisValue_id, productVersion_id, priority) values (1, 1, 1, 0);
insert into AxisConfig (id, axisValue_id, productVersion_id, priority) values (2, 2, 1, 5);

-- Insert Test plan with rules
insert into TestPlan (id, description, name, rules, type, productVersion_id) values (1, 'Dummy description', 'Test plan for JBDS 6', 
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
end', 'SMOKE', 1);
-- Insert Test plan without rules
insert into TestPlan (id, description, name, rules, type, productVersion_id) values (2, 'Dummy description', 'Test plan for JBDS 7', '', 'SMOKE', 2);
-- Insert Test cases for test plan with rules
insert into TestCase (id, ciLink, defaultTester, title, testPlan_id) values (1, 'http://jenkins.brq.redhat.com', 'sbunciak', 'Manual Tests', 1);
insert into TestCase (id, ciLink, defaultTester, title, testPlan_id) values (2, 'http://jenkins.brq.redhat.com', 'sbunciak', 'Auto Tests', 1);
-- Insert Test cases for test plan without rules
insert into TestCase (id, ciLink, defaultTester, title, testPlan_id) values (3, 'http://jenkins.brq.redhat.com', 'sbunciak', 'JBDS can be installed without errors', 2);
insert into TestCase (id, ciLink, defaultTester, title, testPlan_id) values (4, 'http://jenkins.brq.redhat.com', 'sbunciak', 'JBDS can deploy app to OpenShift', 2);