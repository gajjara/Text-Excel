package testExcel2;

import java.util.Scanner;

public class TextExcel
{
	public static void main(String[] args)
	{
		Spreadsheet textexcel = new Spreadsheet();
		System.out.println("Welcome to TextExcel!");
		Scanner console = new Scanner(System.in);

		String command = "";
		System.out.print("Enter a command: ");
		command = console.nextLine();
		
		while(!command.equals("quit"))
		{
			doCommand(command, textexcel);
			
			System.out.print("Enter a command: ");
			command = console.nextLine();
		}
		
		System.out.println("Farewell!");
		console.close();
	}

	
	private static void doCommand(String input, Spreadsheet sheet)
	{		
		if(input.contains("=")) {
			sheet.storeValueToGiveBackToUser(input);
			sheet.setValueOfCell(input, sheet);
		}
		
		else if(input.equals("print")) {
			sheet.print();
		}
		
		else if (sheet.cellReferenceIsCorrect(input) == true) {
			sheet.printOutValueOfReference(input, sheet);
		}
	}
}

