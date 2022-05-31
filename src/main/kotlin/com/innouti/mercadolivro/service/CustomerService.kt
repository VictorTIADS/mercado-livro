package com.innouti.mercadolivro.service

import com.innouti.mercadolivro.enums.CustomerStatus
import com.innouti.mercadolivro.enums.Errors
import com.innouti.mercadolivro.enums.Role
import com.innouti.mercadolivro.exception.NotFoundException
import com.innouti.mercadolivro.model.CustomerModel
import com.innouti.mercadolivro.repository.CustomerRepository
import org.springframework.data.domain.Pageable
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class CustomerService(
    private val repository: CustomerRepository,
    private val bookService: BookService,
    private val bCrypt: BCryptPasswordEncoder
) {

    fun getAll(name: String?, pageable: Pageable) =
        if (name != null) repository.findByNameContains(name, pageable) else repository.findAll(pageable)

    fun findById(id: Int): CustomerModel = repository.findById(id).orElseThrow {
        NotFoundException(Errors.ML201.message.format(id), Errors.ML201.code)
    }

    fun createCustomer(customerModel: CustomerModel) {
        val customer = customerModel.copy(
            roles = setOf(Role.CUSTOMER),
            password = bCrypt.encode(customerModel.password)
        )
        repository.save(customer)
    }

    fun updateCustomer(customerModel: CustomerModel) {
        customerModel.id?.let {
            if (repository.existsById(it)) {
                val customer = repository.findById(it).get()
                customer.name = if (customerModel.name != customer.name) customerModel.name else customer.name
                customer.email = if (customerModel.email != customer.email) customerModel.email else customer.email
                repository.save(customer)
            } else throw Exception()
        } ?: throw Exception()
    }

    fun deleteCustomer(id: Int) {
        val customer = findById(id)
        bookService.deleteByCustomer(customer)
        customer.status = CustomerStatus.INACTIVE
        repository.save(customer)
    }

    fun emailAvailable(email: String) = !repository.existsByEmail(email)

}