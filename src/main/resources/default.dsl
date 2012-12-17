// DSL definition

// TestPlan
[when]There is a TestPlan=$p : TestPlan()

// Axis with category
[when]There is an Axis with=$ax : Axis()
[when]- category '{category}'=category=='{category}'

// Axis criteria with appropriate Axis and value
[when]There is an AxisCriteria with=$ac : AxisCriteria()
[when]- appropriate axis=axis==$ax
[when]- value '{value}'=value=='{value}'

// TestRunCase with title
[when]There is a TestRunCase with=$rc : TestRunCase()
[when]- title '{title}'=title=='{title}'

// Test if the criteria has already been used
[when]The AxisCriteria has been used=eval($rc.getCriterias().contains($ac))

[then]filter this TestRunCase=runCases.remove( $rc );