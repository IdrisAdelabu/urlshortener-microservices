package com.gbadegesin.encryption.encryption;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/* @author sa_oladipo created on 6/6/22 */

public class AesUtil {


    public byte[] getRandomNonce(int numBytes) {
        byte[] nonce = new byte[numBytes];
        new SecureRandom().nextBytes(nonce);
        return nonce;
    }


    public SecretKey getAESKey(int keysize) throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(keysize, SecureRandom.getInstanceStrong());
        return keyGen.generateKey();
    }

    public SecretKey getAESKeyFromPassword(char[] password, byte[] salt)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password, salt, 65536, 256);
        return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
    }


    public String hex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }


    public String hexWithBlockSize(byte[] bytes, int blockSize) {
        String hex = hex(bytes);
        blockSize = blockSize * 2;
        List<String> result = new ArrayList<>();
        int index = 0;
        while (index < hex.length()) {
            result.add(hex.substring(index, Math.min(index + blockSize, hex.length())));
            index += blockSize;
        }
        return result.toString();
    }

    public byte[] getSaltRandomByte(int numBytes){
        try {
            byte[] nonce = new byte[numBytes];
            for (int i = 0; i < nonce.length; i++) {
                nonce[i] = fetchByte();
            }

            return nonce;
        }
        catch(Exception e){
            System.out.println("Hi Exception Nonce: " + e);
        }
        return null;
    }

    public byte fetchByte(){
        Random random = new Random();
        int i = random.nextInt((100 - 1) + 1) + 1;
        return  ((byte) Math.abs(i));
    }
}
