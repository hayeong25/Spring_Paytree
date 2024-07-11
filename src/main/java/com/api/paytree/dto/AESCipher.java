package com.api.paytree.dto;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

public class AESCipher {
    private static final Logger logger = LoggerFactory.getLogger(AESCipher.class);
    private final byte[] merKEY = new byte[32];
    private final byte[] merIV = new byte[16];

    public AESCipher(String licenseKey) {
        byte[] b = Base64.decodeBase64(licenseKey.getBytes());
        System.arraycopy(b, 0, merKEY, 0, 32);
        System.arraycopy(b, 0, merIV, 0, 16);
    }

    public String encrypt(String plainText) {
        if(StringUtils.isEmpty(plainText)) return plainText;

        byte[] plainBytes = plainText.getBytes();

        AlgorithmParameterSpec ivSpec = new IvParameterSpec(merIV);
        SecretKeySpec newKey = new SecretKeySpec(merKEY, "AES");
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, newKey, ivSpec);
            return new String(Base64.encodeBase64(cipher.doFinal(plainBytes)));
        }catch(Exception e) {
            logger.error("AES encrypt exception ", e);
            return plainText;
        }
    }

    public String decrypt(String encText) throws NoSuchAlgorithmException, NoSuchPaddingException {
        if(StringUtils.isEmpty(encText)) return encText;

        AlgorithmParameterSpec ivSpec = new IvParameterSpec(merIV);
        SecretKeySpec newKey = new SecretKeySpec(merKEY, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        try {
            byte[] cipherText = Base64.decodeBase64(encText.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, newKey, ivSpec);
            return new String(cipher.doFinal(cipherText));
        }catch(Exception e) {
            logger.error("AES decrypt exception ", e);
            return encText;
        }
    }

    public byte[] decryptBytes(String encText)
            throws InvalidKeyException, InvalidAlgorithmParameterException,
            NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {

        byte[] cipherText = Base64.decodeBase64(encText.getBytes());

        AlgorithmParameterSpec ivSpec = new IvParameterSpec(merIV);
        SecretKeySpec newKey = new SecretKeySpec(merKEY, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, newKey, ivSpec);

        return cipher.doFinal(cipherText);
    }
}