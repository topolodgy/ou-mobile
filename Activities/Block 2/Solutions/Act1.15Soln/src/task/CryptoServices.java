/*
 * CryptoServices.java 
 *
 * Provides static methods (complete version)
 *
 * Created on 21 July 2016
 *
 * @author tm352 Module Team.
 */
package task;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * Utility methods
 */
public class CryptoServices
{
   /**
    * A method to print out the byte array representation of a SecretKey.
    * Remember that this is a 56 bit key, stored in 8 bytes (which is 64 bits).
    * The remaining 8 bits are used for parity checks.
    * http://www.britannica.com/topic/Data-Encryption-Standard
    *
    * @param s the SecretKey to be displayed
    */
   public static void displayKey(SecretKey s)
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
    * Generate a key for DES using a key string. The key string can be reused to
    * reproduce the same SecretKey.
    *
    * @param keyString a string to use to generate the SecretKey
    * @return a SecretKey based on the keyString as source of randomness
    */
   public static SecretKey getFixedKey(String keyString)
   {
      try
      {
         SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");

         SecretKey sk = skf.generateSecret(new DESKeySpec(keyString.getBytes()));

         return sk;
      }
      catch (NoSuchAlgorithmException | InvalidKeyException | InvalidKeySpecException ex)
      {
         System.out.println(ex);
      }

      return null;
   }

   /**
    * This encipher method shows an example of encryption.
    *
    * @param cipher the cipher name to use
    * @param text the text to be encrypted
    * @param key the key to encipher the text with
    * @return the encrypted text as a byte array
    */
   public static byte[] encipher(String cipher, String text, SecretKey key)
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
         ex.printStackTrace();
         ciphertext = null;
      }

      return ciphertext;
   }

   /**
    * The completed decipher method to return the plaintext or null if an
    * exception occurs
    *
    * @param cipher the name of the cipher to use in deciphering the cipher text
    * @param ciphertext the cipher text to be decrypted
    * @param key the secret key to use to decipher the ciphertext
    * @return the deciphered ciphertext
    */
   public static String decipher(String cipher, byte[] ciphertext, SecretKey key)
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
