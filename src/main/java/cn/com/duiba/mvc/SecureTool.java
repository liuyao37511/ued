package cn.com.duiba.mvc;

import cn.com.duiba.utils.BlowfishUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Created by liuyao on 2017/2/1.
 */
public class SecureTool {
    private static String loginKey;

    public static String decryptWriterCookie(String data){
        return BlowfishUtils.decryptBlowfish(data, loginKey);
    }

    public static String encryptWriterCookie(String data){
        return BlowfishUtils.encryptBlowfish(data,loginKey);
    }

    public void setLoginKey(String loginKey) {
        SecureTool.loginKey = loginKey;
    }
}
