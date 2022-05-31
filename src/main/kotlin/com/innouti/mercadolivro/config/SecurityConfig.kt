package com.innouti.mercadolivro.config

import com.innouti.mercadolivro.enums.Role
import com.innouti.mercadolivro.repository.CustomerRepository
import com.innouti.mercadolivro.security.AuthenticatorFilter
import com.innouti.mercadolivro.security.AuthorizationFilter
import com.innouti.mercadolivro.security.CustomAuthenticationEntryPoint
import com.innouti.mercadolivro.security.JwtUtil
import com.innouti.mercadolivro.service.UserDetailCustomService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig(
    private val customerRepository: CustomerRepository,
    private val userDetailCustomService: UserDetailCustomService,
    private val jwtUtil: JwtUtil,
    private val customAuthenticationEntryPoint: CustomAuthenticationEntryPoint
) : WebSecurityConfigurerAdapter() {

    @Bean
    fun bCryptPasswordEncoder() = BCryptPasswordEncoder()

    @Bean
    fun corsConfig(): CorsFilter {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()
        config.apply {
            allowCredentials = true
            addAllowedOrigin("*")
            addAllowedHeader("*")
            addAllowedMethod("*")
        }
        source.registerCorsConfiguration("/**", config)
        return CorsFilter(source)
    }

    private val PUBLIC_POST_MATCHERS = arrayOf(
        "/customers"
    )

    private val ADMIN_MATHERS = arrayOf(
        "/admin/**"
    )

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailCustomService).passwordEncoder(bCryptPasswordEncoder())
    }

    override fun configure(web: WebSecurity) {
        web.ignoring().antMatchers(
            "/v2/api-docs",
            "/configuration/ui",
            "/swagger-resources/**",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**"
        )
    }

    override fun configure(http: HttpSecurity) {
        http
            .cors()
            .and()
            .csrf()
            .disable()

        http
            .authorizeRequests()
            .antMatchers(HttpMethod.POST, *PUBLIC_POST_MATCHERS)
            .permitAll()
            .antMatchers(*ADMIN_MATHERS)
            .hasAnyAuthority(Role.ADMIN.description)
            .anyRequest()
            .authenticated()

        http
            .addFilter(AuthenticatorFilter(authenticationManager(), customerRepository, jwtUtil))
        http
            .addFilter(AuthorizationFilter(authenticationManager(), jwtUtil, userDetailCustomService))
        http
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        http.exceptionHandling().authenticationEntryPoint(customAuthenticationEntryPoint)
    }
}