package com.example.passkeeper;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class FileSecurity {
    private static SecretKeySpec secretKey;
    private static byte[] key;

    private static void setKey(String mySecretKey) {
        MessageDigest sha = null;
        try {
            key = mySecretKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-256");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static boolean encrypt(String fileName, String content, String secretKey)
    {
        try
        {
            setKey(secretKey);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, FileSecurity.secretKey);
            byte[] iv = cipher.getIV();

            FileOutputStream fileOut = new FileOutputStream(fileName);
            CipherOutputStream cipherOut = new CipherOutputStream(fileOut, cipher);

            fileOut.write(iv);
            cipherOut.write(content.getBytes());
            return true;
        }
        catch (Exception e)
        {
            System.out.println("Error while encrypting: " + e.toString());
            return false;
        }
    }

    String decrypt(String fileName, String secretKey) {
        String content = null;

        try {
            setKey(secretKey);

            FileInputStream fileIn = new FileInputStream(fileName);
            byte[] fileIv = new byte[16];
            fileIn.read(fileIv);

            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, FileSecurity.secretKey, new IvParameterSpec(fileIv));

            CipherInputStream cipherIn = new CipherInputStream(fileIn, cipher);
            InputStreamReader inputReader = new InputStreamReader(cipherIn);
            BufferedReader reader = new BufferedReader(inputReader);

            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            content = sb.toString();
        }catch (Exception e) {
            System.out.println("Error while decrypting: " + e.toString());
        }

        return content;
    }

}
