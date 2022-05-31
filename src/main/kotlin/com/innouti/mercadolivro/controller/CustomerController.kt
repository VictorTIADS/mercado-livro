package com.innouti.mercadolivro.controller

import com.innouti.mercadolivro.controller.request.PostCustomerRequest
import com.innouti.mercadolivro.controller.request.PutCustomerRequest
import com.innouti.mercadolivro.controller.response.CustomerResponse
import com.innouti.mercadolivro.extension.toCustomerModel
import com.innouti.mercadolivro.extension.toPageResponse
import com.innouti.mercadolivro.extension.toResponse
import com.innouti.mercadolivro.security.UserCanOnlyAccessTheirOwnResource
import com.innouti.mercadolivro.service.CustomerService
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("customers")
class CustomerController(
    private val service: CustomerService
) {

    @GetMapping
    @UserCanOnlyAccessTheirOwnResource
    fun getAll(@RequestParam name: String?, @PageableDefault(page = 0, size = 10) pageable: Pageable) =
        service.getAll(name, pageable).map { it.toResponse() }.toPageResponse()

    @GetMapping("/{id}")
    @UserCanOnlyAccessTheirOwnResource
    fun getCustomer(@PathVariable id: Int): CustomerResponse {
        return service.findById(id).toResponse()
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createCustomer(@RequestBody @Valid customerRequest: PostCustomerRequest) {
        service.createCustomer(customerRequest.toCustomerModel())
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @UserCanOnlyAccessTheirOwnResource
    fun updateCustomer(@PathVariable id: Int, @RequestBody @Valid putCustomerRequest: PutCustomerRequest) {
        val customerSaved = service.findById(id)
        service.updateCustomer(putCustomerRequest.toCustomerModel(customerSaved))
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteCustomer(@PathVariable id: Int) {
        service.deleteCustomer(id)
    }
}