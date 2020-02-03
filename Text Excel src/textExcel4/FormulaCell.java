package textExcel4;

public class FormulaCell extends Cell implements Numeric{

	// stores the value of the expression
	private double value;
	// creates a variable to be set by the spreadsheet being inputed into FormulaCell
	private Spreadsheet sheettouse;

	// constructs a new FormulaCell. If the value isn't enclosed in parentheses, then this will 
	// throw an exception and a FormulaCell will not be created.
	public FormulaCell(String value, Spreadsheet sheet) 
	{
		super(value);
		sheettouse = sheet;
		if (!value.startsWith("(") || !value.endsWith(")"))
			throw new IllegalArgumentException("Formula values need to start and end with parentheses. '" + value + "' did not.");
	}

	// returns the value of the expression in the correct form for display in the grid
	@Override
	public String getDisplayValue() 
	{
		String expression = getOriginalValue().substring(1, getOriginalValue().length() - 1);
		value = ExpressionSimplifier.simplify(expression, sheettouse);
		return "" + value;
	}

	@Override
	public double getNumericValue() {
		return value;
	}

}
