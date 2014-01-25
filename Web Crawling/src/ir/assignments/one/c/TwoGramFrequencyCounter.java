package ir.assignments.one.c;

import ir.assignments.one.a.Frequency;
import ir.assignments.one.a.Utilities;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Count the total number of 2-grams and their frequencies in a text file.
 */
public final class TwoGramFrequencyCounter {
	/**
	 * This class should not be instantiated.
	 */
	private TwoGramFrequencyCounter() {}
	
	/**
	 * Takes the input list of words and processes it, returning a list
	 * of {@link Frequency}s.
	 * 
	 * This method expects a list of lowercase alphanumeric strings.
	 * If the input list is null, an empty list is returned.
	 * 
	 * There is one frequency in the output list for every 
	 * unique 2-gram in the original list. The frequency of each 2-grams
	 * is equal to the number of times that two-gram occurs in the original list. 
	 * 
	 * The returned list is ordered by decreasing frequency, with tied 2-grams sorted
	 * alphabetically. 
	 * 
	 * 
	 * 
	 * Example:
	 * 
	 * Given the input list of strings 
	 * ["you", "think", "you", "know", "how", "you", "think"]
	 * 
	 * The output list of 2-gram frequencies should be 
	 * ["you think:2", "how you:1", "know how:1", "think you:1", "you know:1"]
	 *  
	 * @param words A list of words.
	 * @return A list of two gram frequencies, ordered by decreasing frequency.
	 */
	private static List<Frequency> computeTwoGramFrequencies(ArrayList<String> words) {
		List<String> gram=new ArrayList<String>();
		for(int i=0;i<words.size()-1;i++)
		{
			gram.add(words.get(i)+" "+words.get(i+1));
		}
		
		Map<String, Integer> map=new HashMap<String, Integer>();
		List<Frequency> result=new ArrayList<Frequency>();
		if(gram.size()==0)
			return result;
		for(int i=0;i<gram.size();i++)
		{
			if(map.containsKey(gram.get(i)))
			{
				map.put(gram.get(i),map.get(gram.get(i))+1);
			}
			else
			{
				map.put(gram.get(i),1);
			}
		}
		List<Map.Entry<String, Integer>> info=new ArrayList<Map.Entry<String,Integer>>(map.entrySet());
		Collections.sort(info,new Comparator<Map.Entry<String,Integer>>()
				{
					public int compare(Map.Entry<String, Integer> o1,Map.Entry<String,Integer> o2)
					{
						if(o2.getValue()!=o1.getValue())
							return (o2.getValue()-o1.getValue());
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
	
	/**
	 * Runs the 2-gram counter. The input should be the path to a text file.
	 * 
	 * @param args The first element should contain the path to a text file.
	 */
	public static void main(String[] args) {
		File file = new File(args[0]);
		ArrayList<String> words = Utilities.tokenizeFile(file);
		List<Frequency> frequencies = computeTwoGramFrequencies(words);
		Utilities.printFrequencies(frequencies);
	}
}
