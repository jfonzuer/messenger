package com.jfonzuer.configuration;

import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbConnector;
import org.ektorp.impl.StdCouchDbInstance;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.net.MalformedURLException;

/**
 * Created by pgm on 19/03/17.
 */
@Configuration
public class CouchDBConfiguration {

    @Value("${couchdb.url}")
    private String url;

    @Value("${couchdb.user}")
    private String user;

    @Value("${couchdb.password}")
    private String password;

    @Value("${couchdb.image.db}")
    private String imageDb;


    @Bean
    public HttpClient httpClient() throws MalformedURLException {
        return new StdHttpClient.Builder()
                .url(url)
                .username(user)
                .password(password)
                .build();
    }

    @Bean
    public CouchDbInstance couchDbInstance(HttpClient httpClient) {
        return new StdCouchDbInstance(httpClient);
    }

    @Bean
    public CouchDbConnector couchDbImageConnector(CouchDbInstance couchDbInstance) {
        return new StdCouchDbConnector(imageDb, couchDbInstance);
    }
}
