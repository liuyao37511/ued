package cn.com.duiba.utils;

import org.apache.commons.codec.binary.Base64;

import java.security.MessageDigest;

/**
 * Created by liuyao on 2017/2/3.
 */
public class MessageDigestUtils {

    public static String SHA(String str){
        return messageDigest(str,"SHA");
    }

    public static String MD5(String str){
        return messageDigest(str,"MD5");
    }

    private static String messageDigest(String str,String alg){
        try {
            MessageDigest md = MessageDigest.getInstance(alg);
            md.update(str.getBytes("UTF-8"));

            return Base64.encodeBase64String(md.digest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
