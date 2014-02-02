package ir.assignments.one.a;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A collection of utility methods for text processing.
 */
public class Utilities {
	/**
	 * Reads the input text file and splits it into alphanumeric tokens.
	 * Returns an ArrayList of these tokens, ordered according to their
	 * occurrence in the original text file.
	 * 
	 * Non-alphanumeric characters delineate tokens, and are discarded.
	 *
	 * Words are also normalized to lower case. 
	 * 
	 * Example:
	 * 
	 * Given this input string
	 * "An input string, this is! (or is it?)"
	 * 
	 * The output list of strings should be
	 * ["an", "input", "string", "this", "is", "or", "is", "it"]
	 * 
	 * @param input The file to read in and tokenize.
	 * @return The list of tokens (words) from the input file, ordered by occurrence.
	 * @throws FileNotFoundException 
	 */
	public static ArrayList<String> tokenizeFile(File input) {
		try
		{
			if(!input.exists()||input.isDirectory())
			{
				throw new IOException();
			}
			BufferedReader buffer=new BufferedReader(new FileReader(input));
			ArrayList<String> list=new ArrayList<String>();
			String line=buffer.readLine();
			String result=new String();
			while(line!=null)
			{
				result+=line+" ";
				line=buffer.readLine();
			}
			result=result.toLowerCase();
			String [] split=result.split("[^a-z0-9']+");
			for(int i=0;i<split.length;i++)
			{
				if(!split[i].equals(""))
					list.add(split[i]);
			}
			buffer.close();
			return list;
		}
		catch(IOException o)
		{
			System.out.print("File not found.\n");
		}
		
		return new ArrayList<String>();
	}
	
	public static ArrayList<String> tokenizeString(String input) {
			ArrayList<String> list=new ArrayList<String>();
			String result=input;
			result=result.toLowerCase();
			String [] split=result.split("[^a-z0-9']+");
			for(int i=0;i<split.length;i++)
			{
				if(!split[i].equals(""))
					list.add(split[i]);
			}
			return list;
	}
	/**
	 * Takes a list of {@link Frequency}s and prints it to standard out. It also
	 * prints out the total number of items, and the total number of unique items.
	 * 
	 * Example one:
	 * 
	 * Given the input list of word frequencies
	 * ["sentence:2", "the:1", "this:1", "repeats:1",  "word:1"]
	 * 
	 * The following should be printed to standard out
	 * 
	 * Total item count: 6
	 * Unique item count: 5
	 * 
	 * sentence	2
	 * the		1
	 * this		1
	 * repeats	1
	 * word		1
	 * 
	 * 
	 * Example two:
	 * 
	 * Given the input list of 2-gram frequencies
	 * ["you think:2", "how you:1", "know how:1", "think you:1", "you know:1"]
	 * 
	 * The following should be printed to standard out
	 * 
	 * Total 2-gram count: 6
	 * Unique 2-gram count: 5
	 * 
	 * you think	2
	 * how you		1
	 * know how		1
	 * think you	1
	 * you know		1
	 * 
	 * @param frequencies A list of frequencies.
	 */
	public static void printFrequencies(List<Frequency> frequencies) {
		String tmp[]=new String[1];
		if(frequencies.size()!=0)
		{
			tmp=frequencies.get(0).getText().split(" ");
		}
		int total=0;
		int unique=0;
		for(int i=0;i<frequencies.size();i++)
		{
			total+=frequencies.get(i).getFrequency();
			unique++;
		}
		if(tmp.length==1)
		{
			System.out.printf("Total item count: "+total+"\n");
			System.out.printf("Unique item count: "+unique+"\n\n");
		}
		else
		{
			System.out.printf("Total 2-gram count: "+total+"\n");
			System.out.printf("Unique 2-gram count: "+unique+"\n\n");
		}
		for(int i=0;i<frequencies.size();i++)
		{
			System.out.printf(frequencies.get(i).getText()+"\t\t"+frequencies.get(i).getFrequency()+"\n");
		}
	}
}
