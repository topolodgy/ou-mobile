package helloworld;

/**
 * Illustrative Main class, containing a main method wiht the necessary signature
 * @author tm352
 */
public class HelloWorld
{

   /**
    * The main method needs to have a particular signature to be a starting
    * point for execution of the program. This is the usual signature we use.
    * @param args the command line arguments
    */
   public static void main(String[] args)
   {
      // TODO code application logic here
      System.out.println("Hello World!");
      
      Frog f = new Frog("Bob");
      
      System.out.println("Frog information " + f.toString());
      
      System.out.println("");
   }
   
}
