package rsa;

import java.math.BigInteger;
import java.nio.charset.Charset;

public class RSACryptor {

    public static byte[] encrypt(byte[] message, RSAKey key) {
        return (new BigInteger(message)).modPow(key.getKey(), key.getN()).toByteArray();
    }

    public static String encrypt(String message, RSAKey key) {
        return (new BigInteger(message.getBytes())).modPow(key.getKey(), key.getN()).toString();
    }

    public static byte[] decrypt(byte[] message, RSAKey key) {
        return (new BigInteger(message)).modPow(key.getKey(), key.getN()).toByteArray();
    }

    public static String decrypt(String message, Charset charset, RSAKey key) {
        String s = new String(message.getBytes(charset));
        return new String((new BigInteger(s)).modPow(key.getKey(), key.getN()).toByteArray());
    }
}
