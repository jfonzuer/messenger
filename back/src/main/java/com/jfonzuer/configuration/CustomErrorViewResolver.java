package com.jfonzuer.configuration;

import org.springframework.boot.autoconfigure.web.ErrorViewResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ViewResolver;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Map;

/**
 * Created by pgm on 19/04/17.
 */
@Configuration
public class CustomErrorViewResolver implements ErrorViewResolver {

    @Override
    public ModelAndView resolveErrorView(HttpServletRequest httpServletRequest, HttpStatus httpStatus, Map<String, Object> map) {
        String returnView = "app/index";
        if (map.get("path").equals("/robots.txt")) {
            returnView = "robots.txt";
        }
        return httpStatus == HttpStatus.NOT_FOUND ? new ModelAndView(returnView, Collections.<String, Object> emptyMap(), HttpStatus.OK) : null;
    }
}
