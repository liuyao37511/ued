package cn.com.duiba.mvc.interceptor;

import cn.com.duiba.mvc.RequestTool;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by liuyao on 2017/2/1.
 */
@Component("loginInterceptor")
public class LoginInterceptor extends HandlerInterceptorAdapter{
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(RequestTool.getWid() == null){//未登录
            //logger.error("request home project not login ");
            if(handler instanceof HandlerMethod){
                HandlerMethod handlerMethod = (HandlerMethod)handler;
                ResponseBody anno = handlerMethod.getMethodAnnotation(ResponseBody.class);
                if(anno != null || handlerMethod.getReturnType() == null) {//ajax 请求
                    JSONObject model = new JSONObject();
                    model.put("success",false);
                    model.put("notLogin",true);
                    model.put("message","访问失败");
                    response.getWriter().write(model.toJSONString());
                }
            }
            return false;
        }
        return true;
    }
}
