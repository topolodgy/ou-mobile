/*
 * An example REST web service to produce upper and lower cased 
 * strings. The services are accessed at particular URIs
 */
package rest;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author tm352 November 2015
 */
@Path("case")
public class CaseResource
{
   @Context
   private UriInfo context;

   /**
    * Creates a new instance of CaseResource
    */
   public CaseResource()
   {
   }

   /**
    * Web service method to convert a word to uppercase and return HTML 
    * @param word the word to convert to uppercase
    * @return The word converted to uppercase, with HTML wrapping
    */
   @GET
   @Path("/upper/{word}")
   @Produces(MediaType.TEXT_HTML)
   public String upper(@PathParam("word") String word)
   {
      return "<HTML> <b>" + word.toUpperCase() + " </b> <HTML/>";
   }

   /**
    * Web service method to convert a word to lowercase and return HTML 
    * @param word the word to convert to lowercase
    * @return The word converted to lowercase, with HTML wrapping
    */
   @GET
   @Path("/lower/{word}")
   @Produces(MediaType.TEXT_HTML)
   public String lower(@PathParam("word") String word)
   {
      return "<HTML> <b>" + word.toLowerCase() + " </b> <HTML/>";
   }

   /**
    *
    * @return
    */
   @GET
   @Produces(MediaType.TEXT_HTML)
   /**
    * A web service method to produce an HTML description of this web service's
    * operations, 'upper' and 'lower'
    * Note, this method does not have a Path annotation, so it is accessed from
    * the base resource URI (the class URI)
    */
   public String about()
   {
      return "<HTML> Examples:"
              + "<br> /upper/red will give RED"
              + "<br> /lower/BLUE will give blue <HTML/>";
   }

}
