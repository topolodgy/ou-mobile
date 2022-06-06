/*
 * Example code to call REST service programmatically.
 * The project needs to be deployed first.
 */
package rest;

/**
 * Right-click and Run File.
 * @author TM352
 */
public class Main
{
   public static void main(String[] args)
   {
      RESTcaseClient c = new RESTcaseClient();
      
      String about = c.about();
      System.out.println("About returned " + about);
      
      String upper = c.upper("my lowercase string");      
      System.out.println("Upper produced " + upper);
      
      String lower = c.lower("TEST OF LOWERCASE METHOD");      
      System.out.println("Lower produced " + lower);
   }
  
}
