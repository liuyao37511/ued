package cn.com.duiba.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.HashMap;

/**
 * Created by liuyao on 2017/1/31.
 */
public class BlowfishUtils {
    private static final String CIPHER_NAME = "Blowfish/CFB8/NoPadding";
    private static final String KEY_SPEC_NAME = "Blowfish";
    private static final ThreadLocal<HashMap<String, BlowfishUtils>> pool = new ThreadLocal();
    private Cipher enCipher;
    private Cipher deCipher;
    private String key;

    private BlowfishUtils(String key) {
        try {
            this.key = key;
            String e = StringUtils.substring(DigestUtils.md5Hex(key), 0, 8);
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "Blowfish");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(e.getBytes());
            this.enCipher = Cipher.getInstance("Blowfish/CFB8/NoPadding");
            this.deCipher = Cipher.getInstance("Blowfish/CFB8/NoPadding");
            this.enCipher.init(1, secretKeySpec, ivParameterSpec);
            this.deCipher.init(2, secretKeySpec, ivParameterSpec);
        } catch (Exception var5) {
            var5.printStackTrace();
        }

    }

    public static String encryptBlowfish(String str, String key) {
        return getInstance(key).encrypt(str);
    }

    public static String decryptBlowfish(String str, String key) {
        return getInstance(key).decrypt(str);
    }

    public static String encryptBlowfish(String s) {
        return encryptBlowfish(s, "abc");
    }

    public static String decryptBlowfish(String s) {
        return decryptBlowfish(s, "abc");
    }

    private static BlowfishUtils getInstance(String key) {
        HashMap keyMap = (HashMap)pool.get();
        if(keyMap == null || keyMap.isEmpty()) {
            keyMap = new HashMap();
            pool.set(keyMap);
        }

        BlowfishUtils instance = (BlowfishUtils)keyMap.get(key);
        if(instance == null || !StringUtils.equals(instance.key, key)) {
            instance = new BlowfishUtils(key);
            keyMap.put(key, instance);
        }

        return instance;
    }

    private String encrypt(String s) {
        String result = null;
        if(StringUtils.isNotBlank(s)) {
            try {
                byte[] e = this.enCipher.doFinal(s.getBytes());
                result = new String(Base58.encode(e));
            } catch (Exception var4) {
                var4.printStackTrace();
            }
        }

        return result;
    }

    private String decrypt(String s) {
        String result = null;
        if(StringUtils.isNotBlank(s)) {
            try {
                byte[] e = Base58.decode(s);
                result = new String(this.deCipher.doFinal(e));
            } catch (Exception var4) {
                this.resetInstance();
                var4.printStackTrace();
            }
        }

        return result;
    }

    private void resetInstance() {
        pool.set((HashMap<String, BlowfishUtils>) null);
    }
}