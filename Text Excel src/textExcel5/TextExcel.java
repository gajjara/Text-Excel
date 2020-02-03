package textExcel5;

import java.util.Scanner;

/* TextExcel is the main entry point for a console-based spreadsheet
 * program. It supports commands to create, display, modify, and delete 
 * various cells from the spreadsheet. 
 */
public class TextExcel 
{

	public static void main(String[] args)
	{
		// create the spreadsheet and scanner objects we'll use throughout
		Spreadsheet sheet = new Spreadsheet();
		Scanner console = new Scanner(System.in);

		System.out.println("Welcome to Text Excel!");

//______________________________________________________________________________
//SCRIPT IS ENTERED HERE, WORKING CODE COMMENTED OUT
		
		inputScript(console, sheet);
				
//TODO: CHANGE: WORKING CODE CHANGED HERE
/**		
 * WORKING CODE
		String command = getCommand(console);
		while (!command.equals("quit"))
		{
			try
			{	
				handleCommand(command, sheet);
			}
			catch (Exception ex)
			{
				// if anything goes wrong anywhere in the handling of this command,
				// the code there can throw an exception. we'll catch it here and display
				// an error message.
				System.out.println("Invalid command: " + command);
				ex.printStackTrace();
			}

			// get another command
			command = getCommand(console);
		}
		
**/

		System.out.println("Farewell!");
	}

	private static void inputScript(Scanner console, Spreadsheet sheet) {
		String command = ""/*getCommand(console)*/;
		while (!command.equals("quit"))
		{
			try
			{
				//TODO: CHANGED TO PROCESS ALREADY WRITTEN COMMANDS
				String[] commands = {
				"A1 = \"MENU\"",
				"B1 = \"WEDNESDAY\"",
				"C1 = \"AUGUST\"",
				"D1 = \"ONE\"",
				"E1 = \"8:00\"",
				"F1 = \"PM\"",
				"G1 = \"29\"",
				"H1 = \"*C\"",
				"A3 = \"CALENDER\"",
				"B3 = \"TODAY\"",
				"E3 = \"WEATHER\"",
				"F3 = \"TODAY\"",
				"A4 = \"TIME\"",
				"B4 = \"EVENT\"",
				"E4 = \"TIME\"",
				"F4 = \"TEMP\"",
				"G4 = \"%RAIN\"",
				"A5 = \"9AM\"",
				"A6 = \"10AM\"",
				"A7 = \"11AM\"",
				"A8 = \"NOON\"",
				"A9 = \"1PM\"",
				"A10 = \"2PM\"",
				"A11 = \"3PM\"",
				"A12 = \"4PM\"",
				"A13 = \"5PM\"",
				"E5 = \"9AM\"",
				"E6 = \"10AM\"",
				"E7 = \"11AM\"",
				"E8 = \"NOON\"",
				"E9 = \"1PM\"",
				"E10 = \"2PM\"",
				"E11 = \"3PM\"",
				"E12 = \"4PM\"",
				"E13 = \"5PM\"",
				"A14 = \"WEEK\"",
				"B15 = \"MONTH\"",
				"C14 = \"YEAR\"",
				"E14 = \"WEEK\"",
				"F14 = \"2WEEK\"",
				"G14 = \"MONTH\"",
				"A16 = \"TO DO\"",
				"B16 = \"TODAY\"",
				"E16 = \"NEWS\"",
				"F16 = \"OPTIONS\"",
				"A17 = \"OPT1\"",
				"B17 = \"OPT2\"",
				"C17 = \"OPT3\"",
				"E17 = \"NATIONAL\"",
				"E21 = \"WORLD\"",
				"A25 = \"CATEGORY1\"",
				"B25 = \"CATEGORY2\"",
				"C25 = \"CATEGORY3\"",
				"E25 = \"OPTION1\"",
				"F25 = \"OPTION2\"",
				"G25 = \"OPTION3\"",
				"H25 = \"OPTIONS1\"",
				"print",
				};
				// process this command
				for(int i = 0; i < commands.length; i++) {
					command = commands[i];
					handleCommand(command, sheet);
				}
			}
			catch (Exception ex)
			{
				// if anything goes wrong anywhere in the handling of this command,
				// the code there can throw an exception. we'll catch it here and display
				// an error message.
				System.out.println("Invalid command: " + command);
				ex.printStackTrace();
			}

			break;
		}
	}
	
	// prompt the user for a command and return whatever she enters
	@SuppressWarnings("unused")
	private static String getCommand(Scanner s)
	{
		System.out.print("Enter a command: ");
		return s.nextLine();
	}

	// parse the specified command and tell the spreadsheet object what to do
	// with it. any failures should result in an exception being thrown.
	private static void handleCommand(String command, Spreadsheet sheet)
	{
		// ignore empty input
		if (command == null || command.isEmpty())
			return;

		// sort the cell in either ascending or descending order
		if (command.contains("sort")) {			
			int space = command.indexOf(" ");
			int colonlocation = command.indexOf(":");
			String refStart = (space == -1) ? command : command.substring(space + 1, colonlocation); //first cell reference
			String refEnd = (space == -1) ? command : command.substring(colonlocation + 1); //last cell reference

			if(sheet.isCellReference(refStart) && sheet.isCellReference(refEnd)) {
				sheet.sort(sheet, command, refStart, refEnd);
			}

			return;
		}

		// print spreadsheet
		if (command.equals("print"))
		{
			sheet.print();
			return;
		}

		// clear the spreadsheet or referenced cell
		if (command.contains("clear")) 
		{	
			// if no reference clear spreadsheet
			if(command.equals("clear")) 
			{
				sheet.clear();
				return;
			}

			// for the clear command, the cell reference is at the end (ex: clear A1), so this reference
			// is stored in a ref variable 
			int space = command.indexOf(" ");
			String ref = (space == -1) ? command : command.substring(space + 1);

			// if there is a reference clear the cell
			if(sheet.isCellReference(ref)) {
				sheet.clearCell(ref);
				return;
			}
		}

		// for some commands, we need to know the first part of the line (like
		// 'A1' in 'A1 = 3.14'), so pull that out into a firstPart variable.
		int space = command.indexOf(" ");
		String firstPart = (space == -1) ? command : command.substring(0, space);

		if (sheet.isCellReference(firstPart))
		{
			// the whole command is just a cell reference, so display it.
			if (firstPart.equals(command))
			{
				sheet.displayCell(command);
				return;
			}

			// the command is a cell reference followed by ' = ' and something
			// else. Pass the 'something else' to the cell factory to create a 
			// new cell, and tell the spreadsheet to put the new cell at the 
			// location in firstPart.     
			if (command.substring(space).startsWith(" = "))
			{
				sheet.setCell(firstPart, CellFactory.create(command.substring(space + 3), sheet));
				return;
			}
		}

		throw new IllegalArgumentException(command + " is not recognized as a valid command.");
	}
}
