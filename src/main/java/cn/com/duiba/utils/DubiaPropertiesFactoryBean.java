package cn.com.duiba.utils;

import org.springframework.beans.factory.config.PropertiesFactoryBean;

import java.io.IOException;
import java.util.Properties;

public class DubiaPropertiesFactoryBean extends PropertiesFactoryBean {

    private static final String PREFIX="dbseccode";
    private static final String secret="CNxgrtFG2nYQUfu";

    @Override
    protected Properties createProperties() throws IOException {
        Properties properties = super.createProperties();
        Properties p=(Properties) properties.clone();
        for(Object key:p.keySet()){
            if(key instanceof String){
                String skey=(String) key;
                String value=(String)properties.get(skey);
                if(value!=null && value.startsWith(PREFIX)){
                    //如果是加密的，进行解密操作
                    value=decode(value);
                    properties.put(skey, value);
                }
            }
        }
        return properties;
    }
    private String decode(String value){
        if(value.startsWith(PREFIX)){
            value=value.substring(PREFIX.length());
            return BlowfishUtils.decryptBlowfish(value, secret);
        }
        return value;
    }

//    public static void main(String[] args) {
//        String code="dev_fas015";
//        String value=BlowfishUtils.encryptBlowfish(code, secret);
//        System.out.println(value);
//    }
}
