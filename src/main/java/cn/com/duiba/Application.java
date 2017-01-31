package cn.com.duiba;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liuyao on 2017/1/31.
 */
@Configuration
@SpringBootApplication
@ImportResource("classpath*:spring-project.xml")
@PropertySource(ignoreResourceNotFound=false, value="${duiba.config.location}")
public class Application extends SpringBootServletInitializer {
    private static final Logger log = LoggerFactory.getLogger(Application.class);
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        try {
            long start = System.currentTimeMillis();
            SpringApplication.run(Application.class, args);
            long period = System.currentTimeMillis() - start;
            log.error("Application start successfully in "+period+" ms.");
        } catch (Exception e) {
            log.error("Application start error :", e);
            System.exit(-1);
        }
    }
}
