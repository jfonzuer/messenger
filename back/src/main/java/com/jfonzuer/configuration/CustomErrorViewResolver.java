package com.jfonzuer.configuration;

import org.springframework.boot.autoconfigure.web.ErrorViewResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;

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
        return httpStatus == HttpStatus.NOT_FOUND
                ? new ModelAndView("index.html", Collections.<String, Object> emptyMap(), HttpStatus.OK)
                : null;
    }
}
