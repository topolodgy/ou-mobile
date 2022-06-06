/**
 * ACryptoServices2.java
 *
 * Completed ACryptoServices class with a decipher method
 * and generalised encipher and decipher methods (they
 * take Key as parameter rather than subclasses of Key).
 *
 * Created on 23 Feb 2016, 14:38
 *
 * @author tm352 Module Team.
 */
package task;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;

/**
 * Asymmetric encryption utility methods 
 */
public class ACryptoServices
{
   /**
    * This method returns a KeyPair containing public and private keys for the
    * named algorithm (or null, if there is no such algorithm)
    *
    * @param cipher the name of the cipher we want keys for
    * @return a KeyPair for the named cipher
    */
   public static KeyPair keysForCipher(String cipher)
   {
      KeyPair keyHolder;

      try
      {
         // any provider will do
         KeyPairGenerator kpg = KeyPairGenerator.getInstance(cipher);
         keyHolder = kpg.genKeyPair();

         // show the provider of this service
         System.out.println("keys provided by " + kpg.getProvider());
      }
      catch (NoSuchAlgorithmException ex)
      {
         System.out.println(ex);
         keyHolder = null;
      }

      return keyHolder;
   }
   
   /**
    * A method to print out the byte array representation of a SecretKey.
    * Remember that this is a 56 bit key, stored in 8 bytes (which is 64 bits).
    * The remaining 8 bits are used for parity checks.
    * http://www.britannica.com/topic/Data-Encryption-Standard
    *
    * @param s the SecretKey to be displayed
    */
   public static void displayKey(Key s)
   {
      System.out.print("\nKey byte values: ");
      byte[] b = s.getEncoded();

      for (byte x : b)
      {
         System.out.print(x + " ");
      }

      System.out.println();
   }

   /**
    * This method is provided to show an example of asymmetric encryption 
    *
    * @param cipher the cipher name we want to use
    * @param text the plaintext to be enciphered
    * @param key the Key to use to encipher the plaintext
    * @return The enciphered text as a byte array
    */
   public static byte[] encipher(String cipher, String text, Key key)
   {
      byte[] ciphertext;

      try
      {
         Cipher ecipher = Cipher.getInstance(cipher);

         ecipher.init(Cipher.ENCRYPT_MODE, key);

         ciphertext = ecipher.doFinal(text.getBytes());
      }
      catch (Exception ex)
      {
         System.out.println(ex);
         ciphertext = null;
      }

      return ciphertext;
   }

   /**
    * Complete the decipher method. 
    *
    * @param cipher the cipher name to use
    * @param ciphertext the plaintext to be deciphered
    * @param key the Key to use to decipher the plaintext
    * @return the plaintext as a String
    */
   public static String decipher(String cipher, byte[] ciphertext, Key key)
   {
      String result;
      
      try
      {
         Cipher dcipher = Cipher.getInstance(cipher);
         
         dcipher.init(Cipher.DECRYPT_MODE, key);
         
         byte[] plaintext = dcipher.doFinal(ciphertext);
         
         result = new String(plaintext);
      }
      catch (Exception ex)
      {
         System.out.println(ex);
         result = null;
      }

      return result;

   }
}
