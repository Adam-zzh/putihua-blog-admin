package com.smallhua.org.encrypt;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * 〈一句话功能简述〉<br>
 * 〈Rsa工具类〉
 *
 * @author ZZH
 * @create 2022/12/23
 * @since 1.0.0
 */
class RSAUtil{
    private static int MAX_ENCRYPT_BLOCK = 117;
    private static int MAX_DECRYPT_BLOCK = 128;

    public static KeyPair getKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(1024);
        return keyPairGenerator.generateKeyPair();
    }

    /**
     * 获得私钥
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(String privateKey) throws Exception {
        KeyFactory rsa = KeyFactory.getInstance("RSA");
        byte[] bytes = Base64.decodeBase64(privateKey.getBytes());
        return rsa.generatePrivate(new PKCS8EncodedKeySpec(bytes));
    }

    /**
     * 获得公钥
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static PublicKey getPublicKey(String publicKey) throws Exception {
        KeyFactory rsa = KeyFactory.getInstance("RSA");
        byte[] bytes = Base64.decodeBase64(publicKey.getBytes());
        return rsa.generatePublic(new X509EncodedKeySpec(bytes));
    }

    /**
     * 如果加密内容太长会报错 rsa分段加密 每一段大小117
     * @param data
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static String encrypt(String data, PublicKey publicKey) throws Exception {
        Cipher cp = Cipher.getInstance("RSA");
        cp.init(Cipher.ENCRYPT_MODE, publicKey);
        int inputLen = data.getBytes().length;

        // 对数据进行分段加密
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int offset = 0, i = 0;
        byte[] cache;

        while(inputLen - offset > 0){
            if(inputLen - offset > MAX_ENCRYPT_BLOCK){
                cache = cp.doFinal(data.getBytes(), offset, MAX_ENCRYPT_BLOCK);
            } else{
                cache = cp.doFinal(data.getBytes(), offset, inputLen - offset);
            }
            bos.write(cache, 0, cache.length);
            i++;
            offset = MAX_ENCRYPT_BLOCK*i;
        }

        byte[] encryptData = bos.toByteArray();
        bos.close();

        // 使用Base64对加密数据进行编码
        return new String(Base64.encodeBase64(encryptData));
    }

    /**
     * 如果解密内容太长会报错 分段解密 每一段128
     * @param data
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static String decrypt(String data, PrivateKey privateKey) throws Exception {
        Cipher cp = Cipher.getInstance("RSA");
        cp.init(Cipher.DECRYPT_MODE, privateKey);

        // Base64解码加密内容
        byte[] dataBytes = Base64.decodeBase64(data);

        // 对加密内容进行分段解密
        int offset = 0, i = 0, len = dataBytes.length;
        byte[] caches;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        while (len - offset > 0){
            if(len - offset > MAX_DECRYPT_BLOCK){
                caches = cp.update(dataBytes, offset, MAX_DECRYPT_BLOCK);
            } else {
                caches = cp.update(dataBytes, offset, len - offset);
            }
            bos.write(caches, 0, caches.length);

            bos.close();
            i++;
            offset = i* MAX_DECRYPT_BLOCK;
        }

        return new String(bos.toByteArray(), "UTF-8");
    }

    /**
     * 生成签名
     * @param data
     * @param privateKey
     * @return
     * @throws Exception
     */
    public String sign(String data, PrivateKey privateKey) throws Exception{
        byte[] encoded = privateKey.getEncoded();
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey key = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(encoded));

        // 生成签名
        Signature signature = Signature.getInstance("MD5withRSA");
        signature.initSign(key);
        signature.update(data.getBytes());
        return new String(Base64.encodeBase64(signature.sign()));
    }

    /**
     * 进行验签
     * @param srcData 原始数据
     * @param publickey 公钥
     * @param sign 签名信息
     * @return
     * @throws Exception
     */
    public static boolean verify(String srcData, PublicKey publickey, String sign) throws Exception{

        // 生成RSA公钥
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey key = keyFactory.generatePublic(new X509EncodedKeySpec(publickey.getEncoded()));


        // 将签名进行解码
        byte[] signBytes = Base64.decodeBase64(sign);
        Signature signature = Signature.getInstance("MD5withRSA");
        signature.initVerify(key);
        signature.update(srcData.getBytes());
        return signature.verify(signBytes);
    }

    /* 对文件加密、解密、验签以及加签 */


}