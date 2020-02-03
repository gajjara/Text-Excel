package testExcel1;

import java.util.Scanner;

public class TextExcel
{
	
	public static void main(String[] args)
	{
		// create a new spreadsheet first
		Spreadsheet textexcel  = new Spreadsheet();

		// show the welcome message
		System.out.println("Welcome to TextExcel!");

		// set up a scanner to get input
		Scanner console = new Scanner(System.in);

		// the sentinel loop:
		String command = "";
		System.out.print("Enter a command: ");
		command = console.next();
		// get a command - you might want to make this a method
		//    that shows a prompt and returns the string the user entered

		// while the command does not equal "quit" do the following
		while(!command.equals("quit"))
		{
			// do something with the command - again, you might want to
			//    make this a method
			doSomethingWithCommands(command, textexcel);

			// get another command
			System.out.print("Enter a command: ");
			command = console.next();
		}

		System.out.println("Farewell!");
		console.close();
	}

	// didn't make a method for getting a command here, because it was 
	// easier for me to code the sentinel loop without the method

	// here is where you might define a method for doing something with
	//    the command (you'd need to pass in the command and the sheet)
	public static void doSomethingWithCommands(String command, Spreadsheet excel)
	{
		if(command.equals("print")) {
			excel.print();
		}
		// just one if statement that calls the print method for the spreadsheet
	}
	
}

