/*
 * A servlet to serve a widget form to sales people 
 * (users in group 'salespeople'). 
 *
 * March 2016
 */
package servlets;

import java.io.*;

import javax.servlet.*;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

/**
 * The urlPatterns below indicate valid paths to the content generated by this
 * servlet.
 *
 * @author TM352
 * @version 1.2
 */
@WebServlet(name = "WidgetServlet", urlPatterns =
{
   "/widgets", "/WidgetServlet"
})

//Declare allowed roles for this servlet
@ServletSecurity(
        @HttpConstraint(rolesAllowed =
        {
           "salespeople"  //any authenticated users in roles listed here are allowed
        //you could add "anybody" to this list               
}))

public class WidgetServlet extends HttpServlet
{
   /**
    * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
    * methods.
    *
    * @param request servlet request
    * @param response servlet response
    * @throws javax.servlet.ServletException
    * @throws java.io.IOException
    */
   protected void processRequest(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException
   {
      response.setContentType("text/html;charset=UTF-8");
      PrintWriter out = response.getWriter();
      out.println("<html>");
      out.println("<head>");
      out.println("<title>Widget Page</title>");
      out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"styles.css\"> ");

      out.println("</head>");

      request.getRequestDispatcher("links.html").include(request, response);

      out.println("<h1>Welcome to the Widget Servlet page</h1>");

      String name = request.getRemoteUser();
      if (name == null)
      {
         out.println("You seem to be null. You must have arrived here without logging in.");
      }
      else if (request.isUserInRole("salespeople"))
      {
         //customised content for salespeople
         out.println("<body>");
         out.println("<h2>Greetings " + name + "!</h2>");
         out.println("<p>Salespeople are welcome to visit this page and record orders.</p>");

         out.println("<br>");

         //It is possible to include other pages, e.g.      
         request.getRequestDispatcher("/widget_form.html").include(request, response);
         //as well as forward requests to other pages     

         out.println("<br><p>That's our form... Though we haven't any code to process it properly.");
         out.println("Come back when we've finished building our web site.</p>");
      }
      else if (request.isUserInRole("anybody"))
      {
         //customised content for users in role anybody
         out.println("<h2>Greetings " + name + "!</h2>");
         out.println("<p>You've managed to view this page, but we're not showing you</p>");
         out.println("the widget order form");
      }

      out.println("</body>");
      out.println("</html>");
      out.close();
   }

   // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
   /**
    * Handles the HTTP <code>GET</code> method.
    *
    * @param request servlet request
    * @param response servlet response
    */
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException
   {
      processRequest(request, response);
   }

   /**
    * Handles the HTTP <code>POST</code> method.
    *
    * @param request servlet request
    * @param response servlet response
    */
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException
   {
      processRequest(request, response);
   }

   /**
    * Returns a short description of the servlet.
    */
   public String getServletInfo()
   {
      return "Short description";
   }
   // </editor-fold>
}
