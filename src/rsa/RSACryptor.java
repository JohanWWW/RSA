package rsa;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.Base64;

public class RSACryptor {

    public static byte[] encrypt(byte[] message, RSAKey key) {
        byte[] encryptedData = (new BigInteger(message)).modPow(key.getKey(), key.getN()).toByteArray();
        return Base64.getEncoder().encode(encryptedData);
    }

    public static String encrypt(String message, RSAKey key) {
        String encryptedString = (new BigInteger(message.getBytes())).modPow(key.getKey(), key.getN()).toString();
        return Base64.getEncoder().encodeToString(encryptedString.getBytes());
    }

    public static byte[] decrypt(byte[] message, RSAKey key) {
        byte[] decodedData = Base64.getDecoder().decode(message);
        return (new BigInteger(decodedData)).modPow(key.getKey(), key.getN()).toByteArray();
    }

    public static String decrypt(String message, Charset charset, RSAKey key) {
        String decodedString = new String(Base64.getDecoder().decode(message));
        String s = new String(decodedString.getBytes(charset));
        return new String((new BigInteger(s)).modPow(key.getKey(), key.getN()).toByteArray());
    }
}
