/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package echosoapclient;

/**
 *
 * @author ad2472
 */
public class EchoSOAPClient
{

   /**
    * @param args the command line arguments
    */
   public static void main(String[] args)
   {
      // Example code to call the 'echo' service
      String shout = "hello web service";
      String result = echo(shout);
      System.out.println("Result = " + result);
      
      //Example code to call the 'hello' service
      String result2 = hello("Walrus");
      
      System.out.println("Result2 was >" + result2 + "<");

   }

   private static String echo(java.lang.String shout)
   {
      echosoapclient.Echo_Service service = new echosoapclient.Echo_Service();
      echosoapclient.Echo port = service.getEchoPort();
      return port.echo(shout);
   }

   private static String hello(java.lang.String name)
   {
      echosoapclient.Echo_Service service = new echosoapclient.Echo_Service();
      echosoapclient.Echo port = service.getEchoPort();
      return port.hello(name);
   }

}
