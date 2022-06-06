/*
 * Utility class with a method to print out information from an XML document.
 */
package testd;

import java.io.IOException;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.netbeans.saas.RestResponse;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author tm352
 */
public class Utility
{
    /**
    * Utility to print out information about a RestResponse
    * Note that tags and attributes are case-sensitive! 
    * Take care with the spelling. If the tag is not found
    * the NodeList will have one element in it anyway, with a null first child
    * (not checked for).
    * @param res the RestResponse to print out
    * @param tag the tag to look for in this response. 
    * @param attribs the attributes to look for under the tag
    */
   public static void printAbout(RestResponse res, String tag, String[] attribs)
   {
      if (res == null || tag == null || attribs == null)
      {
         System.out.println("There was a null input in printAbout. Nothing to show.");
         return;
      }

      System.out.println("Response was " + res.getResponseMessage());
      System.out.println("Content type was " + res.getContentType());

      InputSource is;

      try
      {
         is = new InputSource(new StringReader(res.getDataAsString()));

         //produce a DOM object from the XML response
         Document doc = DocumentBuilderFactory
                 .newInstance()
                 .newDocumentBuilder()
                 .parse(is);

         //build a list of all nodes tagged <post>
         NodeList results = doc.getElementsByTagName(tag);

         System.out.printf("There are %d nodes tagged <%s>%n", results.getLength(), tag);
             
         for (int i = 0; i < results.getLength(); i++)
         {
            NamedNodeMap attributes = results.item(i).getAttributes();

            for (String s : attribs)
            {
               System.out.print(attributes.getNamedItem(s) + " ");
            }

            System.out.println("");
         }
      }
      catch (ParserConfigurationException | SAXException | IOException ex)
      {
         System.out.println("Exception parsing rest response" + ex);
      }

      System.out.println("");
   }
}
