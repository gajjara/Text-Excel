package testExcel1;

// This is similar to the original hints #2 document. 
// This contains *all* the method signatures I used to format the table 
// along with some descriptions of how they are written
public class Spreadsheet
{
	// these constants make it easy to remember the number of rows and
	// columns to use
	private final static int ROWS = 10; //formerly 10
	private final static int COLS = 7; //formerly 7
	private final static int CELL_WIDTH = 12; //formerly 12
	// the 2D array of cells
	private Cell[][] data;

	// constructor
	public Spreadsheet()
	{
		data = new Cell[ROWS][COLS];
		// all the Cells will start with the value null
	}

	// prints the grid to the console
	public void print()
	{	
		printSheetHeading();
		for(int i = 0; i < ROWS; i++) {
			printRow(i);
		}

		// first print the sheet heading (column letters, dividers, etc)
		// then loop through each row and print it
	}

	private void printRow(int row)
	{
		String dividerextension = "";
		for(int i = 1; i <= CELL_WIDTH; i++) {
			dividerextension = dividerextension + "-";
		}
		dividerextension = "+" + dividerextension;

		// add one to the zero-indexed row number for the heading
		// then loop through all the columns to print each cell in the row
		printRowHeading(row + 1);

		for(int i = 0; i < COLS; i++) {
			printCell(row , i);
		}
		System.out.println();
		System.out.print(dividerextension);
		printDivider();
		// then start a new line and print the row divider
	}

	/* * Just prints something like "| 1 |" */
	private void printRowHeading(int row)
	{
		String heading = "|" + center(Integer.toString(row), CELL_WIDTH) + "|";
		System.out.print(heading);
	}

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
		// test to see if the particular cell is untouched
		// if it is, then just print a blank space in the table
		// if it has been touched have the cell report its value and print it
	}

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

		// print a row divider
		// then print a blank cell space for the upper right corner
		// then loop through the column letters and print them in a table cell
		// then print another row divider
	}

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

		// need to create three parts to put together and return
		// first part is the spaces that would come before the value (lead)
		// then the value itself
		// then the space after the value (trail)
		return returnstring;
		// replace this
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

	// test code: if you run this class's main instead of your TextExcel, you
	// should see a grid printed that has 3 cells filled in.
	public static void main(String[] args) {    

		// these lines should create a spreadsheet, add 3 cells
		// and print the whole grid.
		Spreadsheet sheet = new Spreadsheet();
		sheet.data[0][0] = new Cell("hi");
		sheet.data[3][6] = new Cell("text");
		sheet.data[7][4] = new Cell("excel");
		sheet.print();
	}
}
