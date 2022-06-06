package task1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * Title: Activity 1.2 WebReader Description: Connect to a web page, using HTTP,
 * and read and display its contents, also show header information.
 *
 * You do not need to understand the details of how this code works. It is
 * intended to illustrate how a client could connect to a web site and download
 * the contents of a web page, as well as inspect the contents of a GET message
 * header.
 *
 * Copyright: (c) 2015 The Open University
 *
 * @author TM352 Module Team
 * @version 2 27/4/18
 */
public class WebReader
{
   private final String webAddress; //the URL to open
   private URL selectedURL;
   private HttpURLConnection connection; //used to store a connection reference
   private BufferedReader fromWebsite; //used to stream data from the source

   /**
    * Construct a WebReader object using a given String URL
    *
    * @param address
    */
   public WebReader(String address)
   {
      webAddress = address;
      try
      {
         selectedURL = new URL(webAddress);
      }
      catch (MalformedURLException me)
      {
         System.out.println("Malformed URL found " + me);
         System.out.println("This may mean you forgot the protocol, e.g. http.");
         System.out.println("Or there is an illegal character in the URL.");
      }
   }

   /**
    * This method attempts to establish a connection with a website at the
    * provided URL (webAddress)
    */
   public void createConnection()
   {
      System.out.println("Trying to contact " + webAddress);

      try
      {
         //We assume HTTP protocol here.
         //A connection can be used to obtain header information
         connection = (HttpURLConnection) selectedURL.openConnection();

         //The client can set various request headers               
         connection.setRequestProperty("User-Agent", "NetBeans TM352");
         connection.setRequestProperty("From", "student@open.ac.uk");

         //these request headers are associated with arbitrary strings, although some
         //have designated meanings, such as User-Agent, that can be used by 
         //the server to vary its behaviour. The values associated with 
         //these properties are also not fixed by law (see e.g. 'accept stuff')
         connection.setRequestProperty("Smarties", "no blue ones please");
         connection.setRequestProperty("Accept", "stuff, but not nonsense");

         //System.out.println("props " + connection.getRequestProperty("accept"));
         System.out.println("Connected for " + connection.getRequestMethod());

      }
      catch (IOException ex)
      {
         System.out.println("Problems opening connection " + ex);

         System.out.println("Are you connected to the internet and is java.exe set as a trusted program?");
         System.out.println("Check the URL carefully. Is it correct?");
      }
   }

   /**
    * Our connection is already initialised in the open state, so we don't
    * use this.
    */
   public void openConnection()
   {
      System.out.println("Trying to contact " + webAddress);

      try
      {
         //Connect to the URL (will be ignored if already connected)
         connection.connect();
      }
      catch (IOException ex)
      {
         System.out.println("Problems opening connection " + ex);

         System.out.println("Are you connected to the internet and is java.exe set as a trusted program?");
         System.out.println("Check the URL carefully. Is it correct?");
      }
   }

   /**
    * Print out the headers associated with the current HttpURLConnection
    */
   public void printRequestHeaders()
   {
      System.out.println("***********************************************");
      System.out.println("\n\nPrint Request Properties output..... ");
      if (connection.getRequestProperties().isEmpty())
      {
         System.out.println("No request headers have been set for this request!");
      }

      Map<String, List<String>> requestProperties = connection.getRequestProperties();

      int i = 1;
      for (String s : requestProperties.keySet())
      {
         System.out.printf("%d %s: %s%n", i++, s, requestProperties.get(s));
      }
   }

   /**
    * A method to inspect header information when using a GET message.
    */
   public void printResponseHeaders()
   {
      System.out.println("***********************************************");
      System.out.println("\n\nPrint Header Info output..... ");
      if (connection != null)
      {
         //See http://www.w3.org/Protocols/rfc2616/rfc2616-sec6.html

         System.out.println("\nHere is the connection information\n");

         //Map<String, List<String>> headerFields = connection.getHeaderFields();
         //let's see what the headers are, and the info associated with them
         int i = 0;
         String value = connection.getHeaderField(i);
         String key = connection.getHeaderFieldKey(i);
         while (value != null)
         {
            System.out.printf("%d %s: %s%n", i, key == null ? "status" : key, value);
            i++; //next field
            value = connection.getHeaderField(i);
            key = connection.getHeaderFieldKey(i);
         }

         System.out.println("\nThat's all the header information\n");
         System.out.println("***********************************************\n");
      }
      else
      {
         System.out.println("Sorry, I wasn't able to get any header information.");
      }
   }

   /**
    * Read and display HTML from a connection using a BufferedReader
    */
   public void printHTML()
   {
      System.out.println("Print HTML output..... ");
      try
      {
         //A stream can be used to read the data at the website URL, using the
         //connection we set up (which relies on sockets)
         fromWebsite = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      }
      catch (IOException ex)
      {
         System.out.println("Problems opening stream " + ex);

         System.out.println("Are you connected to the internet and is java.exe set as a trusted program?");
         System.out.println("Check the URL carefully. Is it correct and does it include a protocol, like http?");
         System.out.println("If you can't make this work, ask for help on the module forum, and quote the error you see here.");
      }

      if (fromWebsite != null)
      {
         String lineRead;

         System.out.printf("---Here are the contents of the URL %s%n%n", webAddress);
         try
         {
            // read from web site, one line at a time
            lineRead = fromWebsite.readLine();
            while (lineRead != null)
            {
               System.out.println(lineRead);
               lineRead = fromWebsite.readLine();
            }
            fromWebsite.close(); //close the connection to the website
         }
         catch (IOException io)
         {
            System.out.println("Problems reading " + io);
         }
         System.out.println("--- End of contents of URL: " + webAddress);
      }
      else
      {
         System.out.println("Unable to read web page " + webAddress + ", sorry.");
      }
   }

}
