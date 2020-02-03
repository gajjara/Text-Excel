package textExcel3;

public class FormulaCell extends Cell {

	// stores the value of the expression
	private double value;

	// constructs a new FormulaCell. If the value isn't enclosed in parentheses, then this will 
	// throw an exception and a FormulaCell will not be created.
	public FormulaCell(String value) 
	{
		super(value);

		if (!value.startsWith("(") || !value.endsWith(")"))
			throw new IllegalArgumentException("Formula values need to start and end with parentheses. '" + value + "' did not.");
	}

	// returns the value of the expression in the correct form for display in the grid
	@Override
	public String getDisplayValue() 
	{
		String expression = getOriginalValue().substring(1, getOriginalValue().length() - 1);
		value = ExpressionSimplifier.simplify(expression);
		return "" + value;
	}

}
