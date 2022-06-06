/**
 * This is a standard Java project, with Main set as the Main class.
 * The code in this class was generated automatically by selecting RESTCaseSoln / CaseResource
 * as the REST web service we wanted to use when creating a new RESTful Java client.
 * Note, this project is paired with RESTcaseSoln, which determines its
 * starting URL (BASE_URI)
 */
package test;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

/**
 * Jersey REST client generated for REST resource:CaseResource [case]<br>
 * USAGE:
 * <pre>
 *        RestCaseClient client = new RestCaseClient();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author ad2472
 */
public class RestCaseClient
{
   private WebTarget webTarget;
   private Client client;
   //This base URI applies to the RESTcaseSoln project. 
   //Use http://localhost:8080/RESTcase/webresources for the original project
   private static final String BASE_URI = "http://localhost:8080/RESTcaseSoln/webresources";

   public RestCaseClient()
   {
      client = javax.ws.rs.client.ClientBuilder.newClient();
      webTarget = client.target(BASE_URI).path("case");
   }

   public String upper(String word) throws ClientErrorException
   {
      WebTarget resource = webTarget;
      resource = resource.path(java.text.MessageFormat.format("upper/{0}", new Object[]
      {
         word
      }));
      return resource.request(javax.ws.rs.core.MediaType.TEXT_HTML).get(String.class);
   }

   public String lower(String word) throws ClientErrorException
   {
      WebTarget resource = webTarget;
      resource = resource.path(java.text.MessageFormat.format("lower/{0}", new Object[]
      {
         word
      }));
      return resource.request(javax.ws.rs.core.MediaType.TEXT_HTML).get(String.class);
   }

   public String about() throws ClientErrorException
   {
      WebTarget resource = webTarget;
      return resource.request(javax.ws.rs.core.MediaType.TEXT_HTML).get(String.class);
   }

   public void close()
   {
      client.close();
   }

}
