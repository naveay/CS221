package ir.assignments.one.d;

import ir.assignments.one.a.Frequency;
import ir.assignments.one.a.Utilities;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class PalindromeFrequencyCounter {
	/**
	 * This class should not be instantiated.
	 */
	private PalindromeFrequencyCounter() {}
	
	/**
	 * Takes the input list of words and processes it, returning a list
	 * of {@link Frequency}s.
	 * 
	 * This method expects a list of lowercase alphanumeric strings.
	 * If the input list is null, an empty list is returned.
	 * 
	 * There is one frequency in the output list for every 
	 * unique palindrome found in the original list. The frequency of each palindrome
	 * is equal to the number of times that palindrome occurs in the original list.
	 * 
	 * Palindromes can span sequential words in the input list.
	 * 
	 * The returned list is ordered by decreasing size, with tied palindromes sorted
	 * by frequency and further tied palindromes sorted alphabetically. 
	 * 
	 * The original list is not modified.
	 * 
	 * Example:
	 * 
	 * Given the input list of strings 
	 * ["do", "geese", "see", "god", "abba", "bat", "tab"]
	 * 
	 * The output list of palindromes should be 
	 * ["do geese see god:1", "bat tab:1", "abba:1"]
	 *  
	 * @param words A list of words.
	 * @return A list of palindrome frequencies, ordered by decreasing frequency.
	 */
	private static List<Frequency> computePalindromeFrequencies(ArrayList<String> words) {
		// TODO Write body!
		// You will likely want to create helper methods / classes to help implement this functionality
		List <Frequency> result=new ArrayList<Frequency>();
		Map<String, Integer> map=new HashMap<String, Integer>();
		if(words.size()==0)
			return result;
		int []pr=new int[words.size()+1];
		int []pr_blank=new int[words.size()+1];
		String buffer="";
		String buffer_blank=words.get(0);
		pr_blank[0]=0;
		for(int i=0;i<words.size();i++)
		{
			pr[i]=buffer.length();
			buffer+=words.get(i);
		}
		for(int i=1;i<words.size();i++)
		{
			pr_blank[i]=buffer_blank.length()+1;
			buffer_blank+=" "+words.get(i);
		}
		pr_blank[words.size()]=buffer_blank.length();
		pr[words.size()]=buffer.length();
		int s[][]=new int[buffer.length()][buffer.length()];
		Vector <Vector<Boolean>> tmp = new Vector<Vector<Boolean>>();
		s=calculatepalindrome(buffer,s);
		for(int i=0;i<words.size();i++)
		{
			for(int j=i;j<words.size();j++)
			{
				
				if(s[pr[i]][pr[j+1]-1]==1)
				{
					if(j==words.size()-1)
					{
						String t=buffer_blank.substring(pr_blank[i]);
						if(map.containsKey(t))
						{
							map.put(t, map.get(t)+1);
						}
						else
							map.put(t, 1);
					}
					else
					{
						String t=buffer_blank.substring(pr_blank[i], pr_blank[j+1]-1);
						if(map.containsKey(t))
						{
							map.put(t, map.get(t)+1);
						}
						else
							map.put(t, 1);
					}
				}
			}
		}
		List<Map.Entry<String, Integer>> info=new ArrayList<Map.Entry<String,Integer>>(map.entrySet());
		Collections.sort(info,new Comparator<Map.Entry<String,Integer>>()
				{
					public int compare(Map.Entry<String, Integer> o1,Map.Entry<String,Integer> o2)
					{
						if(o2.getKey().replace(" ", "").length()!=o1.getKey().replace(" ", "").length())
							return o2.getKey().replace(" ", "").length()-o1.getKey().replace(" ", "").length();
						else if(o2.getValue()!=o1.getValue())
						{
							return o2.getValue()-o1.getValue();
						}
						else
						{
							return(o1.getKey().toString().compareTo(o2.getKey()));
						}

				}
		});
		for(int i=0;i<info.size();i++)
		{
			result.add(new Frequency(info.get(i).getKey(),info.get(i).getValue()));
		}
		return result;
	}
	private static int[][] calculatepalindrome(String s,int [][]table)
	{
		int size=s.length();
		table=new int[size][size];
		for(int i=0;i<size;i++)
		{
			for(int j=0;j<size;j++)
				table[i][j]=0;
		}
		
		for(int len=1;len<=size;len++)
		{
			for(int start=0;start<=size-len;start++)
			{
				int end=start+len-1;
				if(start==end)
					table[start][end]=1;
				else if(start+1==end&&s.charAt(start)==s.charAt(end))
					table[start][end]=1;
				else if(table[start+1][end-1]==1&&s.charAt(start)==s.charAt(end))
					table[start][end]=1;
			}
		}
		return table;
	}
	/**
	 * Runs the 2-gram counter. The input should be the path to a text file.
	 * 
	 * @param args The first element should contain the path to a text file.
	 */
	public static void main(String[] args) {
		File file = new File(args[0]);
		ArrayList<String> words = Utilities.tokenizeFile(file);
		List<Frequency> frequencies = computePalindromeFrequencies(words);
		Utilities.printFrequencies(frequencies);
	}
}
