package task2;

/**
 * Title: Activity 1.2 TestRequestSender
 *
 * Creates an instance of RequestSender and invokes its run method to send a
 * request to an HTTP server. The RequestSender handles the underlying
 * communication with the web server using low level communications mechanisms:
 * a socket on a host, at a particular port.
 *
 * HTTP/1.0 is less strict than HTTP/1.1
 *
 * If you write a bad request to the server, the connection may become hung and
 * you will need to terminate it manually at the bottom of the NetBeans window.
 *
 * Copyright: (c) 2015 Company: The Open University
 *
 * @author TM352 Module Team
 * @version 1st February 2016
 */
public class TestRequestSender
{
   static final int PORT = 80; //we will use the standard HTTP port
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

      //Uncomment one example at a time!      
      TestRequestSender.example1();
      //TestRequestSender.example2();
      //TestRequestSender.example3();
   }

   static void example1()
   {
      RequestSender rs = new RequestSender();

      String host1 = "www.open.ac.uk"; //retrieve information from here            
      String path = "/"; //the path relative to the host address      

      //In this example we're asking for the root folder at the OU web server.
      //The protocol requires us to end the request with a carriage return
      //and a linefeed, which is written \r\n. Headers are also separated
      //with \r\n characters
      String request = String.format("%s %s %s\r\n\r\n", METHOD, path, PROTOCOL);

      rs.run(host1, PORT, request); //see RequestSender for the details.
   }

   static void example2()
   {
      RequestSender rs = new RequestSender();

      //Example 2: Some web servers require the host header. We've added one here
      String host2 = "www.bbc.co.uk";
      String path2 = "/";

      //Note the extra line we send to the server Host: host name
      String request = String.format("%s %s %s\r\nHost: %s\r\n\r\n", METHOD, path2, PROTOCOL, host2);

      rs.run(host2, PORT, request);
   }

   static void example3()
   {
      RequestSender rs = new RequestSender();

      //Example 3: Using a path relative to the host address
      String host3 = "www.w3.org";
      String path3 = "/Protocols/"; //A path relative to the host address

      //Note the extra line we send to the server Host: host name
      String request = String.format("%s %s %s\r\nHost: %s\r\n\r\n", METHOD, path3, PROTOCOL, host3);

      rs.run(host3, PORT, request);
   }

}
