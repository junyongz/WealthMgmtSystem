package com.hakkazhong.wms;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.r2dbc.init.R2dbcScriptDatabaseInitializer;
import org.springframework.boot.sql.init.DatabaseInitializationSettings;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.handler.WebFluxResponseStatusExceptionHandler;

import io.r2dbc.spi.ConnectionFactory;
import reactor.core.publisher.Hooks;

@SpringBootApplication
public class WealthMgmtSystemApplication {

	public static void main(String[] args) {
		Hooks.onOperatorDebug();

		SpringApplication.run(WealthMgmtSystemApplication.class, args);
	}

	@Bean
	R2dbcScriptDatabaseInitializer r2dbcInit(ConnectionFactory connFactory) {
		DatabaseInitializationSettings settings = new DatabaseInitializationSettings();
		settings.setSchemaLocations(Arrays.asList("schema.sql"));

		return new R2dbcScriptDatabaseInitializer(connFactory, settings);
	}

	@Bean
	@Order(0) // ordering in ExceptionHandlingWebHandler
	WebFluxResponseStatusExceptionHandler webfluxExceptionHandler() {
		WebFluxResponseStatusExceptionHandler handler = new WebFluxResponseStatusExceptionHandler();
		handler.setWarnLogCategory(WebFluxResponseStatusExceptionHandler.class.getName());

		return handler;
	}

	@Bean
	WebFluxConfigurer webfluxExtendedConfigurer() {
		return new WebFluxConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
					.allowedOrigins("*")
					.allowedMethods("GET", "POST", "DELETE");
			}
		};
	}

}
