package task1;

/**
 * Class to create a WebReader
 * object which will be used to connect to the Open University home page and
 * show its contents in text form. This is the 'main' class.
 *
 * Copyright: (c) 2015 Company: The Open University
 *
 * @author TM352 Module Team
 * @version 1.1 1/5/2015
 */
public class TestWebReader
{

   /**
    * The main method
    * @param args
    */
   public static void main(String[] args)
   {
      //Specify a URL to open. You can try changing this, e.g. to http://bbc.co.uk
      //This page should have a 200 (OK) status
      WebReader wr = new WebReader("http://www.open.ac.uk");
      
      //wr = new WebReader("http://bbc.co.uk");
      
      //A page that should have a 404 (Not Found) status
      //wr = new WebReader("http://www.open.ac.uk/broken"); //nothing to see here
      
      wr.createConnection();
      
      wr.printRequestHeaders();

      //check header information. This requires opening a connection,
      //which is underpinned by a socket
      wr.printResponseHeaders();

      //show contents of a webpage. This requires streaming information 
      //through the connection
      wr.printHTML();
   }
}
