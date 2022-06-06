/**
 * Main.java
 * Test using symmetric cryptographic services.
 *
 * Created on 23rd February 2016
 *
 * This is the main class
 *
 * @author tm352 Module Team.
 */
package task1;

import javax.crypto.SecretKey;

/**
 * Main class
 */
public class Main
{

   /**
    * The main method
    * @param args
    */
   public static void main(String[] args)
   {
      // "DES", "AES" and "TripleDES" are examples 
      // of recognised symmetric cipher algorithm names
      String cipherName = "AES";

      // use originalText to store the text to be encrypted and decrypted
      String originalText = "I am the Eggman. I am the walrus";

      SecretKey key;          //this will hold the secret key for the algorithm
      byte[] ciphertext;      //this will hold the ciphertext      
      String plaintext = "";  //this will hold the deciphered ciphertext

      System.out.println("\nOriginal text>" + originalText + "<");

      // obtain a key, if the cipherName is recognised, else null
      key = CryptoServices.keyForCipher(cipherName);
      
      //System.out.println("Key Format " + key.getFormat());
      
      //If we successfully obtained a key, use it to encrypt and 
      //to decrypt the originalText string, and compare with the original text
      if (key != null)
      {
         //confirm we obtained a key, and from which provider
         System.out.println("\nObtained a key of class " + key.getClass());
         
         //Display the key
         CryptoServices.displayKey(key);

         // perform the encryption - ciphertext is a byte array
         ciphertext = CryptoServices.encipher(cipherName, originalText, key);
         System.out.println("\nciphertext>" + new String(ciphertext)+ "<");

         // Call the decipher method to perform decryption, using the same SecretKey
         plaintext = CryptoServices.decipher(cipherName, ciphertext, key);
         
         //visual check of the decrypted ciphertext
         System.out.println("\nplaintext>" + plaintext + "<");

         //the plaintext should not be null
         if (plaintext != null)
         {
            System.out.println("\nOriginal equals plaintext? " + plaintext.equals(originalText));
         }
         else
         {            
            System.out.println("\nThe plaintext does not match the original text!");
            System.out.println("Have you completed the method decipher?");
         }
      }
      else //failed to get a key for cipherName
      {
         System.out.println("\nCould not obtain key for cipher " + cipherName);
         System.out.println("That's probably not a recognised cipher name");
      }

   }
}
