package testExcel2;

public class StringCell extends Cell {
	String string;
	
	public StringCell (String userInput) {
		super(userInput);
	}
	
	public String getSheetValue( ) {
		return string;
	}

	public String getDisplayValue() {
		return string;
	}
}
