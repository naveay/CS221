package org.webcrawling;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.http.Header;

import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;

public class MyCrawler extends WebCrawler {

    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|bmp|gif|jpe?g" 
                                                      + "|png|tiff?|mid|mp2|mp3|mp4"
                                                      + "|wav|avi|mov|mpeg|ram|m4v|pdf" 
                                                      + "|rm|smil|wmv|swf|wma|zip|rar|gz))$");

    /**
     * You should implement this function to specify whether
     * the given url should be crawled or not (based on your
     * crawling logic).
     */
     
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
					&& !EXCLUDE.matcher(href).matches();
    }

    /**
     * This function is called when a page is fetched and ready 
     * to be processed by your program.
     */
  @Override
	    public void visit(Page page) {          
//	            String url = page.getWebURL().getURL();
	    		WebURL url = page.getWebURL();
	            System.out.println("URL: " + url);
	            
	    		if (page.getParseData() instanceof HtmlParseData) {
	    			HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
	    			String title = htmlParseData.getTitle();	    			
	    			String html = htmlParseData.getHtml();
	    			List<WebURL> links = htmlParseData.getOutgoingUrls();
	    			
	    			String text = htmlParseData.getText();
	    			
	    			if (LOGPATH != null) {
	    				writeToLog(url, title, text);
	    			}
	    			if (LINKFILE != null){
	    				printLog(url,text,html,links);
	    			}
	    		}
	    	}
	    		

		private synchronized void printLog(WebURL url, final String text, final String html, final List<WebURL> links){
			System.out.println("URL: " + url.getURL());
			System.out.println("Text length: " + text.length());
			System.out.println("Html length: " + html.length());
			System.out.println("Number of outgoing links: " + links.size());
			try {
				FileWriter fWriter = new FileWriter(LINKFILE,true);
				StringBuilder builder = new StringBuilder(url.getURL());
				builder.append("\t");
				for ( WebURL link : links){
					builder.append(link.getURL());
					builder.append(" ");
				}
				builder.append("\n");
				fWriter.write(builder.toString());
				fWriter.close();
			} catch (IOException e) {
				System.err.println("Error when writing " + url.getURL());
				e.printStackTrace();
			}
		}
		
		private void writeToLog(WebURL url, String title, String text) {
			try {
				// write the data ...
				String path = url.getURL().substring("http://".length());
				File file = new File(LOGPATH + path);
				if (file.isDirectory()){
					file = new File(LOGPATH + path + "index.txt");
				}else if (url.getPath().lastIndexOf('.') < url.getPath().lastIndexOf('/')){
					file = new File(LOGPATH + path + "/index.txt");
				}
				if (!file.exists()){
					File parent = new File(file.getParent());
					if (!parent.exists()){
						parent.mkdirs();
					}
				}
				FileWriter fWriter = new FileWriter(file);
				if (title!=null){
					fWriter.write(title);
				}
				if (text != null){
					fWriter.write(text);
				}
				fWriter.close();
			} catch (Exception e) {
				System.err.println("Error when writing " + url );
				e.printStackTrace();
			}
		}

		public static void setLogFile(String pathname) {
			LOGPATH = pathname;
		}
		
		public static void setLinkFile(String pathname){
			LINKFILE = pathname;
		}

		private static String LOGPATH = null;
		private static String LINKFILE = null;
		
		public static void main(String[] args) throws Exception {
			System.out.println(QUERRFILTERS.matcher("http://www.ics.uci.edu/2323??82/djfj?").matches());
			System.out.println(BEGIN.matcher("http://djf.ics.uci.edu/").matches()) ;
			System.out.println(BEGIN.matcher("http://tomato.ics.uci.edu/").matches()) ;
			System.out.println(BEGIN.matcher("http://cs.uci.edu/").matches()) ;
			System.out.println(EXCLUDE.matcher("http://kdd.ics.uci.edu/").matches());
			System.out.println("http://ics".substring("http://".length()));
		}
	}
