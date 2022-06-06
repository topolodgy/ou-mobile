package task4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Title: Activity 1.3 RequestSender
 *
 * Description: the run method of this class takes three arguments
 *
 * String serverURL
 * int portNumber
 * String an HTTP request string
 *
 * When the run method is called, a network connection is established to a
 * server at the site identified by serverURL, listening on port portNumber.
 *
 * The request is then sent to the server, and the response is read line
 * by line and displayed on the standard output.
 *
 * Copyright:   (c) 2015
 * Company:     The Open University
 * @author TM352 Module Team
 * @version 1.0 September 2015
 */

public class RequestSender
{
   private Socket socket; // A socket to connect to server
   private PrintWriter out; // output stream to server
   private BufferedReader in; // input stream from server

   /**
    * Initialise socket to connect to a particular host and port
    * @param hostName the host name to connect to
    * @param portNumber the port on which to connect
    * @param request an HTTP request to send to the host
    */
   public void run(String hostName, int portNumber, String request)
   {
      try
      {
         // Connect to the server.
         socket = new Socket(hostName, portNumber);
         
         //If we get this far then we should be connected
         //(otherwise an exception will be thrown)
         System.out.println("\nConnected to site " + hostName + 
            " on port number " + portNumber);

         sendRequest(request);

         displayResponse();

         closeConnection();
      }
      catch (IOException e)
      {
         System.out.println(e);
      }
   }

   /**
    * Send a request expressed as a string to a web server
    * @param request
    * @throws IOException 
    */
   private void sendRequest(String request) throws IOException
   {  
      System.out.println("Attempting to send request: \n" + request);
      /* Open a writer to send a request to the server. */
      out = new PrintWriter(socket.getOutputStream(), true /* autoFlush */);

      /* Send the request. */
      out.println(request);
      out.flush();
      
      //If we reached here we succeeded in sending the request.
      
      System.out.println("Request was sent!");
   }

   /**
    * Read all the lines of text returned by the web server
    * and print them on the screen.
    * @throws IOException 
    */
   private void displayResponse() throws IOException
   {
      System.out.println("THE SERVER RESPONDED AS FOLLOWS:\n ");
      /* Open a reader to receive the response from the server. */
      in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

      /* Receive and display the response line by line */
      String responseLine = in.readLine();
      while (responseLine != null)
      {
         System.out.println(responseLine);
         responseLine = in.readLine();
      }
      
      System.out.println("END SERVER RESPONSE");
   }

   /**Close the streams and the socket linked to the web server.
    * 
    * @throws IOException 
    */
   private void closeConnection() throws IOException
   {
      out.close();
      in.close();
      socket.close();
   }

}
