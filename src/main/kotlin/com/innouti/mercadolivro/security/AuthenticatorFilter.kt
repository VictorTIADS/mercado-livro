package com.innouti.mercadolivro.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.innouti.mercadolivro.controller.request.LoginRequest
import com.innouti.mercadolivro.exception.AuthenticationException
import com.innouti.mercadolivro.repository.CustomerRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthenticatorFilter(
    authenticationManager: AuthenticationManager,
    private val customerRepository: CustomerRepository,
    private val jwtUtil: JwtUtil
) : UsernamePasswordAuthenticationFilter(authenticationManager) {
    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        try {
            val authMap = ObjectMapper().readValue(request.inputStream, Map::class.java)
            val loginAuth = LoginRequest(authMap["email"].toString(), authMap["password"].toString())
            val id = customerRepository.findByEmail(loginAuth.email)?.id
            val authentication = UsernamePasswordAuthenticationToken(id, loginAuth.password)
            return authenticationManager.authenticate(authentication)
        } catch (e: Exception) {
            throw AuthenticationException("Falha Authentication", "")
        }
    }

    override fun successfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain,
        authResult: Authentication
    ) {
        val id = (authResult.principal as UserCustomerDetails).id
        val token = jwtUtil.generateToken(id)
        response.addHeader("Authorization", token)
    }
}