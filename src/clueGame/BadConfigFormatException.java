package clueGame;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class BadConfigFormatException extends Exception 
{
	public BadConfigFormatException(String message)
	{
		super("Problem with: " + message);
		PrintWriter writer;
		try 
		{
			writer = new PrintWriter("ExceptionLog.txt");
			writer.println("Problem with: " + message);
			writer.close();
		} 
		catch (FileNotFoundException e) 
		{
			System.out.println("Error writing to log.");
		}
	}
}
