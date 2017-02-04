package cn.com.duiba.mvc;

import cn.com.duiba.mvc.interceptor.BeginInterceptor;
import cn.com.duiba.mvc.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by liuyao on 2017/2/1.
 */
@Configuration
public class SpringMvcConfig extends WebMvcConfigurerAdapter {
    @Autowired
    private BeginInterceptor beginInterceptor;
    @Autowired
    private LoginInterceptor loginInterceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(beginInterceptor).addPathPatterns("/**");
        registry.addInterceptor(loginInterceptor).addPathPatterns("/works/**");
    }
}
