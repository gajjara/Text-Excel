package textExcel3;

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
	public static double simplify(String expression)
	{
		// turn the expression into an array list ex: {1, +, 2, +, 5, * 8}
		ArrayList<String> tokens = convertToList(expression); 

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
	 * Some test code. Feel free to add more expressions if you want to make sure
	 * your code is well-tested.	
	 */
	public static void main(String[] args) 
	{
		System.out.println("1 == " + simplify("1"));
		System.out.println("3 + 5 == " + simplify("3 + 5"));
		System.out.println("4 - 7 == " + simplify("4 - 7"));
		System.out.println("5 * 4 + 3 * 2 == " + simplify("5 * 4 + 3 * 2"));
		System.out.println("10 - 9 / 3 == " + simplify("10 - 9 / 3"));
		System.out.println("5 * 4 + 3 * 2 + 4 * 4 == " + simplify("5 * 4 + 3 * 2 + 4 * 4"));
		System.out.println("5 * 4 + 3 * 2 - 16 / 4 == " + simplify("5 * 4 + 3 * 2 - 16 / 4"));
		System.out.println("5 * 4 + 3 / 2 - 16 / 4 == " + simplify("5 * 4 + 3 / 2 - 16 / 4"));
		System.out.println("11 / 8 + 3 / 2 - 3 / 4 == " + simplify("11 / 8 + 3 / 2 - 3 / 4"));
	}
}