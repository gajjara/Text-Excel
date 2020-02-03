package testExcel2;

import java.text.SimpleDateFormat;

// This is similar to the original hints #2 document. 
// This contains *all* the method signatures I used to format the table 
// along with some descriptions of how they are written
public class Spreadsheet
{
	private final static int ROWS = 10;
	private final static int COLS = 7;
	private final static int CELL_WIDTH = 12; 
	private Cell[][] data;
	private Cell[][] celldata;
	//this other array of cells is meant to store the values that the user enters
	//so if the user enters something like B8, it gives back the value that the user inputed for that cell

	// constructor
	public Spreadsheet()
	{
		data = new Cell[ROWS][COLS];
		celldata = new Cell[ROWS][COLS];
	}

	// prints the grid to the console
	public void print()
	{	
		printSheetHeading();
		for(int i = 0; i < ROWS; i++) {
			printRow(i);
		}
	}

	// Prints a blank row
	private void printRow(int row)
	{
		String dividerextension = "";
		for(int i = 1; i <= CELL_WIDTH; i++) {
			dividerextension = dividerextension + "-";
		}
		dividerextension = "+" + dividerextension;

		printRowHeading(row + 1);

		for(int i = 0; i < COLS; i++) {
			printCell(row , i);
		}
		System.out.println();
		System.out.print(dividerextension);
		printDivider();
	}

	/* * Just prints something like "| 1 |" */
	private void printRowHeading(int row)
	{
		String heading = "|" + center(Integer.toString(row), CELL_WIDTH) + "|";
		System.out.print(heading);
	}

	// Prints out a centered value for a cell
	private void printCell(int row, int col)
	{
		if(data[row][col] == null) {
			String blankspace = "";

			for(int i = 1; i <= CELL_WIDTH; i++) {
				blankspace = blankspace + " ";
			}
			String value = blankspace + "|";

			System.out.print(value);
		}

		else {
			String value = data[row][col].getValue();
			String centeredvalue = center(value, CELL_WIDTH) + "|";

			System.out.print(centeredvalue);
		}
	}

	// Prints the sheet heading
	private void printSheetHeading()
	{
		//for the blank cell on the top left of the spreadsheet
		String blankheadingtop = "";
		for(int i = 1; i <= CELL_WIDTH; i++) {
			blankheadingtop = blankheadingtop + "-";
		}
		blankheadingtop = "+" + blankheadingtop;

		String blankheadingmiddle = "";
		for(int i = 1; i <= CELL_WIDTH; i++) {
			blankheadingmiddle = blankheadingmiddle + " ";
		}
		blankheadingmiddle = "|" + blankheadingmiddle;

		String blankheadingbottom = "";
		for(int i = 1; i <= CELL_WIDTH; i++) {
			blankheadingbottom = blankheadingbottom + "-";
		}
		blankheadingbottom = "+" + blankheadingbottom;	

		//top part of header
		System.out.print(blankheadingtop);	
		printDivider();

		String heading = "";  				
		for(int i = 1; i <= COLS; i++) {
			//the (char) (i + 64) expression is meant to create the characters A, B, C, D, E, F, G, etc.
			heading = heading + "|" + center(Character.toString((char) (i + 64)) ,CELL_WIDTH);
		}

		//middle part of header
		String finalheading = blankheadingmiddle + heading + "|";
		System.out.println(finalheading);

		//bottom part of header
		System.out.print(blankheadingbottom);
		printDivider();
	}

	// Centers a given input given a width of the cell
	private String center(String value, int width)
	{
		String returnstring = "";

		int space = width - value.length();
		int leftorrightwidth = space/2;

		if(value.length() % 2 == 0) {
			returnstring = repeatedChar(' ', leftorrightwidth) + value + repeatedChar(' ', leftorrightwidth);
		}
		else {
			returnstring = repeatedChar(' ', leftorrightwidth) + value + " " + repeatedChar(' ', leftorrightwidth);
		}

		return returnstring;
	}

	/* * Prints divider between rows and starts a new line */
	private void printDivider()
	{
		String line = "";
		for(int i = 1; i <= CELL_WIDTH; i++) {
			line = line + "-";
		}
		line = "+" + line;

		String wholeline = "";
		for(int i = 1; i <= COLS; i++) {
			wholeline = line + wholeline;
		}

		System.out.println(wholeline + "+");
	}

	/* * Returns a String made up of length number of ch characters */
	private String repeatedChar(char ch, int length)
	{
		String result = "";
		for (int i = 0; i < length; i++) {
			result = result + ch;
		}
		return result;
	}

	// Returns the index of the row given the user input
	private int getRowIndex (String input) {
		int rowindex = 0;
		String rowandcolumnvalue = "";
		if(input.contains("=")) {
			rowandcolumnvalue = input.substring(0, (input.indexOf("=") - 1) );
		}
		else {
			rowandcolumnvalue = input;
		}
		String rowstringindex = rowandcolumnvalue.substring(1);
		rowindex = Integer.parseInt(rowstringindex) - 1;

		return rowindex;
	}

	// Returns the index of the column given the user input
	private int getColumnIndex (String input) {
		int columnindex = 0;
		String rowandcolumnvalue = "";
		if(input.contains("=")) {
			rowandcolumnvalue = input.substring(0, (input.indexOf("=") - 1) );
		}
		else {
			rowandcolumnvalue = input;
		}
		String columnletterindex = rowandcolumnvalue.substring(0, 1);

		for(int i = 0; i < COLS; i++) {
			char chars = (char) ('A' + i);	
			if(columnletterindex.equals(Character.toString(chars))) {
				columnindex = i;
			}
		}	

		return columnindex;
	}

	// Stores the full value that a user entered into a cell
	// so that this value can be given back to the user
	public void storeValueToGiveBackToUser (String input) {
		String value = input.substring(input.indexOf("=") + 2);
		
		int rowindex = getRowIndex(input);
		int columnindex = getColumnIndex(input);
		
		celldata[rowindex][columnindex] = CellFactory.makeCell(value);
	}
	
	// returns the value stored in a cell
	private String getStoredValue (String input) {
		int rowindex = getRowIndex(input);
		int columnindex = getColumnIndex(input);
		
		return celldata[rowindex][columnindex].getValue();
	}
	
	// Sets the value of a cell based off of the user input, the row index from the user input
	// and the column index from the user input, and stores that value in the indices the user referenced
	public void setValueOfCell (String input, Spreadsheet sheet) {
		int rowindex = getRowIndex(input);
		int columnindex = getColumnIndex(input);
		String value = input.substring(input.indexOf("=") + 2);
		String valuetouse = "";
			
		char quotechar = '"';
		String quote = Character.toString(quotechar);
		
		if(value.length() > (CELL_WIDTH + 1)) {
			String valuethatiscutoff = (value.substring(1, value.length() - 1));
			
			boolean isadouble = true;
			boolean isadate = true;

			try 
			{
				@SuppressWarnings("unused")
				double test = Double.parseDouble(value);
			}
			catch (Exception e) {
				isadouble = false;
			}

			try 
			{
				SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
				format.parse(value);
			}
			catch (Exception e) {
				isadate = false;
			}

			if(isadouble == false && isadate == false) {
				valuetouse = quote + valuethatiscutoff.substring(0, CELL_WIDTH - 1) + ">" + quote;
			}
			else {
				valuetouse = valuethatiscutoff.substring(0, CELL_WIDTH - 1);
			}
		}
		else {
			valuetouse = value;
		}
		
		sheet.data[rowindex][columnindex] = CellFactory.makeCell(valuetouse);
	}

	// This method checks if the user entered in the correct cell reference
	public boolean cellReferenceIsCorrect(String input) {
		boolean result = false;

		try {
			int rowindex = getRowIndex(input);
			int columnindex = getColumnIndex(input);

			if(columnindex < COLS && rowindex < ROWS) {
				result = true;
			}
		}
		catch (Exception e) {
		}

		return result;
	}

	// Prints out the value of a cell if the user only entered the cell reference
	public void printOutValueOfReference(String input, Spreadsheet sheet) {
		int rowindex = getRowIndex(input);
		int columnindex = getColumnIndex(input);
		boolean cellisnotempty = true;

		try {
			data[rowindex][columnindex].getValue();
		}
		catch (Exception e) {
			System.out.println(input + " = " + "<empty>");
			cellisnotempty = false;
		}

		if(cellisnotempty == true) {
			String value = sheet.getStoredValue(input);

			char quotechar = '"';
			String quote = Character.toString(quotechar);

			boolean isadouble = true;
			boolean isadate = true;

			try 
			{
				@SuppressWarnings("unused")
				double test = Double.parseDouble(value);
			}
			catch (Exception e) {
				isadouble = false;
			}

			try 
			{
				SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
				format.parse(value);
			}
			catch (Exception e) {
				isadate = false;
			}

			if(isadouble == false && isadate == false) {
				System.out.println(input + " = " + quote + sheet.getStoredValue(input) + quote);
			}
			else {
				System.out.println(input + " = " + sheet.getStoredValue(input));
			}
		}
		else {
		}
	}

	// main string for testing methods
	public static void main(String[] args) {    
//		Spreadsheet sheet = new Spreadsheet();
	}


}
