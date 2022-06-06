/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

/**
 * Jersey REST client generated for REST resource:CaseResource [case]<br>
 * USAGE:
 * <pre>
        RESTcaseClient client = new RESTcaseClient();
        Object response = client.XXX(...);
        // do whatever with response
        client.close();
 </pre>
 *
 * @author ad2472
 */
public class RESTcaseClient
{
   private WebTarget webTarget;
   private Client client;
   private static final String BASE_URI = "http://localhost:8080/RESTcaseSoln/webresources";

   public RESTcaseClient()
   {
      client = javax.ws.rs.client.ClientBuilder.newClient();
      webTarget = client.target(BASE_URI).path("case");
   }

   public String upper(String word) throws ClientErrorException
   {
      WebTarget resource = webTarget;
      resource = resource.path(java.text.MessageFormat.format("upper/{0}", new Object[]{word}));
      return resource.request(javax.ws.rs.core.MediaType.TEXT_HTML).get(String.class);
   }

   public String lower(String word) throws ClientErrorException
   {
      WebTarget resource = webTarget;
      resource = resource.path(java.text.MessageFormat.format("lower/{0}", new Object[]{word}));
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
