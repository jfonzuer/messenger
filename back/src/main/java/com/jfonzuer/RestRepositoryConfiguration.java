package com.jfonzuer;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

/**
 * Created by pgm on 07/11/16.
 */
@Configuration
public class RestRepositoryConfiguration extends RepositoryRestConfigurerAdapter {
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.setPageParamName("p")
                .setLimitParamName("l")
                .setSortParamName("q");
    }
}
