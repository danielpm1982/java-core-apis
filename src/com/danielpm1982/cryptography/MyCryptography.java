package com.danielpm1982.cryptography;
import javax.crypto.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.logging.Logger;

public class MyCryptography {
    final static String STRING_TO_ENCRYPT = "Hello World !";
    final static String ALGORITHM = "AES";
    final static int KEY_SIZE = 256;
    final static Logger logger = Logger.getLogger(MyCryptography.class.getName());
    public static void execute(){
        try {
            SecretKey secretKey = getSecretKeyAES();
            String stringEncrypted = encryptAES(STRING_TO_ENCRYPT, secretKey);
            String stringDecrypted = decryptAES(stringEncrypted, secretKey);
            System.out.println(STRING_TO_ENCRYPT+" => encrypted to => "+stringEncrypted+
                    " => decrypted to => "+stringDecrypted);
            System.out.println(secretKey.getAlgorithm()+" algorithm ("+KEY_SIZE+" bits) used to create the secretKey !");
        } catch (Exception e) {
            logger.severe("The following Exception has been thrown: "+ e.getClass().getName());
        }
    }
    private static SecretKey getSecretKeyAES() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
        System.out.println(keyGenerator.getProvider().getInfo());
        System.out.println("Generating key...");
        keyGenerator.init(KEY_SIZE);
        SecretKey secretKey = keyGenerator.generateKey();
        System.out.println("Key successfully generated !");
        return secretKey;
    }
    @SuppressWarnings("SameParameterValue")
    private static String encryptAES(String decryptedString, SecretKey secretKey) throws NoSuchPaddingException,
            NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        System.out.println("Encrypting String...");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedStringByteArray = cipher.doFinal(decryptedString.getBytes());
        String encryptedString = Base64.getEncoder().encodeToString(encryptedStringByteArray);
        System.out.println("String successfully encrypted !");
        return encryptedString;
    }
    private static String decryptAES(String encryptedString, SecretKey secretKey) throws NoSuchPaddingException,
            NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        System.out.println("Decrypting String...");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] encryptedStringByteArray = Base64.getDecoder().decode(encryptedString);
        byte[] decryptedStringByteArray = cipher.doFinal(encryptedStringByteArray);
        System.out.println("String successfully decrypted !");
        return new String(decryptedStringByteArray);
    }
    void main(){
        execute();
    }
}

/*
For basic Java encryption, we may use the Cypher class from the javax.crypto package. Both for encryption as for
decryption, we first get an instance, passing the algorithm we want it to use, then we set the init() state of
the encryption/decryption instance, passing the MODE (e.g. DECRYPT_MODE, ENCRYPT_MODE, etc.) and a secret_key to
be used, and then, through the doFinal() method, we encrypt or encrypt the message we want, passing it as a byte[]
format. For this, we may use the Base64 encoder and decoder to help. If we're asking the doFinal() to encrypt a
message, we must pass the message as a byte[] format, and the returned encrypted message will also be of byte[] type,
having us to use Base64 encoder to encode it to String, if we want to see the String representation of that byte[]
encrypted message. The other way around also applies. If we're asking the doFinal() to decrypt a message, we must
use the Base64 decoder to decode that String message to byte[] format, and then pass it to the doFinal(); the
returned decrypted message will also be of byte[] type, having us to use Base64 encoder to encode it to String, if
we want to see the String representation of that byte[] decrypted message. Before using the Cypher init() and doFinal(),
we first gotta have already the SecretKey for the cryptography, which can be easily created using the KeyGenerator class.
The SecretKey for decryption must be the same used to encrypt the message, as well as the message, which also must be
the same (in byte[]), with no corruption or modifications; the same for the algorithm; otherwise BadPaddingException
will be thrown. We must have already decided which message to encrypt or decrypt, the algorithm type - for both encryption
and decryption - (there are many options) and the keySize (e.g. 128, 256, 2048 bits) of the SecretKey that is going to be
created and used at the encryption.

So, in the above example, we've created three auxiliary methods for:
- creating and returning a SecretKey, using the AES cryptography algorithm and the keySize chosen for that key (256 bits);
- encrypting a message, using the AES algorithm and the previously generated SecretKey, through the Cypher class, using
also the Base64 class to encode to, or decode from, String the byte[] type messages;
- decrypting a previously encoded message, using exactly the same encoded message, the same SecretKey, the Cypher class
and the Base64, as well.

At the execute() method we then simply get a SecretKey through the getSecretKeyAES() method, encrypt the "Hello World"
message through the encryptAES() method and decrypt the latter back to the original message "Hello World" through the
decryptAES() method.

For other cryptography methods, as for RSA, there will be different ways to implement the code. AES uses symmetric
cryptography, having only one SecretKey both for encryption as for decryption, while RSA is asymmetric, having a pair
of SecretKeys, one public for encryption and another private for decryption. There are many cryptography algorithms to
choose from, depending on the use case and level of security needed.
*/
