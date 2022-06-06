/*
 * GreeterServlet.java
 * Activity 1.3
 * Created on 1st February 2016
 */

package servlets;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * Responds to GET and POST requests
 * @author TM352
 * @version 1.2
 */
public class GreeterServlet extends HttpServlet
{
    /** Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
    * @throws javax.servlet.ServletException
    * @throws java.io.IOException
     */   
   protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
        //set the response Content Type header
        response.setContentType("text/html;charset=UTF-8");
        
        //get an output stream to communicate with the client
        PrintWriter out = response.getWriter();
        
        //Write some initial tags to the output stream to create a web page
        out.println("<html>");
        out.println("<head>");
        out.println("<title>WebGreeter</title>");
        out.println("</head>");
        out.println("<body>");
                
        //This debugging output appears in the GlassFish Server output window
        //in NetBeans, since this code will run in the server.        
        //E.g., you could use this approach to show the client's request method
        System.out.println("Greeter servlet processRequest invoked " + request.getMethod());
        
        //Looks for the parameter called name, in the client's request
        String name = request.getParameter("name");
        
        //customise the output depending on whether a name was specified
        if (name == null)
        {
            int port = request.getLocalPort();
            String serverInfo = "This is the GreeterServlet on port " +  port;
            out.println("<h2>Pleased to meet you! </br>" + serverInfo + ".</br>");
            out.println("Sorry, I don't know your name.</h2>");
        }
        else
        {
            out.println("<h2>Greetings " + name + "!</h2>");
        }
        
        //finish writing the end tags of the web page to the output stream
        out.println("</body>");
        out.println("</html>");
        out.close();
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
    * @throws javax.servlet.ServletException
    * @throws java.io.IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
        processRequest(request, response);
    }

    /** Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
    * @throws javax.servlet.ServletException
    * @throws java.io.IOException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
        processRequest(request, response);
    }

    /** Returns a short description of the servlet.
    * @return 
     */
    public String getServletInfo()
    {
        return "Short description";
    }
    // </editor-fold>
}
