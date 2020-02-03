package testExcel2;

public class NumberCell extends Cell {
	double number;
	
	public NumberCell (String userInput) {
		super(userInput);
		number = Double.parseDouble(userInput);
	}
	
	public double getSheetValue() {
		return number;
	}
	
	public String getDisplayValue() {
		return "" + number + "";
	}
}
