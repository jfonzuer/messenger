package com.jfonzuer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MessengerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MessengerApplication.class, args);
	}


	/**
	 * CorsInterceptor factory.
	 *
	 * @return CorsInterceptor
	 */
//	@Bean
//	public CorsInterceptor corsInterceptor() {
//		return new CorsInterceptor();
//	}
//
//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(corsInterceptor());
//	}

//	@Bean
//	HeaderHttpSessionStrategy sessionStrategy() {
//		return new HeaderHttpSessionStrategy();
//	}
}
