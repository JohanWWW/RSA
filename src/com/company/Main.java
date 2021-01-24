package com.company;

import rsa.RSACryptor;
import rsa.RSAKey;
import rsa.RSAKeyGenerator;
import rsa.RSAKeyIO;
import rsa.RSAKeyPair;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        menu();
    }

    private static void menu() {
        while (true) {
            System.out.println("1. Create key pair");
            System.out.println("2. Encrypt string");
            System.out.println("3. Decrypt string");
            System.out.println("4. Encrypt text file");
            System.out.println("5. Decrypt text file");
            System.out.println("6. Encrypt binary");
            System.out.println("7. Decrypt binary");
            System.out.println();
            System.out.println("9. Exit");

            String choice = readString("Make a selection: ");

            switch (choice) {
                case "1" -> generateKeys();
                case "2" -> encryptString();
                case "3" -> decryptString();
                case "4" -> encryptTextFile();
                case "5" -> decryptTextFile();
                case "6" -> encryptBinary();
                case "7" -> decryptBinary();
                case "9" -> System.exit(0);
            }
        }

    }

    private static void generateKeys() {
        var keyIO = new RSAKeyIO("keys");

        String keyPairName = readString("Please enter key pair name: ");
        int keySize = readInt("Please enter bit size: ");

        RSAKeyPair keyPair = RSAKeyGenerator.generateKeys(keySize);

        try {
            keyIO.write(keyPairName, keyPair);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void encryptString() {
        String message = readString("Please enter a string to encrypt: ");
        RSAKey key = getKeyMenu("Specify a key to use for encryption");

        String encryptedMessage = RSACryptor.encrypt(message, key);
        copyToClipboard(encryptedMessage);

        System.out.println("Encrypted message: " + encryptedMessage);
        System.out.println("(copied to clipboard)");
    }

    private static void decryptString() {
        String message = readString("Please enter an encrypted string to decrypt: ");
        RSAKey key = getKeyMenu("Specify a key to use for decryption");

        String decryptedMessage = RSACryptor.decrypt(message, StandardCharsets.UTF_8, key);

        System.out.println("Decrypted message: " + decryptedMessage);
    }

    private static void encryptTextFile() {
        File file = getFileMenu("Select file");

        String text;
        try {
            text = Files.readString(Paths.get("IO/input/" + file.getName()));
        }
        catch (IOException e) {
            e.printStackTrace();
            return;
        }

        RSAKey key = getKeyMenu("Specify key to use for encrypting the text file");

        String encryptedText = RSACryptor.encrypt(text, key);

        try {
            Path writtenToPath =
                    Files.writeString(Paths.get("IO/output/" + file.getName()), encryptedText);
            System.out.println("Written to path: " + writtenToPath.toString());
            Files.delete(Paths.get("IO/input/" + file.getName()));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void decryptTextFile() {
        File file = getFileMenu("Select file");

        String text;
        try {
            text = Files.readString(Paths.get("IO/input/" + file.getName()));
        }
        catch (IOException e) {
            e.printStackTrace();
            return;
        }

        RSAKey key = getKeyMenu("Specify key to use for decrypting the text file");

        String decryptedText = RSACryptor.decrypt(text, StandardCharsets.UTF_8, key);

        try {
            Path writtenToPath = Files.writeString(Paths.get("IO/output/" + file.getName()), decryptedText);
            System.out.println("Written to path: " + writtenToPath.toString());
            Files.delete(Paths.get("IO/input/" + file.getName()));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void encryptBinary() {
        File file = getFileMenu("Select file");

        byte[] data;
        try {
            data = Files.readAllBytes(Paths.get("IO/input/" + file.getName()));
        }
        catch (IOException e) {
            e.printStackTrace();
            return;
        }

        RSAKey key = getKeyMenu("Specify key to use for encrypting the binary file");

        byte[] encryptedData = RSACryptor.encrypt(data, key);

        try {
            Path writtenToPath = Files.write(Paths.get("IO/output/" + file.getName()), encryptedData);
            System.out.println("Written to path: " + writtenToPath.toString());
            Files.delete(Paths.get("IO/input/" + file.getName()));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void decryptBinary() {
        File file = getFileMenu("Select file");

        byte[] data;
        try {
            data = Files.readAllBytes(Paths.get("IO/input/" + file.getName()));
        }
        catch (IOException e) {
            e.printStackTrace();
            return;
        }

        RSAKey key = getKeyMenu("Specify key to use for decrypting the file");

        byte[] decryptedData = RSACryptor.decrypt(data, key);

        try {
            Path writtenToPath = Files.write(Paths.get("IO/output/" + file.getName()), decryptedData);
            System.out.println("Written to path: " + writtenToPath.toString());
            Files.delete(Paths.get("IO/input/" + file.getName()));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static RSAKey getKeyMenu(String title) {
        var keyIO = new RSAKeyIO("keys");

        System.out.println(title);

        RSAKey key = null;
        while (key == null) {
            String keyName = readString("Please enter key name: ");
            try {
                key = keyIO.readKey(keyName);
            }
            catch (IOException | ClassNotFoundException e) {
                System.err.println("An error occurred. Please try again.");
                e.printStackTrace();
            }
        }

        return key;
    }

    private static File getFileMenu(String title) {
        var file = new File("IO/input");

        System.out.println(title);

        File[] children = file.listFiles();
        for (int i = 0; i < children.length; i++) {
            System.out.printf("[%s] -> %s\n", i, children[i].getName());
        }

        int index = readInt("Please enter index: ");

        return children[index];
    }

    private static void copyToClipboard(String text) {
        var selection = new StringSelection(text);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
    }

    private static String readString() {
        return scanner.nextLine();
    }

    private static String readString(String message) {
        System.out.print(message);
        return readString();
    }

    private static int readInt() {
        return scanner.nextInt();
    }

    private static int readInt(String message) {
        System.out.print(message);
        return readInt();
    }

    private static byte[] readFile(String filePath) throws IOException {
        var fileInputStream = new FileInputStream(filePath);
        byte[] data = fileInputStream.readAllBytes();
        fileInputStream.close();
        return data;
    }

    private static void writeFile(String filePath, byte[] data) throws IOException {
        var fileOutputStream = new FileOutputStream(filePath);
        fileOutputStream.write(data);
        fileOutputStream.flush();
        fileOutputStream.close();
    }
}
