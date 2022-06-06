/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netbeans.saas.delicious;

import java.io.IOException;
import org.netbeans.saas.RestConnection;
import org.netbeans.saas.RestResponse;

/**
 * DeliciousBookmarkingService Service
 *
 * @author TM352
 */
public class DeliciousBookmarkingService
{

   /**
    * Creates a new instance of DeliciousBookmarkingService
    */
   public DeliciousBookmarkingService()
   {
   }
   
   private static void sleep(long millis)
   {
      try
      {
         Thread.sleep(millis);
      }
      catch (Throwable th)
      {
      }
   }

   /**
    *
    * @param tag
    * @return an instance of RestResponse
    */
   public static RestResponse postsDates(String tag) throws IOException
   {
      DeliciousBookmarkingServiceAuthenticator.login();
      String[][] pathParams = new String[][]{};
      String[][] queryParams = new String[][]{{"tag", tag}};
      RestConnection conn = new RestConnection("http://api.del.icio.us/v1/posts/dates", pathParams, queryParams);
      sleep(1000);
      return conn.get(null);
   }

   /**
    *
    * @param tag
    * @param count
    * @return an instance of RestResponse
    */
   public static RestResponse recentPosts(String tag, String count) throws IOException
   {
      DeliciousBookmarkingServiceAuthenticator.login();
      String[][] pathParams = new String[][]{};
      String[][] queryParams = new String[][]{{"tag", tag}, {"count", count}};
      RestConnection conn = new RestConnection("http://api.del.icio.us/v1/posts/recent", pathParams, queryParams);
      sleep(1000);
      return conn.get(null);
   }

   /**
    *
    * @return an instance of RestResponse
    */
   public static RestResponse updatePosts() throws IOException
   {
      DeliciousBookmarkingServiceAuthenticator.login();
      String[][] pathParams = new String[][]{};
      String[][] queryParams = new String[][]{};
      RestConnection conn = new RestConnection("http://api.del.icio.us/v1/posts/update", pathParams, queryParams);
      sleep(1000);
      return conn.get(null);
   }

   /**
    *
    * @param url
    * @param description
    * @param extended
    * @param tags
    * @param dt
    * @param replace
    * @param shared
    * @return an instance of RestResponse
    */
   public static RestResponse addPosts(String url, String description, String extended, String tags, String dt, String replace, String shared) throws IOException
   {
      DeliciousBookmarkingServiceAuthenticator.login();
      String[][] pathParams = new String[][]{};
      String[][] queryParams = new String[][]{{"url", url}, {"description", description}, {"extended", extended}, {"tags", tags}, {"dt", dt}, {"replace", replace}, {"shared", shared}};
      RestConnection conn = new RestConnection("http://api.del.icio.us/v1/posts/add", pathParams, queryParams);
      sleep(1000);
      return conn.get(null);
   }
}
