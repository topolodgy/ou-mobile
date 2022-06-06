/**
 * Main.java
 * Completed class to use asymmetric cryptographic services
 * This version demonstrates that the keys can be swapped.
 * 
 * To run the main method in this class, right-click and
 * select Run File.
 *
 * Created on 23 Feb 2016, 11:59
 *
 * @author tm352 Module Team.
 */
package task;

import java.security.Key;
import java.security.KeyPair;

/**
 * The Main class
 */
public class Main
{
   /**
    * The main method uses RSA to perform public-private key (asymmetric) encryption
    * @param args
    */
   public static void main(String[] args)
   {
      // originalText stores the string to be encrypted and decrypted
      String originalText = "I am the Eggman. I am the walrus";

      System.out.println("original text>" + originalText + "<\n");

      String cipherName = "RSA";

      // 1. Variables 
      KeyPair keys;         // the keys for the algorithm

      // 2. Obtain a key pair
      keys = ACryptoServices.keysForCipher(cipherName);    
      
      //Test 1 - first order of keys is public and private
      System.out.println("\n*************** TEST 1 *****************");
      Main.test(cipherName, originalText, keys.getPublic(), keys.getPrivate());
      
      //Test 2 - swap order of keys to private then public
      System.out.println("\n*************** TEST 2 *****************");
      Main.test(cipherName, originalText, keys.getPrivate(), keys.getPublic());
   }
   
   /**
    * Method to test ACryptoServices encipher and decipher methods,
 which take Keys as parameters (rather than PublicKey and PrivateKey)
    * 
    * @param cipherName the name of the cipher
    * @param originalText the text to be encrypted
    * @param keyOne one of a public-private key pair
    * @param keyTwo the partner key of the public-private key pair
    */
   private static void test(String cipherName, String originalText, Key keyOne, Key keyTwo)
   {
      // 3. Perform the encryption and display the result
      byte[] ciphertext = ACryptoServices.encipher(cipherName, originalText, keyOne);
      System.out.println("\nciphertext>" + new String(ciphertext) + "<");

      // 4. Perform decryption 
      String plaintext = ACryptoServices.decipher(cipherName, ciphertext, keyTwo);

      System.out.println("\nplaintext " + plaintext);
      
      //5. Compare with original text 
      System.out.println("\nOriginal equals plaintext? " + plaintext.equals(originalText));
   }
}
