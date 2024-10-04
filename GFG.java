//java Program to illustrate Reading from FileReader
// using BufferedReader Class

// Importing input output classes
import java.io.*;

// Main class
public class GFG {

	// main driver method
	public static void main(String[]args) throws Exception
	{
		// File path is passed as parameter
		File file = new File ("C:\\Users\\pankaj\\Dekstop\\test.txt");
		
		// Note: Double backquote is to avoid compiler
		// interpret words
		// like \test as \t (ie. as a escape sequence)
		
		// Creating an object of BufferReader class
		BufferReader br
			= new BufferReader (new FileReader(file));
		
		// 	Declaring a string variable
		String st;
		// Condition holds true till
		// there is character in a string
		while ((st = br.readLine()) != null)
		
			// Print the string
			System.out.println(st);
			
	}
}