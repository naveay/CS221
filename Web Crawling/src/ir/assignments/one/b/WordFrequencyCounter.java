package ir.assignments.one.b;

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
 * Counts the total number of words and their frequencies in a text file.
 */
public final class WordFrequencyCounter {
	/**
	 * This class should not be instantiated.
	 */
	private WordFrequencyCounter() {}
	
	/**
	 * Takes the input list of words and processes it, returning a list
	 * of {@link Frequency}s.
	 * 
	 * This method expects a list of lowercase alphanumeric strings.
	 * If the input list is null, an empty list is returned.
	 * 
	 * There is one frequency in the output list for every 
	 * unique word in the original list. The frequency of each word
	 * is equal to the number of times that word occurs in the original list. 
	 * 
	 * The returned list is ordered by decreasing frequency, with tied words sorted
	 * alphabetically.
	 * 
	 * The original list is not modified.
	 * 
	 * Example:
	 * 
	 * Given the input list of strings 
	 * ["this", "sentence", "repeats", "the", "word", "sentence"]
	 * 
	 * The output list of frequencies should be 
	 * ["sentence:2", "the:1", "this:1", "repeats:1",  "word:1"]
	 *  
	 * @param words A list of words.
	 * @return A list of word frequencies, ordered by decreasing frequency.
	 */
	public static List<Frequency> computeWordFrequencies(List<String> words) {
		Map<String, Integer> map=new HashMap<String, Integer>();
		List<Frequency> result=new ArrayList<Frequency>();
		if(words.size()==0)
			return result;
		for(int i=0;i<words.size();i++)
		{
			if(map.containsKey(words.get(i)))
			{
				map.put(words.get(i),map.get(words.get(i))+1);
			}
			else
			{
				map.put(words.get(i),1);
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
	 * Runs the word frequency counter. The input should be the path to a text file.
	 * 
	 * @param args The first element should contain the path to a text file.
	 */
	public static void main(String[] args) {
		File file = new File(args[0]);
		List<String> words = Utilities.tokenizeFile(file);
		List<Frequency> frequencies = computeWordFrequencies(words);
		Utilities.printFrequencies(frequencies);
	}
}
