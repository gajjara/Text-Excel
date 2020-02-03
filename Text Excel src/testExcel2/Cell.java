package testExcel2;

public abstract class Cell {
	public String value;

	public Cell(String initialValue) {
		value = initialValue;
	}

	public String getValue() {
		return value;
	}

	public abstract String getDisplayValue();
}