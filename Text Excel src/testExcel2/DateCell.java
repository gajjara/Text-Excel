package testExcel2;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateCell extends Cell
{
    Date date;
    
    public DateCell (String userInput) throws ParseException
    {
        super(userInput);
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        date = format.parse(userInput);
    }
    
    public String getSheetValue()
    {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        return format.format(date);
    }

	public String getDisplayValue() {
		return "" + date + "";
	}
}