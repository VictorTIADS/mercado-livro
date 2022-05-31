package com.innouti.mercadolivro.security

import com.innouti.mercadolivro.exception.AuthenticationException
import com.innouti.mercadolivro.service.UserDetailCustomService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthorizationFilter(
    authenticationManager: AuthenticationManager,
    private val jwtUtil: JwtUtil,
    private val userDetails: UserDetailCustomService
) : BasicAuthenticationFilter(authenticationManager) {
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val authorization = request.getHeader("Authorization")
        if (authorization != null && authorization.startsWith("Bearer")) {
            val auth = getAuthentication(authorization.removePrefix("Bearer"))
            SecurityContextHolder.getContext().authentication = auth
        }
        chain.doFilter(request, response)
    }

    private fun getAuthentication(authentication: String): UsernamePasswordAuthenticationToken {
        if (!jwtUtil.isValidToken(authentication)) {
            throw AuthenticationException("Token Invalido", "999")
        }
        val id = jwtUtil.getSubject(authentication)
        val customer = userDetails.loadUserByUsername(id)
        return UsernamePasswordAuthenticationToken(customer, null, customer.authorities)
    }
}