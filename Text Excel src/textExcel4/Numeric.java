package textExcel4;

//used to get a value of a formula or number cell without having to call the getDisplayValue
// or getOriginalValue methods of those two classes
public interface Numeric {
	public double getNumericValue();
}
