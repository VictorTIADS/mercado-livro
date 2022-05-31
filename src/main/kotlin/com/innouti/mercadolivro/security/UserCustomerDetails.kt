package com.innouti.mercadolivro.security

import com.innouti.mercadolivro.enums.CustomerStatus
import com.innouti.mercadolivro.model.CustomerModel
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserCustomerDetails(val customerModel: CustomerModel) : UserDetails {
    val id: Int = customerModel.id!!
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return customerModel.roles.map {
            SimpleGrantedAuthority(it.description)
        }.toMutableList()
    }

    override fun getPassword() = customerModel.password
    override fun getUsername() = customerModel.id.toString()
    override fun isAccountNonExpired() = true
    override fun isAccountNonLocked() = true
    override fun isCredentialsNonExpired() = true
    override fun isEnabled() = customerModel.status == CustomerStatus.ACTIVE
}