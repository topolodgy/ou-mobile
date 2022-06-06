/*
 * Main.java - Signing example. 
 *
 * Signing example. The signature is not certified, but certification could be
 * added. We use a private key for signing and public key for verification.
 * In practice the signed message and public key may have been sent over a 
 * network and wrapped in some way.
 *
 * Created on 23 Feb 2016, 11:59
 *
 * @author tm352 Module Team.
 */
package task;

import java.security.KeyPair;

/**
 * The Main class 
 */
public class Main
{
   /**
    * The main method acts as both sender and receiver of a signed message
    *
    * @param args
    */
   public static void main(String[] args)
   {
      // Our example uses the Digital Signature Algorithm
      final String cipherName = "DSA";
      final String signatureName = "SHA256withDSA";

      // 1. Sender's message
      String message
              = "Dear Eggman, I am having a great time in Majorca. "
              + "Check out these tangerine dreams!"
              + "Wish you were here."
              + "Pink would love this place."
              + "Yours Sincerely, the Walrus";

      // 2. Sender generates a pair of related keys - one is public
      KeyPair walrusKeys = SigningServices.getKeyPair(cipherName);

      // You could display information about the walrusKeys, e.g.
      // System.out.println("public "+ walrusKeys.getPublic());        
      // 3. Generate a signature
      // In practice, this signature would be sent to the recipient along
      // with the message so that the recipient can verify the signature.
      byte[] signature = SigningServices.signMessage(message, walrusKeys.getPrivate(), signatureName);

      // To check that the signature is okay.
      // We assume that the receiver has received the message, and the signature
      // and now wants to verify the message:     
      boolean okay = SigningServices.verifyMessage(message, walrusKeys.getPublic(), signatureName, signature);

      System.out.println("\nWalrus message correctly signed? " + okay);
   }
}
