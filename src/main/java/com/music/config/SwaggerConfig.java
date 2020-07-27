
package com.music.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/** Swagger configuration class.
 * @author Proma Chowdhury
 * @version 1.0
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Music")
                .apiInfo(apiInfo())
                .select()
                .paths(PathSelectors.regex("/artists.*"))
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Music API")
                .description("API exposes the list of Artists and ALbums")
                .termsOfServiceUrl("www.mocktermsofservice.com")
                .contact("promachowdhury5@gmail.com").license("Custom license")
                .licenseUrl("www.customurl.com").version("1.0").build();
    }

}
