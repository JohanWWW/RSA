package com.company;

import rsa.RSAKey;
import rsa.RSAKeyGenerator;
import rsa.RSAKeyIO;
import rsa.RSAKeyPair;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        RSAKeyPair keyPair = RSAKeyGenerator.generateKeys(4096);

        var keyIO = new RSAKeyIO();
        keyIO.write("key", keyPair);

        RSAKeyPair readKeyPair = keyIO.read("key");

        System.out.println("PUBLIC KEY");
        System.out.println("Key:\t" + readKeyPair.getPublicKey().getKey());
        System.out.println("n:\t\t" + readKeyPair.getPublicKey().getN());
        System.out.println();
        System.out.println("PRIVATE KEY");
        System.out.println("Key:\t" + readKeyPair.getPrivateKey().getKey());
        System.out.println("n:\t\t" + readKeyPair.getPrivateKey().getN());
    }
}
