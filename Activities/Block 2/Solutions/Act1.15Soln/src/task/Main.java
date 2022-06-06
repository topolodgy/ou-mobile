/**
 * Main.java
 * Using a fixed key, dependent on a key string to generate the fixed key.
 * A new method getFixedKey has been provided in CryptoServices2
 *
 * Created on 23rd July 2016
 *
 * @author tm352 Module Team.
 */
package task;

import javax.crypto.SecretKey;

/**
 * The Main class
 */
public class Main
{

   /**
    * The main method
    *
    * @param args
    * @throws Exception
    */
   public static void main(String[] args) throws Exception
   {
      String originaltext = "To be or not to be?";
      String plaintext;
      byte[] ciphertext;
      final String cipherName = "DES"; //only DES in this example      
      String keyString = "abcdefgh";
      SecretKey key;

      System.out.println("\nOriginal text>" + originaltext + "<");
      System.out.println("Using DES\n");

      //skf = SecretKeyFactory.getInstance(cipherName);      
      key = CryptoServices.getFixedKey(keyString);

      //displaying the key (allows you to check that it remains the same)
      System.out.println("First key we got...");
      CryptoServices.displayKey(key);

      //Get key again, do we get the same key? (we should if we use the same key string)
      System.out.println("\nSecond key, should be same as first:");
      key = CryptoServices.getFixedKey(keyString);

      CryptoServices.displayKey(key);

      //Encipher the original text using that key
      ciphertext = CryptoServices.encipher("DES", originaltext, key);

      //Show the enciphered text
      System.out.println("\nEncrypted message: >" + (new String(ciphertext)) + "<");

      //Decipher using the same key (symmetric)
      plaintext = CryptoServices.decipher(cipherName, ciphertext, key);

      System.out.println("\nplaintext>" + plaintext + "<");

      if (plaintext != null)
      {
         System.out.println("\nOriginal equals plaintext? " + plaintext.equals(originaltext));
      }
      else
      {
         System.out.println("Unable to decipher the ciphertext.");
      }

   }

}
