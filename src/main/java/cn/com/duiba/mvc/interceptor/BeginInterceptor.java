package cn.com.duiba.mvc.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.com.duiba.mvc.RequestTool;

/**
 * Created by liuyao on 2017/2/1.
 */
@Component("beginInterceptor")
public class BeginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(response.getStatus() == 404 || response.getStatus() == 500){
            return true;
        }
        RequestTool.setRequestInThreadLocal(request,response);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) throws Exception {
        RequestTool.removeRequestInThreadLocal();
    }

}
