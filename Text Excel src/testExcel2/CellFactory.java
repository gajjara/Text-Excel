package testExcel2;

import java.text.SimpleDateFormat;

public class CellFactory
{
	/**
	 * Takes the userValue supplied and figures out what kind of Cell to make
	 * @param userValue - part of the command on the right side of the equal sign
	 * @return the appropriate type of Cell based on the userValue
	 */
	public static Cell makeCell(String value)
	{	
		char quotechar = '"';
		String quote = Character.toString(quotechar);
		
		if (value.contains(quote))
		{
			String valuetouse = value.substring(1, value.length() - 1);
			return new StringCell(valuetouse);
		}

		try 
		{
			@SuppressWarnings("unused")
			double test = Double.parseDouble(value);
			return new NumberCell(value);
		}
		catch (Exception e) {
		}

		try 
		{
			SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
			format.parse(value);
			return new DateCell(value);
		}
		catch (Exception e) {
		}

		throw new IllegalArgumentException("Not valid for any Cell type: " + value);
	}
}