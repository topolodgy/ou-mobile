/**
 * CryptoServices.java
 *
 * This class provides static methods, one of which is incomplete.
 * Solution code is available in task2/CryptoServices2.
 *
 * Created on 21 February 2016
 *
 * @author tm352 Module Team.
 */
package task1;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/**
 * Utility methods
 */
public class CryptoServices
{
   /**
    * Returns a SecretKey for the named algorithm (or null, if there is no such
    * algorithm). This method makes use of a KeyGenerator.
    *
    * @param cipher The name of the cipher to return a SecretKey for
    * @return a SecretKey for the named cipher, or null if no such algorithm
    */
   public static SecretKey keyForCipher(String cipher)
   {
      SecretKey sk;
      KeyGenerator kg;

      try
      {
         kg = KeyGenerator.getInstance(cipher);

         //System.out.println("key provider " + kg.getProvider());
         sk = kg.generateKey();
      }
      catch (NoSuchAlgorithmException ex)
      {
         System.out.println("Exception " + ex);
         sk = null;
      }

      return sk;
   }

   /**
    * A method to print out the byte array representation of a SecretKey.
    * Remember that (for DES) this is a 56 bit key, stored in 8 bytes (which is
    * 64 bits). The remaining 8 bits are used for parity checks.
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
      catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex)
      {
         System.out.println("Exception " + ex);
         ciphertext = null;
      }

      return ciphertext;
   }

   /**
    * The decipher method is to be completed to return the plaintext or null if
    * an exception occurs
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

      return result; // to be changed
   }
}
