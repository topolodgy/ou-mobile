/*
 * Main.java
 * 22 Feb 2016 v1.0
 * Discover information about your installed cryptography services
 *
 * @author tm352 
 */
package task;

import java.security.Provider;
import java.security.Security;
import java.util.Set;
import java.util.TreeSet;

/**
 * Main class
 * @author tm352
 */
public class Main
{
   /**
    * Display information about cryptography services
    * @param args
    */
   public static void main(String[] args)
   {
      //get an array of providers of security services
      Provider[] providers = Main.showProviders();

      //show services from all our providers
      Main.showServices(providers);

      //show Cipher related algorithms
      Main.showAlgorithms("Cipher");
      
      //show Signature related algorithms
      Main.showAlgorithms("Signature");
 
   }

   /**
    * 1. Display information about your installed providers
    *
    * @return an array of Provider
    */
   private static Provider[] showProviders()
   {
      System.out.println("\n\nPROVIDERS\n");
      Provider[] providers = Security.getProviders();
      
      //show information about each provider
      for (Provider p : providers)
      {
         System.out.println(p.getInfo());
      }
      
      return providers;
   }

   /**
    * 2. Display information about the services that are provided. Because more
    * than one provider can implement the same service, we use a set to store
    * those services (to remove duplicates). So that we can display the services
    * in alphabetical order, we use a TreeSet.
    */
   private static void showServices(Provider[] providers)
   {
      System.out.println("\n\nSERVICES\n");
      TreeSet<String> typeSet = new TreeSet<String>();

      //for all the providers
      for (Provider p : providers)
      {
         //iterate over the services they provide 
         for (Provider.Service ps : p.getServices())
         {
            //and add them to the set of services
            typeSet.add(ps.getType());
         }
      }

      //print out the set of services we built up
      for (String t : typeSet)
      {
         System.out.println(t);
      }
   }

   /**
    * 3. Display information about the specified service
    * in terms of the algorithms that service provides
    */
   private static void showAlgorithms(String name)
   {
      System.out.println("\n" + name + " ALGORITHMS\n");

      Set ciphers = Security.getAlgorithms(name);
      //System.out.println(ciphers);

      for (Object o : ciphers)
      {
         System.out.println(o);
      }
   }
}
