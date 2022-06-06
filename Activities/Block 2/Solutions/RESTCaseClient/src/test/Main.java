/**
 * We added a Main class to test the methods that were generated for us
 * automatically in RestCaseClient.java. All we need to do is create
 * and object of the class that was created for us, and invoke its methods
 * using some test data.
 */
package test;

/**
 *
 * @author tm352
 */
public class Main
{

   /**
    * @param args the command line arguments
    */
   public static void main(String[] args)
   {
      String lower = "Frida Kahlo";
      String upper = "UNICEF";

      RestCaseClient r = new RestCaseClient();

      System.out.println("About " + r.about());

      System.out.printf("Upper of %s is %s%n", lower, r.upper(lower));

      System.out.printf("Lower of %s is %s%n", upper, r.lower(upper));

   }

}
