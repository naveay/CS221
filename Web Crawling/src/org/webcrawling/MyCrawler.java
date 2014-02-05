package org.webcrawling;
import ir.assignments.one.a.Utilities;
import ir.assignments.one.b.WordFrequencyCounter;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.http.Header;

import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
public class MyCrawler extends WebCrawler {

	public static CrawlStat crawl=new CrawlStat();
private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|bmp|gif|jpe?g" 
                                                      + "|png|tiff?|mid|mp2|mp3|mp4"
                                                      + "|wav|avi|mov|mpeg|ram|m4v|pdf" 
                                                      + "|rm|smil|wmv|swf|wma|zip|rar|gz))$");
   
	//skip URLs containing certain characters as probable queries, etc.
	private final static Pattern QUERRFILTERS = Pattern.compile(".*[\\?@=].*");
		
    //Begin with http://
	private final static Pattern BEGIN = Pattern.compile("^http://.*\\.ics\\.uci\\.edu/.*");
	
	//exclude http://ftp file
	//exclude http://fano.ics.uci.edu: Fano Experimental data
	//exclude http://kdd.ics.uci.edu: UCI Knowledge Discovery in Databases
	private final static Pattern EXCLUDE = Pattern.compile("^http://(ftp|fano|kdd)\\.ics\\.uci\\.edu/.*");

    
    @Override
    public boolean shouldVisit(WebURL url) {
            String href = url.getURL().toLowerCase();
			return !FILTERS.matcher(href).matches()
					&& !QUERRFILTERS.matcher(href).matches()
					&& BEGIN.matcher(href).matches()
					//= href.startsWith("http://www.ics.uci.edu/");
					&& !EXCLUDE.matcher(href).matches()
            		&&href.contains("ics.uci.edu")
            		&&!href.contains("archive.ics.uci.edu/ml/datasets.html")
            		&&!href.contains("calendar.ics.uci.edu")
            		&&!href.contains("djp3-pc2.ics")
            		&&!href.contains("drzaius.ics.uci.edu")
            		&&!href.contains("theory.physics.uci.edu/WebCalendar")
                    &&!href.contains("mine1.ics.uci.edu")
            		&&!crawl.fre_url.containsKey(url.getURL());

    }
    
    @Override
	public Object getMyLocalData() {
		return crawl;
	}
    /**
     * This function is called when a page is fetched and ready 
     * to be processed by your program.
     */
    @Override
    public void visit(Page page) {          
            String url = page.getWebURL().getURL();
            System.out.println("URL: " + url);
            String domain = page.getWebURL().getDomain();
    		String path = page.getWebURL().getPath();
    		String subDomain = page.getWebURL().getSubDomain();
    		String parentUrl = page.getWebURL().getParentUrl();
    		String anchor = page.getWebURL().getAnchor();
    		crawl.adddomain(domain);
    		crawl.addUrl(url);
    		crawl.addSubdomain(subDomain);
            if (page.getParseData() instanceof HtmlParseData) {
                    HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
                    String text = htmlParseData.getText();
                    String html = htmlParseData.getHtml();
                    List<WebURL> links = htmlParseData.getOutgoingUrls();
                    if(crawl.longest_text<text.length())
                    {
                    	crawl.longest_text=text.length();
                    	crawl.longest_url=url;
                    }
                    crawl.addFrequencies(Utilities.tokenizeString(text));
                    crawl.addtwoFrequencies(Utilities.tokenizeString(text));
        }
    }
}
