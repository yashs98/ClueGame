package clueGame;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * 
 * @author Yash Sinha and Daniel Thorne and Calvin Mak
 * This is an exception class called BadConfigFormatException where it is thrown when the board is not created properly.
 * It has two constructors that writes the error message to a file called logfile.txt
 *
 */
public class BadConfigFormatException extends Exception {

	/**
	 * 
	 * @throws FileNotFoundException
	 * This is a default constructor that writes the error to logfile.txt
	 */
	public BadConfigFormatException()  {
		super();
		
		try {
			PrintWriter write = new PrintWriter("logfile.txt");
			write.println("Error: Board not configured properly");
		
			write.close();
		
		} catch(Exception e) {
			e.getMessage();
		}
		
		
	}
	/**
	 * 
	 * @param message
	 * @throws FileNotFoundException
	 * 
	 * This constructor takes in a string message to write a more specific error message. 
	 */
	public BadConfigFormatException(String message)  {
		super();
		try {
			PrintWriter anotherWrite = new PrintWriter("logfile.txt");
			anotherWrite.println("Error: " + message + " detected!");
		
			anotherWrite.close();
		
		} catch (Exception e ) {
			e.getMessage();
		}
		
		
	}
	
	
	
}
