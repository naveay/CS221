package org.webcrawling;
import java.util.*;

import ir.assignments.one.*;
/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import ir.assignments.one.a.Frequency;
import ir.assignments.one.a.Utilities;
import ir.assignments.one.b.WordFrequencyCounter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class CrawlStat {
    private  Map<String, Integer> fre_subdomain=new HashMap<String, Integer>();
    private  Map<String, Integer> fre_domain=new HashMap<String, Integer>();
    private  Map<String, Integer> fre_url=new HashMap<String, Integer>();
    
    public String longest_url="";
    public int hour=0;
    public int minute=0;
    public int longest_text=0;
    public Map<String, Integer> stop_frequencies=new HashMap<String, Integer>();;
    public Map<String, Integer> word_frequencies=new HashMap<String, Integer>();
    public Map<String, Integer> two_frequencies=new HashMap<String, Integer>();
	public int getUrl() {
		return fre_url.size();
	}
	public void time()
	{
		 System.out.println(new Date(System.currentTimeMillis()));
	}
	public void addFrequencies(List<String> words) {
		for(int i=0;i<words.size();i++)
		{
			if(!stop_frequencies.containsKey(words.get(i)))
			{
				if(word_frequencies.containsKey(words.get(i)))
				{
					word_frequencies.put(words.get(i),word_frequencies.get(words.get(i))+1);
				}
				else
				{
					word_frequencies.put(words.get(i),1);
				}
			}
		}
	}
	
	public void addtwoFrequencies(List<String> words) {
		for(int i=0;i<words.size()-1;i++)
		{
			if(!stop_frequencies.containsKey(words.get(i))&&!stop_frequencies.containsKey(words.get(i+1)))
			{
				String gram=words.get(i)+" "+words.get(i+1);
				if(two_frequencies.containsKey(gram))
				{
					two_frequencies.put(gram,two_frequencies.get(gram)+1);
				}
				else
				{
					two_frequencies.put(gram,1);
				}
			}
		}
	}
	
	public void stopFrequencies(List<String> words) {
		for(int i=0;i<words.size();i++)
		{
			if(stop_frequencies.containsKey(words.get(i)))
			{
				stop_frequencies.put(words.get(i),stop_frequencies.get(words.get(i))+1);
			}
			else
			{
				stop_frequencies.put(words.get(i),1);
			}
		}
	}
	public CrawlStat()
	{
	    File file = new File("stop.txt");
		List<String> words = Utilities.tokenizeFile(file);
		stopFrequencies(words);
	}
	public void addUrl(String url) {
		if(fre_url.containsKey(url))
		{
			fre_url.put(url, fre_url.get(url)+1);
		}
		else
			fre_url.put(url, 1);
	}

	public void addSubdomain(String subDomain) {
		if(fre_subdomain.containsKey(subDomain))
		{
			fre_subdomain.put(subDomain, fre_subdomain.get(subDomain)+1);
		}
		else
			fre_subdomain.put(subDomain, 1);
	}
	public void file() throws IOException
	{
		try {
            FileOutputStream out=new FileOutputStream("Subdomains.txt");
            PrintStream p=new PrintStream(out);
            List<Map.Entry<String, Integer>> info=new ArrayList<Map.Entry<String,Integer>>(fre_subdomain.entrySet());
    		Collections.sort(info,new Comparator<Map.Entry<String,Integer>>()
    				{
    					public int compare(Map.Entry<String, Integer> o1,Map.Entry<String,Integer> o2)
    					{
    							return(o1.getKey().toString().compareTo(o2.getKey()));
    				}
    		});
    		for(int i=0;i<info.size();i++)
    		{
    			p.println(info.get(i).getKey()+","+info.get(i).getValue());
    		}
    		out.close();
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
	}
	public void print()
	{
		List<Map.Entry<String, Integer>> info=new ArrayList<Map.Entry<String,Integer>>(word_frequencies.entrySet());
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
		for(int i=0;i<500;i++)
		{
			System.out.println(info.get(i).getKey()+" : "+info.get(i).getValue());
		}
	}
	public void print_two()
	{
		List<Map.Entry<String, Integer>> info=new ArrayList<Map.Entry<String,Integer>>(two_frequencies.entrySet());
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
		for(int i=0;i<20;i++)
		{
			System.out.println(info.get(i).getKey()+" : "+info.get(i).getValue());
		}
	}
	public int getsubDomain() {
		return fre_subdomain.size();
	}
	public void adddomain(String Domain) {
		fre_domain.put(Domain, 1);
	}

	public int getDomain() {
		return fre_domain.size();
	}
}

