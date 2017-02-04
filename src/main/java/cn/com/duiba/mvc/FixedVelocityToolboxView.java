package cn.com.duiba.mvc;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.context.Context;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.view.velocity.VelocityToolboxView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by liuyao on 2017/2/1.
 */
public class FixedVelocityToolboxView extends VelocityToolboxView {

    private ConcurrentMap<ServletContext, ServletContext> proxyedMap = new ConcurrentHashMap<ServletContext, ServletContext>(1);

    @Override
    protected Context createVelocityContext(Map<String, Object> model,
                                            HttpServletRequest request, HttpServletResponse response) throws Exception {
        org.apache.velocity.tools.view.context.ChainedContext context = new org.apache.velocity.tools.view.context.ChainedContext(
                new VelocityContext(model), getVelocityEngine(), request, response,
                getServletContext());
        if (getToolboxConfigLocation() != null) {
            setContextToolbox(context);
        }
        return context;
    }

    @SuppressWarnings("unchecked")
    private void setContextToolbox(
            org.apache.velocity.tools.view.context.ChainedContext context) {
        org.apache.velocity.tools.view.ToolboxManager toolboxManager = org.apache.velocity.tools.view.servlet.ServletToolboxManager
                .getInstance(getToolboxConfigFileAwareServletContext(),
                        getToolboxConfigLocation());
        Map<String, Object> toolboxContext = toolboxManager.getToolbox(context);
        context.setToolbox(toolboxContext);
    }

    private ServletContext getToolboxConfigFileAwareServletContext() {
        ServletContext context = getServletContext();
        ServletContext proxtedContext = proxyedMap.get(context);
        if(proxtedContext == null) {
            ProxyFactory factory = new ProxyFactory();
            factory.setTarget(context);
            //factory.setTargetClass(ServletContext.class);
            factory.addAdvice(new FixedVelocityToolboxView.GetResourceMethodInterceptor(getToolboxConfigLocation()));
            proxtedContext = (ServletContext) factory.getProxy(getClass().getClassLoader());
            ServletContext temp = proxyedMap.putIfAbsent(context, proxtedContext);
            if(temp != null){
                proxtedContext = temp;
            }
        }
        return proxtedContext;
    }

    /**
     * {@link MethodInterceptor} to allow the calls to getResourceAsStream() to resolve
     * the toolboxFile from the classpath.
     */
    private static class GetResourceMethodInterceptor implements MethodInterceptor {

        private final String toolboxFile;

        GetResourceMethodInterceptor(String toolboxFile) {
            if (toolboxFile != null && !toolboxFile.startsWith("/")) {
                toolboxFile = "/" + toolboxFile;
            }
            this.toolboxFile = toolboxFile;
        }

        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            if (invocation.getMethod().getName().equals("getResourceAsStream")
                    && invocation.getArguments()[0].equals(this.toolboxFile)) {
                InputStream inputStream = (InputStream) invocation.proceed();
                if (inputStream == null) {
                    try {
                        inputStream = new ClassPathResource(this.toolboxFile,
                                Thread.currentThread().getContextClassLoader())
                                .getInputStream();
                    }catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
                return inputStream;
            }
            return invocation.proceed();
        }

    }
}
