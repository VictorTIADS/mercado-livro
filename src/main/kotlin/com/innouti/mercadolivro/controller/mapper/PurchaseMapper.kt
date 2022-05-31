package com.innouti.mercadolivro.controller.mapper

import com.innouti.mercadolivro.controller.request.PostPurchaseRequest
import com.innouti.mercadolivro.model.PurchaseModel
import com.innouti.mercadolivro.service.BookService
import com.innouti.mercadolivro.service.CustomerService
import org.springframework.stereotype.Component

@Component
class PurchaseMapper(
    private val bookService: BookService,
    private val customerService: CustomerService
) {
    fun toModel(request: PostPurchaseRequest): PurchaseModel {
        val customer = customerService.findById(request.customerId)
        val books = bookService.findAllByIds(request.bookIds)
        return PurchaseModel(customer = customer, books = books.toMutableList(), price = books.sumOf { it.price })
    }
}