package com.innouti.mercadolivro.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Profile("!prod")
@Configuration
@EnableSwagger2
class SwaggerConfiguration {

    @Bean
    fun api(): Docket = Docket(DocumentationType.SWAGGER_2)
        .apiInfo(getApiInfo())
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.innouti.mercadolivro.controller"))
        .paths(PathSelectors.any())
        .build()

    private fun getApiInfo(): ApiInfo {
        val contact = Contact("Innouti Technology Solutions", "", "victor.g.s.batista@gmail.com")
        return ApiInfoBuilder()
            .title("Mercado Livro")
            .description("Innouti")
            .version("1.0.0")
            .license("Apache 2.0")
            .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0")
            .contact(contact)
            .build()
    }
}