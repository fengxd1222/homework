package io.github.kimmking.gateway.token;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * token校验工具类 aes加解密
 */
public class AESTokenHandler implements TokenHandler{
    //key和secret需要提前约定好 可以使用配置文件配置
    private String key = "012345678abcdefg";
    private String secret = "abcdefg012345678";

    /*public AESTokenHandler(String key, String secret) {
        this.key = key;
        this.secret = secret;
    }*/

    private AESTokenHandler() {
    }

    private static class SingletonTokenHandler{
        private static final AESTokenHandler TOKEN_HANDLER = new AESTokenHandler();
    }

    public static AESTokenHandler getInstance(){
        return SingletonTokenHandler.TOKEN_HANDLER;
    }


    @Override
    public String encrypt(String beforeEncryptStr) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] raw = key.getBytes();
            SecretKeySpec secretKeySpec = new SecretKeySpec(raw, "AES");
            IvParameterSpec iv = new IvParameterSpec(secret.getBytes());//向量iv，可增加加密算法的强度
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, iv);
            byte[] encrypted = cipher.doFinal(beforeEncryptStr.getBytes("utf-8"));
            return new BASE64Encoder().encode(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String decrypt(String beforeDecryptStr) {
        try {
            byte[] raw = key.getBytes("ASCII");
            SecretKeySpec secretKeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(secret.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, iv);
            byte[] encrypted1 = new BASE64Decoder().decodeBuffer(beforeDecryptStr);//先用base64解密
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original,"UTF-8");
            return originalString;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean checkToken(String token) {
        return decrypt(token)!=null;
    }
}
