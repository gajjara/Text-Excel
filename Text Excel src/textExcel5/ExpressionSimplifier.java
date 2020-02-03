package textExcel5;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * ExpressionSimplifier has a static simplify method that can take a 
 * string with a mathematical expression in it and reduce it down to
 * a single term. For example, 5 * 4 + 3 * 2 can be simplified to 26.
 */
public class ExpressionSimplifier 
{
	/**
	 * The simplify method takes an expression like 3 + 2 as a parameter
	 * and returns a simplified version of it.
	 * 
	 * @param expression - a valid math expression like 9 / 2
	 * 
	 * @return - a double representing the result of evaluating the
	 * expression
	 */
	public static double simplify(String expression, Spreadsheet sheet)
	{
		// turn the expression into an array list ex: {1, +, 2, +, 5, * 8}
		ArrayList<String> tokens = convertToList(expression);

		replaceCellReferencesWithValue(tokens, sheet);
		String cellReferenceStart = "";
		String cellReferenceEnd = "";
		replaceSumAvgReferencesWithValues(tokens, sheet, cellReferenceStart, cellReferenceEnd);

		while (tokens.size() > 1)
		{
			// finds the index of the next operator based off order of operations
			int operatorIndex = findIndexOfNextOperator(tokens);

			// calculates expression based of the index of the operator
			String leftOperand = tokens.get(operatorIndex - 1);
			String operator = tokens.get(operatorIndex);
			String rightOperand = tokens.get(operatorIndex + 1);
			String result = calculate(leftOperand, operator, rightOperand);

			// removes the expressions calculated with, and inputs the result of that expression 
			replaceInList(tokens, operatorIndex - 1, 3, result);
		}

		return Double.parseDouble(tokens.get(0));
	}

	/**
	 * Given a string, break it up by spaces, so that something like
	 * "a + b" becomes a 3-element list: a, +, b
	 * 
	 * There are a couple of different ways to do this. You can use 
	 * the String indexOf and substring methods to find each space
	 * and pull out the parts between them, or you can use the String
	 * split method to get an array of Strings and convert the array
	 * to an ArrayList.
	 * 
	 * @param str - the String to break up into tokens
	 * 
	 * @return - an ArrayList containing the parts of the input string
	 */
	private static ArrayList<String> convertToList(String str)
	{
		// creates an array list by creating each new token based off the " " in between expression terms
		ArrayList<String> tokens = new ArrayList<String>(Arrays.asList(str.split(" ")));
		return tokens;
	}

	/**
	 * Given a series of tokens like a, +, b, *, and c, find the index
	 * of the operator that should be evaluated next. In the example
	 * here, the next operator is the '*' at index 3.
	 *  
	 * @param tokens - an ArrayList of numbers and math operators that
	 * form a valid math expression.
	 * 
	 * @return - the index in the ArrayList of the next operator that 
	 * should be evaluated.
	 */
	private static int findIndexOfNextOperator(ArrayList<String> tokens)
	{		
		int returnvalue = -1;

		for(int i = 0; i < tokens.size(); i++) {
			// checks if index contains * or /
			if(tokens.get(i).equals("*") || tokens.get(i).equals("/")) {
				returnvalue = i; 
				break; //if condition is true then loop ends
			}
			// checks if index contains + or -
			else if(tokens.get(i).equals("+") || tokens.get(i).equals("-")) {
				returnvalue = i;
				break;
			}
		}

		// if there isn't an index value, then an exception is thrown
		if(returnvalue == -1) {
			throw new InvalidParameterException("No operator found in token list");
		}
		// else return the index value
		else {
			return returnvalue;
		}
	}

	/**
	 * Given left and right operands and an operator for in between them, calculate
	 * the result of the expression and return it.
	 * 
	 * @param left - the left operand
	 * @param op - the operator (*, /, +, or -)
	 * @param right - the right operand
	 * 
	 * @return - the result of evaluating the expression formed by left, op, and right
	 */
	private static String calculate(String left, String op, String right)
	{
		double leftnum = Double.parseDouble(left);
		double rightnum = Double.parseDouble(right);

		if(op.equals("*")) {
			return Double.toString((leftnum * rightnum));
		}
		else if(op.equals("/")) {
			return Double.toString((leftnum / rightnum));
		}
		else if(op.equals("+")) {
			return Double.toString((leftnum + rightnum));
		}
		else if(op.equals("-")) {
			return Double.toString((leftnum - rightnum));
		}

		throw new InvalidParameterException("'" + op + "' is not a valid operator");
	}

	/**
	 * Replace some items in an ArrayList with a replacement value
	 * 
	 * @param items - the ArrayList of items to modify
	 * @param firstIndex - the index in the ArrayList of the first item to remove
	 * @param howManyItems - how many items to remove 
	 * @param newValue - the value to replace the removed item(s) with
	 */
	private static void replaceInList(ArrayList<String> items, int firstIndex, int howManyItems, String newValue)
	{
		// remove x amount of items starting from the first index
		for(int i = 0; i < howManyItems; i++) {
			items.remove(firstIndex);
		}

		// add the new value in the first index
		items.add(firstIndex, newValue);
	}

	/**
	 * Replace the items in an ArrayList that are cell references to the numeric value contained in the cell reference
	 * 
	 * @param items - the ArrayList of items to modify
	 * @param sheet - the Spreadsheet used to get the values from the cell references
	 */
	private static void replaceCellReferencesWithValue(ArrayList<String> items, Spreadsheet sheet) { 

		for(int i = 0; i < items.size(); i++) {
			String token = items.get(i);
			Double numericvalue = -1.0;

			if(sheet.isCellReference(token)) { //check if token is a cell reference
				int col = sheet.getCol(token); //get column value
				int row = sheet.getRow(token); //get row value
				Cell touse = sheet.getCell()[row][col]; //get the cell

				if(touse instanceof Numeric) { // get the numeric value of the cell
					numericvalue = ((Numeric) sheet.getCell()[row][col]).getNumericValue();
				}

				String value = Double.toString(numericvalue); //set the token value to the numeric value
				items.set(i, value); //put that token into the list
			}

			else {
			}
		}
	}

	/**
	 * Replace the items in an ArrayList that are sum and average references with the value of the sum and avg
	 * 
	 * @param items - the ArrayList of items to modify
	 * @param sheet - the Spreadsheet used to get the values of cell references
	 */
	private static void replaceSumAvgReferencesWithValues(ArrayList<String> items, Spreadsheet sheet, String cellReferenceStart, String cellReferenceEnd) {
		for(int i = 0; i < items.size(); i++) { 
			if(items.get(i).equals("sum")) {
				String cellreferences = items.get(i + 1); //get the cell references

				cellReferenceStart = cellreferences.substring(0 ,cellreferences.indexOf(":")); //set the starting cell reference
				cellReferenceEnd = cellreferences.substring(cellreferences.indexOf(":") + 1); //set the ending cell reference

				String sum = calculateSum (sheet, cellReferenceStart, cellReferenceEnd); //calculate the sum value

				items.remove(i); //remove the sum reference
				items.set(i, sum); //set the index to the calculated sum value
			}
			else if(items.get(i).equals("avg")) {
				String cellreferences = items.get(i + 1); //get the cell references

				cellReferenceStart = cellreferences.substring(0 ,cellreferences.indexOf(":")); //set the starting cell reference
				cellReferenceEnd = cellreferences.substring(cellreferences.indexOf(":") + 1); //set the ending cell reference

				String avg = calculateAvg (sheet, cellReferenceStart, cellReferenceEnd); //calculate the average value

				items.remove(i); //remove the avg reference
				items.set(i, avg); //set the index to the calculated avg value
			}
			else {
			}
		}
	}

	/**
	 * Calculates the summation value of the values within and containing the referenced cells
	 *
	 * @param sheet - the Spreadsheet used to get and change the values of cell references
	 * @param cellReferenceStart - the cell reference that indicates the starting row and column
	 * @param cellReferenceEnd - the cell reference that indicates the ending row and column
	 * 
	 * @return the sum value of the values located within the cell references in its string form
	 */
	private static String calculateSum (Spreadsheet sheet, String cellReferenceStart, String cellReferenceEnd) {
		double sum = 0;

		// sets the starting and ending columns and rows
		int startRow = sheet.getRow(cellReferenceStart);
		int endRow = sheet.getRow(cellReferenceEnd);
		int startCol = sheet.getCol(cellReferenceStart);
		int endCol = sheet.getCol(cellReferenceEnd);

		// nested for loop to go through rows and columns to find sum
		for(int i = startRow; i <= endRow; i++) {
			for(int j = startCol; j <= endCol; j++) {
				if(sheet.getCell()[i][j] == null) { //if a cell value is null, then set that cell value to zero
					Cell cell = new NumberCell("0");
					sheet.setCell(i, j, cell);
				}
				sum += ((Numeric) sheet.getCell()[i][j]).getNumericValue();
			}
		}

		return Double.toString(sum);
	}

	/**
	 * Calculates the average value of the values within and containing the referenced cells
	 *
	 * @param sheet - the Spreadsheet used to get and change the values of cell references
	 * @param cellReferenceStart - the cell reference that indicates the starting row and column
	 * @param cellReferenceEnd - the cell reference that indicates the ending row and column
	 * 
	 * @return the average value of the values located within the cell references in its string form
	 */
	private static String calculateAvg (Spreadsheet sheet, String cellReferenceStart, String cellReferenceEnd) {
		double avg = 0;
		int numberofterms = 0;

		// sets the starting and ending columns and rows
		int startRow = sheet.getRow(cellReferenceStart);
		int endRow = sheet.getRow(cellReferenceEnd);
		int startCol = sheet.getCol(cellReferenceStart);
		int endCol = sheet.getCol(cellReferenceEnd);

		//find sum of referenced values
		double sum = Double.parseDouble(calculateSum(sheet , cellReferenceStart, cellReferenceEnd));

		//nested for loop to run through the the rows and columns to find the number of terms
		for(int i = startRow; i <= endRow; i++) {
			for(int j = startCol; j <= endCol; j++) {
				numberofterms += 1;
			}
		}

		//divide the sum by the number of terms to find the average
		avg = sum/numberofterms;

		return Double.toString(avg);
	}

	/**
	 * Some test code.
	 */
	public static void main(String[] args) 
	{
		//lines set up values to be used to test sum and average calculations
		Spreadsheet sheet = new Spreadsheet();

		//test lines
		System.out.println(simplify(("5 + sum A1:B3"), sheet)); //should equal 20
		System.out.println(simplify(("5 + avg A1:A5"), sheet)); //should equal 8
	}
}