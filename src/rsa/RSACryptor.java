package rsa;

import java.math.BigInteger;

public class RSACryptor {

    public static byte[] encrypt(byte[] message, RSAKey key) {
        return (new BigInteger(message)).modPow(key.getKey(), key.getN()).toByteArray();
    }

    public static byte[] decrypt(byte[] message, RSAKey key) {
        return (new BigInteger(message)).modPow(key.getKey(), key.getN()).toByteArray();
    }
}
