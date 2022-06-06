package rest;

import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author tm352 module team
 *
 * Created February 2016
 * v2 To use local XML file July 2019
 */
public class BBCSchedules
{

   /**
    * The main method demonstrates connecting to a REST service at the BBC
    */
   public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException
   {
      // Build up a string representing the URI of the RESTful resource.
      //This XML data service is no longer available. It used to work like this:
      //String uriBase = "http://www.bbc.co.uk/bbcone/programmes/schedules/";
      //String region = "london/";
      String date = "2015/10/27";
      //String uriString = uriBase + region + date + ".xml";
       
      //The data we used to get online is in this file in the project folder
      //Just for demonstration purposes
      String uriString = "BBCSchedule.xml";

      // Send a GET request to the URI and obtain the XML document.
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      DocumentBuilder db = dbf.newDocumentBuilder();
      Document xmlDocument = db.parse(uriString);

      System.out.println("BBC One Schedule for " + date + "\n");      
      
      //Create an XPath object to process the XML document
      XPathFactory xpf = XPathFactory.newInstance();
      XPath xpath = xpf.newXPath();

      //Get a list of all the nodes in the tree that are tagged <broadcast>
      NodeList broadcastList = xmlDocument.getElementsByTagName("broadcast");

      //Iterate over the node list, displaying information about each node
      String start, synopsis, title;
      Node broadcast;
      for (int i = 0; i < broadcastList.getLength(); i++)
      {
         //Access the current broadcast node
         broadcast = broadcastList.item(i);
         
         //Extract information from the broadcast about its start time, synopsis and title
         start = (String) xpath.evaluate("start", broadcast, XPathConstants.STRING);
         synopsis = (String) xpath.evaluate("programme/short_synopsis", broadcast, XPathConstants.STRING);
         title = (String) xpath.evaluate("programme/display_titles/title", broadcast, XPathConstants.STRING);

         System.out.println(start.substring(11, 16) + " " + title);
         System.out.println(synopsis + "\n");
      }
   }
}
