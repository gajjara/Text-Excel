package testExcel2;
/**********************************************************
 * Assignment: StringTools
 *
 * Author: Anuj Gajjar
 *
 * Description: This is a class for the text excel project that contains a center and repeatedChar method
 *
 * Academic Integrity: I pledge that this program represents my own work. I
 * received help from no one in designing and debugging
 * my program.
 **********************************************************/

public class StringTools
{
    /**
     * This method centers a String in a field of given width
     * @param s - the String to be centered
     * @param width - the length of the returned string
     * @return the String s centered in a field of spaces width chars wide
     * If the length of s > width, then '>' is placed at the end of the
     * returned String
     */
    public static String center(String s, int width)
    {
    		String returnstring = "";
    		
    		if(s.length() > width) {
    			s = s.substring(0, width + 1) + ">";
    		}
    		else {
    		}
    	
		int space = width - s.length();
		int leftorrightwidth = space/2;

		if(s.length() % 2 == 0) {
			returnstring = repeatedChar(' ', leftorrightwidth) + s + repeatedChar(' ', leftorrightwidth);
		}
		else {
			returnstring = repeatedChar(' ', leftorrightwidth) + s + " " + repeatedChar(' ', leftorrightwidth);
		}
		
		return returnstring;
    }    

    /**
     * This method builds a String of repeated characters
     * @param ch - the character to be repeated
     * @param howMany - the number of times to repeat the character
     * @return the String made by repeating the character ch howMany times
     */
    public static String repeatedChar(char ch, int howMany)
    {
		String result = "";
		for (int i = 0; i < howMany; i++) {
			result = result + ch;
		}
		return result;
    } 

    public static void main(String[] args)
    {
        for (char ch = 'A'; ch < 'A' + 8; ch++)
            System.out.println("|" + center(ch + "", 12) + "|");
        System.out.println("|" + center("Hello", 10) + "|");
        System.out.println("|" + center("Goodbye!", 15) + "|");
        System.out.println("|" + center("Oops, what about a long string?", 12) + "|");
    }
    
}