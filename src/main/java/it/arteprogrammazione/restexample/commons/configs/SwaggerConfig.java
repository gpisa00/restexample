package it.arteprogrammazione.restexample.commons.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private static final String BASE_PATH = "it.arteprogrammazione.restexample.controllers.";

    @Bean
    public Docket customersApi() {
        return new Docket(DocumentationType.SWAGGER_2).useDefaultResponseMessages(false).groupName("customers")
                .select().apis(RequestHandlerSelectors.basePackage(BASE_PATH + "customers"))
                .paths(regex(".*/customers.*"))
                .build();
    }


    @Bean
    public Docket paymentCardsApi() {
        return new Docket(DocumentationType.SWAGGER_2).useDefaultResponseMessages(false).groupName("paymentcards")
                .select().apis(RequestHandlerSelectors.basePackage(BASE_PATH + "paymentcards"))
                .paths(regex(".*/paymentcards.*"))
                .build();
    }
}
