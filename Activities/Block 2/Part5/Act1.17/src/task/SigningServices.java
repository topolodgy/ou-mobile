/*
 * SigningServices.java
 *
 * Created on 21 Feb 2016, 14:38
 *
 * @author tm352 Module Team.
 */
package task;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;

/**
 * Utility class with methods to demonstrate signing * 
 */
public class SigningServices
{
   /**
    * Generate an asymmetric key pair
    *
    * @param cipher the name of the cipher to use
    * @return A KeyPair for the named cipher, or null if not possible
    */
   public static KeyPair getKeyPair(String cipher)
   {
      KeyPair keyHolder;
      try
      {
         KeyPairGenerator kpg = KeyPairGenerator.getInstance(cipher);        
         
         keyHolder = kpg.genKeyPair();

         //show the provider of this service
         //System.out.println("keys provided by " + kpg.getProvider());
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
    * Return a signature for a particular algorithm name if that name is valid,
    * otherwise return null
    *
    * @param algorithm Signing algorithm name
    * @return Signature instance for the named algorithm, or null if fails
    */
   public static Signature getSignature(String algorithm)
   {
      Signature sig;

      try
      {
         sig = Signature.getInstance(algorithm);
         //System.out.println("Signature provided by " + sig.getProvider());
      }
      catch (NoSuchAlgorithmException ex)
      {
         System.out.println(ex);
         sig = null;
      }

      return sig;
   }

   /**
    * The sender (Walrus) will execute code similar to this //to generate the
    * signature, which is returned by this method (else null)
    *
    * @param message the message to be signed
    * @param key the private key used to generate the signature
    * @param sigName the name of the signing algorithm
    *
    * @return a byte[] containing a signature for the message
    */
   public static byte[] signMessage(String message, PrivateKey key, String sigName)
   {
      Signature sig;
      byte[] signature;

      //create a Signature object
      sig = SigningServices.getSignature(sigName);

      //show what we've got so far - this signature has not been initialised for signing
      System.out.println("Walrus signing with " + sig);

      try
      {       
         //initialize the signature so that it can be used for signing messages
         sig.initSign(key); //the key might be invalid

         //now observe that the signature object is ready for use in this context
         System.out.println("Walrus signing with " + sig);

         try //produce the signature
         {
            sig.update(message.getBytes()); //store the message to be signed
            signature = sig.sign();     //signature for the message 
            System.out.println("Walrus generated a signature ");
         }
         catch (SignatureException ex)
         {
            System.out.println(ex);
            signature = null;
         }
      }
      catch (InvalidKeyException ex)
      {
         System.out.println(ex);
         signature = null;
      }

      return signature;
   }

   /**
    * The receiver side (EggMan) has: A message (here a string, but in practice
    * this may be in some other form). A partner key to one assumed to have been
    * used to sign the message. The name of the signing algorithm. The signature
    * (array of bytes) received with the message
    *
    * This method returns true if the signature is verified, otherwise it
    * returns false.
    *
    * @param message the message the receiver received
    * @param key public key partner to the key the sender used to sign the
    * message
    * @param sigName the name of the signing algorithm the sender used
    * @param theSignature the signature that was received, for verification
    * @return true if signature was verified, otherwise false
    */
   public static boolean verifyMessage(String message, PublicKey key, String sigName, byte[] theSignature)
   {
      boolean result;
      Signature sig;

      // create a signature object
      sig = SigningServices.getSignature(sigName);

      // show what we've got so far
      System.out.println("\nEggman verifying with " + sig);

      try
      {
         // initialize the signature object to perform verification
         sig.initVerify(key);
         System.out.println("Eggman verifying with " + sig);

         // update the signature object with the message to be verified   
         try
         {
            // update the signature object with the message to be verified
            sig.update(message.getBytes());

            if (sig.verify(theSignature)) //verify check
            {
               System.out.println("Signature has been verified.");
               result = true;
            }
            else
            {
               System.out.println("Message was sent by an impostor!");
               result = false;
            }
         }
         catch (SignatureException ex)
         {
            System.out.println(ex);
            result = false;
         }
      }
      catch (InvalidKeyException ex)
      {
         System.out.println(ex);
         result = false;
      }

      return result;
   }

}
