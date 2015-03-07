import java.util.*;
import java.net.*;
import java.io.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.util.ArrayList;

class nytMovieReviews {
	
	//API key is split into two parts to support movie name entry in middle
	String apiReq1 = "http://api.nytimes.com/svc/movies/v2/reviews/search.xml?query=";
	String apiReq2 = "&api-key=ENTER-YOUR-KEY!"; //****Enter NYT API Key !****//
	
	//Movie Data Fields
	private String movieName;
	private String headline;
	private String shortSummary;
	private String openingDate;
	private String mpaaRating;
	
	//Contains list of all the search results for a movie in formated form
	ArrayList<String> movieResults = new ArrayList<String>();
	
	//Empty constructor
	public nytMovieReviews() {}
	
	//Access and prints out all the movie results for passed in term
	public void getMovieReviews(String movie)
	{
		parseMovieData(getResponse(movie.replaceAll("\\s","")));
		
		//Cycle and print out search results
		for(int k = 0; k < movieResults.size(); k++)
		{
			System.out.println("Search Result #" + (k+1));
			System.out.println(movieResults.get(k));
		}
	}
	//Returns an XML document with search results
	private Document getResponse(String query)
	{
		//Create an empty document
		Document doc = null;
		//MUST be accompanied by try {...} catch () {...} or will not WORK!!!!
		try {
			URL xmlURL = new URL(apiReq1 + query + apiReq2); //Create a URL with API XML document
			InputStream xml = xmlURL.openStream(); //Create a stream to read the XML document from the URL link
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance(); //Create a 'factory" to take the xml and make a document
			DocumentBuilder db = dbf.newDocumentBuilder(); //A document builder inside the factory creates the document
			doc = db.parse(xml);
			xml.close();
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
		
		return doc;
	}
	//Take all the movie data from the passed in XML document for formatting
	private void parseMovieData(Document xmlDocument)
	{
		//Loads the 'node' array with all the review results
		NodeList nodes = xmlDocument.getElementsByTagName("review");
		if( nodes.getLength() == 0 )
		{
			System.out.println("No results found. Try again !");
			return;
		}
		
		//Parse in Movie Data for each Review
		for(int i = 0; i < nodes.getLength(); i++)
		{
			//From the node list, access the i'th node
			Node currentNode = nodes.item(i);
			
			//Access all the data
			if( currentNode.getNodeType() == Node.ELEMENT_NODE) //If its a Node with elements
			{
				Element currentElement = (Element) currentNode;
				
				movieName = currentElement.getElementsByTagName("display_title").item(0).getTextContent();
				mpaaRating = currentElement.getElementsByTagName("mpaa_rating").item(0).getTextContent();
				headline = currentElement.getElementsByTagName("headline").item(0).getTextContent();
				shortSummary = currentElement.getElementsByTagName("summary_short").item(0).getTextContent();
				openingDate = currentElement.getElementsByTagName("opening_date").item(0).getTextContent();
				
				movieResults.add(generateTextRep());
			}
		}
	}
	//Generates a formatted representation of the search results.
	private String generateTextRep()
	{
		String movieText = "";
		movieText += "Movie Name: " + movieName + " (" + mpaaRating + ")\n";
		movieText += "Opening Date: " + openingDate + "\n";
		movieText +=  "Overview: " + headline + "\n";
		movieText += "Movie Review:\n" + shortSummary + "\n";
		
		return movieText;
		
	}
	
}
