package cn.com.duiba.mvc;

import com.google.common.base.Splitter;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liuyao on 2017/2/1.
 */
public class RequestTool {
    private static final Logger log = LoggerFactory.getLogger(RequestTool.class);
    private static ThreadLocal<RequestTool> local = new ThreadLocal<RequestTool>();

    private HttpServletRequest request;
    private HttpServletResponse response;
    private RequestParams params;

    public static void setRequestInThreadLocal(HttpServletRequest request, HttpServletResponse response){
        if(request==null) throw new RuntimeException("RequestTool注入request为空");
        if(response==null) throw new RuntimeException("RequestTool注入response为空");
        RequestTool tool = new RequestTool();
        tool.request = request;
        tool.response = response;
        tool.params = RequestParams.parse(request);
        local.set(tool);
    }

    private static RequestTool get(){
        if(local.get()==null) throw new RuntimeException("请在拦截器中调用setRequestInThreadLocal");
        return local.get();
    }

    public static HttpServletRequest getRequest(){
        return get().request;
    }

    public static HttpServletResponse getResponse(){
        return get().response;
    }

    public static void removeRequestInThreadLocal(){
        local.remove();
    }

    public static Long getWid(){
        RequestTool tool = RequestTool.get();
        return tool.params.getWriterId();
    }

    public static String getIp(){
        RequestTool tool = RequestTool.get();
        return tool.params.getIp();
    }

    public static String getUserAgent(){
        RequestTool tool = RequestTool.get();
        return tool.params.getUserAgent();
    }

    public static String getUrl(){
        HttpServletRequest request= get().request;
        String url = request.getRequestURL().toString();
        String queryString = request.getQueryString();
        StringBuilder sb = new StringBuilder();
        sb.append(url);

        if(StringUtils.isNotBlank(queryString)) sb.append("?").append(queryString);
        return sb.toString();
    }

    public static boolean isLocalRequest(){
        RequestTool tool = RequestTool.get();
        return RequestParams.LOCALHOST_IP.equals(tool.params.getIp());
    }

    public static boolean isHttpsRequest(){
        HttpServletRequest request= get().request;
        return "true".equals(request.getHeader("Use-Https"));
    }

    public static String getCookie(String name){
        RequestTool tool = RequestTool.get();
        return tool.params.getCookie(name);
    }


    public static String getServerPath(){
        HttpServletRequest request= get().request;
        String port = String.valueOf(request.getServerPort());
        if ("80".equals(port)) {
            port = "";
        } else {
            port = ":" + port;
        }
        String serverUrl = request.getScheme() + "://" + request.getServerName() + port;
        return serverUrl;
    }

    public static String getWebRootDir(){
        HttpServletRequest request= get().request;
        return request.getServletContext().getRealPath("/");
    }
}
