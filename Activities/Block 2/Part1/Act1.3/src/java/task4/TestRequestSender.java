package task4;

/**
 * Title: Activity 1.3 TestRequestSender
 * 
 * Creates an instance of RequestSender and invokes its run method to send a
 * request to an HTTP server. The RequestSender handles the underlying
 * communication with the web server using low level communications mechanisms:
 * a socket on a host, at a particular port.
 * 
 * To run the examples in this file, right-click on it and select 'run'.
 *
 * Copyright: (c) 2016 Company: The Open University
 *
 * @author TM352 Module Team
 * @version 1.1 2/2/2016
 */
public class TestRequestSender
{
   static final String HOST = "localhost";
   static final int PORT = 8080; //the port on which our servlet is running
   static final String PROTOCOL = "HTTP/1.0"; //The protocol and version number. 
   static final String METHOD = "GET"; //retrieves information from the server

   /**
    * The main method
    * @param args
    */
   public static void main(String[] args) 
   {
      //Three examples of using a basic GET request and low level socket
      //based communication with a web server, using Java.

      //Example 1 visits the default entry point for the servlet
      TestRequestSender.example1();
      
      //Example 2 visits the same URL, providing a name parameter
      //allowing you to send a name to the servlet and 
      //receive a personalised greeting
      //TestRequestSender.example2("TM352 Student");
      
      //Example 3 visits the root localhost URL, to see a message from
      //the GlassFish server. This is the same as visiting http://localhost:8080/
      //TestRequestSender.example3();
   } 
   
   /**
    * Here we connect to the servlet by providing the correct path to
    * add to the base URL. 
    */
   static void example1()
   {
      RequestSender rs = new RequestSender();
        
      //In this example we add on the path to our servlet, but no parameters
      String simplepath = "/Act1.3/GreeterServlet";      
      
      String request = String.format("%s %s %s\r\n\n", METHOD, simplepath, PROTOCOL);

      rs.run(HOST, PORT, request); //see RequestSender for the details.
   }

   /**
    * Here we connect to the servlet by providing the correct path to
    * add to the base URL. We add on a parameter - our name.
    * Note the need to encode space characters as %20
    * to conform with the rules for URL formation. 
    */
   static void example2(String yourName) 
   {
      RequestSender rs = new RequestSender();
           
      //In this example we add on the path to our servlet, but no parameters
      String simplepath = "/Act1.3/GreeterServlet";      
      
      String parameter = String.format("name=%s", yourName);      

      //URLs may not contain spaces, so we replace any spaces with %20 
      String encodedParam = parameter.replace(" ", "%20");       
     
      String request = String.format("%s %s?%s %s\r\n\n", METHOD, simplepath, 
                                                           encodedParam, PROTOCOL);
      rs.run(HOST, PORT, request); //see RequestSender for the details.
   }
   
   /**
    * In this example we just connect to the glassfish server at its root.
    * I.e., we connected to http://localhost:8080/
    */
   static void example3()
   {
      RequestSender rs = new RequestSender();
           
      String path = "/"; //the path relative to the host address      

      //In this example we're asking for the root folder of the server on the
      //localhost (IP address 127.0.0.1) on port 8080 (the value of PORT).
      //The protocol requires us to have two carriage return and linefeed characters
      //at the end of the request, which is written \r\n\r\n. 
      String request = String.format("%s %s %s\r\n\n", METHOD, path, PROTOCOL);

      rs.run(HOST, PORT, request); //see RequestSender for the details.
   }  
  
}
