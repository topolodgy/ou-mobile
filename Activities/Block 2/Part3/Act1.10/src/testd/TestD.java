/**
 * You need your own account to access this service. To sign up, go to
 * delicious.com. You will need to post bookmarks before you will
 * be able to retrieve any!
 */
package testd;

import org.netbeans.saas.delicious.DeliciousBookmarkingService;
import org.netbeans.saas.RestResponse;

/**
 *
 * @author tm352
 */
public class TestD
{
   /**
    * @param args the command line arguments
    */
   public static void main(String[] args)
   {

      //add a bookmark (overwrites previous)
      TestD.addPosts("http://www.bbc.com", "bbc description", "radio tv television", "yes");
      
      //check the last update for your account
      RestResponse updatePosts = TestD.updatePosts();

      String[] attribs =
      {
         "code", "time"
      };
      Utility.printAbout(updatePosts, "update", attribs);
      
      //check recent posts contents
      RestResponse recentPosts = recentPosts();
      String[] attribs2 =
      {
         "description", "time"
      };
      Utility.printAbout(recentPosts, "post", attribs2);      
              
   }

   public static RestResponse updatePosts()
   {
      RestResponse result3 = null;

      try
      {
         result3 = DeliciousBookmarkingService.updatePosts();
         if (result3.getDataAsObject(delicious.bookmarkingservice.deliciousresponse.Update.class) instanceof delicious.bookmarkingservice.deliciousresponse.Update)
         {
            delicious.bookmarkingservice.deliciousresponse.Update result3Obj = result3.getDataAsObject(delicious.bookmarkingservice.deliciousresponse.Update.class);
         }
         //TODO - Uncomment the print Statement below to print result.
         System.out.println("The SaasService returned: " + result3.getDataAsString());
      }
      catch (Exception ex)
      {
         ex.printStackTrace();
      }

      return result3;

   }

   /**
    * Example method to call the addPosts code. There are seven parameters the
    * generated code can use, but we only specified four of them in the method
    * header.
    *
    * @param url a url to add to the user's bookmarks
    * @param description a description of the url
    * @param tags tags to describe the url we're adding, space separated
    * @param replace yes if this should replace an existing post
    */
   private static void addPosts(String url, String description, String tags, String replace)
   {

      try
      {
         String extended = null;
         String dt = null;
         String shared = null;

         RestResponse result4 = DeliciousBookmarkingService.addPosts(url, description, extended, tags, dt, replace, shared);
         if (result4.getDataAsObject(delicious.bookmarkingservice.deliciousresponse.Result.class) instanceof delicious.bookmarkingservice.deliciousresponse.Result)
         {
            delicious.bookmarkingservice.deliciousresponse.Result result4Obj = result4.getDataAsObject(delicious.bookmarkingservice.deliciousresponse.Result.class);
         }
         //TODO - Uncomment the print Statement below to print result.
         //System.out.println("The SaasService returned: "+result4.getDataAsString());
      }
      catch (Exception ex)
      {
         ex.printStackTrace();
      }

   }

   public static RestResponse recentPosts()
   {
      RestResponse result1 = null;

      try
      {
         String tag1 = null;
         String count = null;

         result1 = DeliciousBookmarkingService.recentPosts(tag1, count);
         if (result1.getDataAsObject(delicious.bookmarkingservice.deliciousresponse.Posts.class) instanceof delicious.bookmarkingservice.deliciousresponse.Posts)
         {
            delicious.bookmarkingservice.deliciousresponse.Posts result1Obj = result1.getDataAsObject(delicious.bookmarkingservice.deliciousresponse.Posts.class);
         }
         //TODO - Uncomment the print Statement below to print result.
         System.out.println("The SaasService returned: " + result1.getDataAsString());
      }
      catch (Exception ex)
      {
         ex.printStackTrace();
      }

      return result1;

   }

   public static RestResponse postDates()
   {
      RestResponse result = null;

      try
      {
         String tag = null;

         result = DeliciousBookmarkingService.postsDates(tag);
         if (result.getDataAsObject(delicious.bookmarkingservice.deliciousresponse.Dates.class) instanceof delicious.bookmarkingservice.deliciousresponse.Dates)
         {
            delicious.bookmarkingservice.deliciousresponse.Dates resultObj = result.getDataAsObject(delicious.bookmarkingservice.deliciousresponse.Dates.class);
         }
         //TODO - Uncomment the print Statement below to print result.
         System.out.println("The SaasService returned: " + result.getDataAsString());
      }
      catch (Exception ex)
      {
         ex.printStackTrace();
      }

      return result;

   }

}
