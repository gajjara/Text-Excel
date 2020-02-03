package textExcel5;

/* Spreadsheet stores a 2d array of Cell objects and is able to 
 * modify the array and display it by printing to System.out.
 */
public class Spreadsheet 
{
	private final static int COLS = 10; //TODO CHANGE: Value originally 7
	private final static int ROWS = 25; //TODO CHANGE: Value originally 12
	private final static int CELL_WIDTH = 12;

	// store the cells in a 2D array
	private Cell[][] data;

	// construct a new spreadsheet. each cell in 'data' will be
	// null initially, representing an empty cell.
	public Spreadsheet()
	{
		data = new Cell[ROWS][COLS];
	}

	// print the spreadsheet to system.out
	public void print()
	{
		printHorizontalLine();
		printColumnHeaders();
		printHorizontalLine();

		for (int r = 0; r < ROWS; r++)
		{
			printRow(r);
			printHorizontalLine();
		}
	}

	// check to see if 'ref' is a valid cell reference, like A3 or G10. if
	// ref can't be parsed as a column and row, or if it is not in the 
	// appropriate range for this spreadsheet, return false.
	public boolean isCellReference(String ref)
	{
		if (ref == null || ref.length() < 2 || ref.length() > 3)
			return false;

		if (ref.charAt(0) < 'A' || ref.charAt(0) > 'A' + COLS)
			return false;

		int row = Integer.parseInt(ref.substring(1));
		if (row < 1 || row > ROWS)
			return false;

		return true;
	}

	// display the value of a single cell, represented by ref, by printing
	// it to system.out.
	public void displayCell(String ref)
	{
		Cell c = data[getRow(ref)][getCol(ref)];
		String value = (c == null) ? "<empty>" : c.getOriginalValue(); // "ternary operator" - bing it :)

		System.out.println(ref + " = " + value);
	}

	// store a cell at the specified location in the grid, replacing whatever
	// might be there already.
	public void setCell(String ref, Cell cell)
	{							
		data[getRow(ref)][getCol(ref)] = cell;
	}

	//is a another setter method that takes row and column integers, rather than the whole cell reference
	public void setCell(int row, int col, Cell cell) {
		data[row][col] = cell;
	}

	// is a getter method to get the value of a cell without having to call the data variable
	public Cell[][] getCell() {
		return data;
	}

	// given a string that is supposed to be a reference to a cell, parse the
	// row index from it (i.e. F4 will return 3 because 3 is the index of the
	// 4th row in 'data').
	public int getRow(String ref)
	{
		if (!isCellReference(ref))
			throw new IllegalArgumentException(ref + " is not a valid cell reference");

		return Integer.parseInt(ref.substring(1)) - 1;
	}

	// given a string that is supposed to be a reference to a column, parse
	// the column index from it (e.g. C7 will return 2, since C is the 3rd column
	// and its zero-based index in data would therefore be 2).
	public int getCol(String ref)
	{
		if (!isCellReference(ref))
			throw new IllegalArgumentException(ref + " is not a valid cell reference");

		return ref.charAt(0) - 'A';
	}

	// print one line of +------------+--- etc.
	private void printHorizontalLine()
	{
		for (int col = 0; col < COLS + 1; col++)
		{
			System.out.print("+");
			for (int ch = 0; ch < CELL_WIDTH; ch++)
				System.out.print("-");
		}
		System.out.println("+");
	}

	// print the column header row
	private void printColumnHeaders()
	{
		System.out.print("|" + center("", CELL_WIDTH)); // blank cell at top left

		for (int col = 0; col < COLS; col++)
		{
			System.out.print("|");
			System.out.print(center((char)(col + 'A') + "", CELL_WIDTH));
		}
		System.out.println("|");
	}
	
	//TODO: EXPAND IDEA
	@SuppressWarnings("unused")
	private void printColumnHeadersIdea(int[] indices, int[] CELL_WIDTH_VARIED) {		
		System.out.print("|" + center("", CELL_WIDTH)); //TODO: ASSUMPTION: Assuming that first cell isnt changed

		//MAIN FOR LOOP//
		for(int i = 0; i < indices.length; i++) {
			int indexToChange = indices[i];

			for(int col = 0; col < indexToChange; col++) {
				System.out.print("|");
				System.out.print(center((char)(col + 'A') + "", CELL_WIDTH));
			}
			System.out.print("|");
			System.out.print(center((char)(indexToChange + 'A') + "", CELL_WIDTH_VARIED[i]));

			for(int col = indexToChange + 1; col < COLS; col++) {
				System.out.print("|");
				System.out.print(center((char)(col + 'A') + "", CELL_WIDTH));
			}


		}
	}
	
	//TODO WRITE
	@SuppressWarnings("unused")
	private void printColumnHeadersFinalIdea(int[] indices, int CELL_WIDTH_VARIED) {
		System.out.print("|" + center("", CELL_WIDTH)); //TODO: ASSUMPTION: Assuming that first cell isnt changed
		
		for(int i = 0; i < indices.length; i++) {
			
			
			
		}
	}

	// print the specified row of cells, including their left and right borders
	private void printRow(int row)
	{
		System.out.print("|" + center(row + 1 + "", CELL_WIDTH)); // the header column

		for (int col = 0; col < COLS; col++)
		{
			String value = (data[row][col] == null) ? "" : getCell()[row][col].getDisplayValue();
			System.out.print("|" + center(value, CELL_WIDTH));
		}

		System.out.println("|");
	}

	// given a string 'text', truncate or pad it to make it fit exactly in  
	// 'width' characters
	private String center(String text, int width)
	{
		if (text.length() > width)
			return text.substring(0, width - 1) + ">";

		String centered = "";
		int leftSpaces = (width - text.length()) / 2;
		for (int c = 0; c < leftSpaces; c++)
			centered += " ";
		centered += text;
		for (int c = centered.length(); c < width; c++)
			centered += " ";

		return centered;
	}

	// clears the entire spreadsheet
	public void clear() 
	{
		for(int i = 0; i < ROWS; i++) {
			for(int j = 0; j < COLS; j++) {
				data[i][j] = null;
			}
		}
	}

	// clears a single cell
	public void clearCell(String ref) 
	{
		int row = getRow(ref);
		int col = getCol(ref);
		data[row][col] = null;
	}

	public void sort(Spreadsheet sheet, String command, String refStart, String refEnd) {
		//get cell references
		int startRow = sheet.getRow(refStart);
		int endRow = sheet.getRow(refEnd);
		int startCol = sheet.getCol(refStart);
		int endCol = sheet.getCol(refEnd);

		//single array of cells
		Cell[] values = new Cell[(endRow - startRow + 1) * (endCol - startCol + 1)];

		//put values into single array
		int k = 0; //counter
		for(int i = startRow; i <= endRow; i++) {
			for(int j = startCol; j <= endCol; j++) {
				values[k] = sheet.getCell()[i][j];
				k++;
			}
		}

		//sort the values
		MergeSortCells.mergeSort(values);

		if(command.contains("sorta")) {  //put values back into sheet in ascending order
			int c = 0; //counter
			for(int i = startRow; i <= endRow; i++) {
				for(int j = startCol; j <= endCol; j++) {
					sheet.setCell(i, j, values[c]);
					c++;
				}
			}
		}
		else { //put values back into sheet in descending order
			int c = values.length - 1; //counter
			for(int i = startRow; i <= endRow; i++) {
				for(int j = startCol; j <= endCol; j++) {
					sheet.setCell(i, j, values[c]);
					c--;
				}
			}
		}
	}

}
