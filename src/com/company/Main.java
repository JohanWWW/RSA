package com.company;

import rsa.RSACryptor;
import rsa.RSAKeyGenerator;
import rsa.RSAKeyPair;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        RSAKeyPair keyPair = RSAKeyGenerator.generateKeys(4096);

        byte[] encrypted = RSACryptor.encrypt("Hello, World!".getBytes(), keyPair.getPublicKey());
        System.out.println(new String(encrypted));

        byte[] decrypted = RSACryptor.decrypt(encrypted, keyPair.getPrivateKey());
        System.out.println(new String(decrypted));
    }
}
