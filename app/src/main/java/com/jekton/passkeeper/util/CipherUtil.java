package com.jekton.passkeeper.util;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author luojitong
 */
public class CipherUtil {

    public static byte[] encrypt(byte[] data, byte[] key, byte[] iv)
            throws NoSuchPaddingException,
                   NoSuchAlgorithmException,
                   InvalidAlgorithmParameterException,
                   InvalidKeyException,
                   BadPaddingException,
                   IllegalBlockSizeException {

        return doCipher(data, key, iv, Cipher.ENCRYPT_MODE);
    }


    public static byte[] decrypt(byte[] data, byte[] key, byte[] iv)
            throws NoSuchPaddingException,
                   NoSuchAlgorithmException,
                   InvalidAlgorithmParameterException,
                   InvalidKeyException,
                   BadPaddingException,
                   IllegalBlockSizeException {

        return doCipher(data, key, iv, Cipher.DECRYPT_MODE);
    }


    private static byte[] doCipher(byte[] data, byte[] key, byte[] iv, int mode)
            throws NoSuchPaddingException,
                   NoSuchAlgorithmException,
                   InvalidAlgorithmParameterException,
                   InvalidKeyException,
                   BadPaddingException,
                   IllegalBlockSizeException {

        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");

        cipher.init(mode, keySpec, ivSpec);
        return cipher.doFinal(data);
    }


    public static byte[] md5Bytes(String password, byte[] salt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(salt);
        return md.digest(password.getBytes());
    }


    private CipherUtil() {
        throw new AssertionError("Don't instantiate this class");
    }
}
