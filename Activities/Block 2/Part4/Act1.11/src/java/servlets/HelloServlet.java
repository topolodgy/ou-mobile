/**
 * A servlet to serve up a greeting page to any visitors
 * whether authenticated or not.
 *
 * @author TM352
 * @version 1.1
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The URL patterns in the annotation below (an array of strings)
 * determine the paths that will lead to this servlet - there can be more than
 * one.
 * 
 */
@WebServlet(name = "HelloServlet", urlPatterns =
{
   "/HelloServlet", "/hello"
})
public class HelloServlet extends HttpServlet
{
   /**
    * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
    * methods.
    *
    * @param request servlet request
    * @param response servlet response
    * @throws ServletException if a servlet-specific error occurs
    * @throws IOException if an I/O error occurs
    */
   protected void processRequest(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException
   {
      response.setContentType("text/html;charset=UTF-8");

      PrintWriter out = response.getWriter();

      out.println("<html>");
      out.println("<head>");
      out.println("<title>Hello Servlet</title>");
      out.println("</head>");
      out.println("<body>");
      
      //include the content of links.html in this response
      request.getRequestDispatcher("links.html").include(request, response);  
      
      out.println("<h1>Welcome to the HelloServlet</h1>");

      out.println("<p>If you completed NetBeans activity 11 Part 1, you should have ");
      out.println("set up a user called <b>sales-1</b> with password <b>sales</b></p>");
      out.println("<p>and a user called <b>anyone</b> with password <b>anyone</b>.</p>");

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
    * @throws ServletException if a servlet-specific error occurs
    * @throws IOException if an I/O error occurs
    */
   @Override
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
    * @throws ServletException if a servlet-specific error occurs
    * @throws IOException if an I/O error occurs
    */
   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException
   {
      processRequest(request, response);
   }

   /**
    * Returns a short description of the servlet.
    *
    * @return a String containing servlet description
    */
   @Override
   public String getServletInfo()
   {
      return "Test authentication servlet";
   }// </editor-fold>

}
