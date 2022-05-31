package com.innouti.mercadolivro.controller

import com.innouti.mercadolivro.controller.mapper.PurchaseMapper
import com.innouti.mercadolivro.controller.request.PostPurchaseRequest
import com.innouti.mercadolivro.controller.response.PurchaseResponse
import com.innouti.mercadolivro.extension.toResponse
import com.innouti.mercadolivro.service.CustomerService
import com.innouti.mercadolivro.service.PurchaseService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("purchase")
class PurchaseController(
    private val purchaseService: PurchaseService,
    private val purchaseMapper: PurchaseMapper,
    private val customerService: CustomerService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun purchase(@RequestBody request: PostPurchaseRequest) {
        purchaseService.create(purchaseMapper.toModel(request))
    }

    @GetMapping("/{customerId}")
    fun getPurchaseByUser(@PathVariable customerId: Int): PurchaseResponse {
        return purchaseService.getPurchaseByUserId(customerService.findById(customerId)).toResponse()
    }
}