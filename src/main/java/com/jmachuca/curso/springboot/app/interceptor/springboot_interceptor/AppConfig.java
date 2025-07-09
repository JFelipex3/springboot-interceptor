package com.jmachuca.curso.springboot.app.interceptor.springboot_interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig implements WebMvcConfigurer {

    @Autowired
    @Qualifier("timeInterceptor")
    private HandlerInterceptor timeInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(timeInterceptor); // Interceptor para todas las rutas
        //registry.addInterceptor(timeInterceptor).addPathPatterns("/app/bar", "/app/foo"); // Interceptor solo para rutas especificas
        //registry.addInterceptor(timeInterceptor).excludePathPatterns("/app/bar"); // Interceptor para todos excepto los indicados
        //registry.addInterceptor(timeInterceptor).addPathPatterns("/app/**"); // Interceptor para todas las rutas de /app/* 
    }

}
