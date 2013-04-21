[condition][]There is a TestPlan=$p : TestPlan()
[condition][]There is an Axis with=$ax : Axis()
[condition][]- category '{category}'=category=="{category}"
[condition][]- category "{category}"=category=="{category}"
[condition][]There is an AxisConfig with=$ac : AxisConfig()
[condition][]- appropriate axis=axisValue.axis==$ax
[condition][]- value '{value}'=axisValue.value=="{value}"
[condition][]- value "{value}"=axisValue.value=="{value}"
[condition][]There is a TestRunCase with=$rc : TestRunCase()
[condition][]- title '{title}'=title=="{title}"
[condition][]- title "{title}"=title=="{title}"
[condition][]The AxisConfig has been used=eval($rc.getCriterias().contains($ac))
[consequence][]filter this TestRunCase=runCases.remove( $rc );
