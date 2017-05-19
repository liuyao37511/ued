package cn.com.duiba.mvc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

/**
 * Created by liuyao on 2017/2/1.
 */
public class RequestParams {
    public static final String LOCALHOST_IP="127.0.0"+".1";

    private String userAgent = "";
    private Long writerId;
    private String ip;
    private Map<String, String> cookies = Collections.emptyMap();

    public String getUserAgent() {
        return userAgent;
    }

    public Long getWriterId() {
        return writerId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getCookie(String name) {
        return this.cookies.get(name);
    }

    public void setCookies(Map<String, String> cookies) {
        cookies.putAll(cookies);
    }

    public Map<String, String> getCookies() {
        return cookies;
    }

    public static RequestParams parse(HttpServletRequest request) {
        if(request==null) throw new RuntimeException("RequestParams解析时的request不能为空");
        RequestParams rp = new RequestParams();

        String ua = request.getHeader("User-Agent");
        if(StringUtils.isNotBlank(ua)) rp.userAgent = ua.toLowerCase();

        rp.ip = parseIp(request);

        Map<String,String> cookieMap = parseCookies(request);
        rp.writerId = parseWid(cookieMap);
        rp.cookies = cookieMap;
        return rp;
    }

    public static Long parseWid(Map<String,String> cookieMap){
        String wdata3 = cookieMap.get("wdata3");
        if (wdata3 != null) {
            String content = SecureTool.decryptWriterCookie(wdata3);
            JSONObject json = JSON.parseObject(content);
            if (new Date().getTime() - json.getLong("time") < 60 * 60 * 1000L) {// 1小时过期
                return json.getLong("wid");
            }
        }
        return null;
    }

    public static String parseIp(HttpServletRequest request){
        String ip = request.getHeader("x-forwarded-for");
        if (ip != null && ip.trim().length() > 0) {
            String[] ips = ip.trim().split(",");
            int size = ips.length;
            if (size > 0) {
                ip = ips[0].trim();
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Cdn-Src-Ip");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip != null && ip.startsWith("0:0:0:0")) {
            ip = LOCALHOST_IP;
        }
        return ip;
    }

    public static Map<String,String> parseCookies(HttpServletRequest request){
        Cookie[] cookies=request.getCookies();
        Map<String,String> cookieMap = Maps.newHashMap();
        if(cookies!=null){
            for(Cookie cookie : cookies){
                cookieMap.put(cookie.getName(),cookie.getValue());
            }
        }
        return cookieMap;
    }
}
