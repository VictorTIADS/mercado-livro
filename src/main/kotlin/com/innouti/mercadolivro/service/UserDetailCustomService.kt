package com.innouti.mercadolivro.service

import com.innouti.mercadolivro.exception.AuthenticationException
import com.innouti.mercadolivro.repository.CustomerRepository
import com.innouti.mercadolivro.security.UserCustomerDetails
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailCustomService(
    private val customerRepository: CustomerRepository
) : UserDetailsService {
    override fun loadUserByUsername(id: String): UserDetails {
        val customer =
            customerRepository.findById(id.toInt()).orElseThrow { AuthenticationException("User Not Found", "999") }
        return UserCustomerDetails(customer)
    }
}