package com.jmachuca.curso.springboot.app.interceptor.springboot_interceptor.interceptors;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component("timeInterceptor")
public class LoadingTimeInterceptor implements HandlerInterceptor{

    private static final Logger logger = LoggerFactory.getLogger(LoadingTimeInterceptor.class);
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        boolean error = false;

        HandlerMethod controller = (HandlerMethod) handler;
        
        logger.info("LoadingTimeInterceptor: preHandle() Iniciando... " + controller.getMethod().getName());

        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);

        // Generar un delay para tener respuestas más lentas y ver distintos tiempos
        Random random = new Random();
        int delay =random.nextInt(500);
        Thread.sleep(delay);

        Map<String, String> json = new HashMap<>();
        json.put("error", "No tienes acceso a está página!");
        json.put("date", new Date().toString());

        if (error) { 
            ObjectMapper mapper = new ObjectMapper();
            String jsonStr = mapper.writeValueAsString(json);
    
            response.setContentType("application/json");
            response.setStatus(401);
            response.getWriter().write(jsonStr);

            return false;
        }

        return true; // Si responde false no continúa ejecución al controlador o no continua con la cadena de interceptor en caso que se invoque a otros posterior a este
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerMethod controller = (HandlerMethod) handler;

        long endTime = System.currentTimeMillis();
        long startTime = (long) request.getAttribute("startTime");
        long executionTime = endTime - startTime;

        logger.info("Tiempo transcurrido: " + executionTime + "ms");
        logger.info("LoadingTimeInterceptor: postHandle() Terminando... " + controller.getMethod().getName());
    }

}
