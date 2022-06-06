/*
 * Sample solution to Act1.4 Echo web service
 */
package task1;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author TM352
 */
@WebService(serviceName = "Echo")
public class Echo
{

   /**
    * This is a sample web service operation
    * @param txt text to use in creating a simple hello message
    * @return our hello message, incorporating txt
    */
   @WebMethod(operationName = "hello")
   public String hello(@WebParam(name = "name") String txt)
   {
      return "Hello " + txt + "!";
   }

   /**
    * Echo web service operation
    * @param shout a string to use in creating an 'echo'
    * @return the 'echo' of the shout
    */
   @WebMethod(operationName = "echo")
   public String echo(@WebParam(name = "shout") String shout)
   {
      String response = shout + "\n";
      for (int i = 1; i < shout.length(); i++)
      {
         response += shout.substring(i) + "\n";
      }

      return response;
   }

}
