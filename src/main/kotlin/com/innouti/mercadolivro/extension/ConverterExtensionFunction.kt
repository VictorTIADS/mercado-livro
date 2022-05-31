package com.innouti.mercadolivro.extension

import com.innouti.mercadolivro.controller.request.PostBookRequest
import com.innouti.mercadolivro.controller.request.PostCustomerRequest
import com.innouti.mercadolivro.controller.request.PutBookRequest
import com.innouti.mercadolivro.controller.request.PutCustomerRequest
import com.innouti.mercadolivro.controller.response.*
import com.innouti.mercadolivro.enums.BookStatus
import com.innouti.mercadolivro.enums.CustomerStatus
import com.innouti.mercadolivro.model.BookModel
import com.innouti.mercadolivro.model.CustomerModel
import com.innouti.mercadolivro.model.PurchaseModel
import org.springframework.data.domain.Page

fun PostCustomerRequest.toCustomerModel() =
    CustomerModel(name = name, email = email, status = CustomerStatus.ACTIVE, password = password)

fun PutCustomerRequest.toCustomerModel(previousValue: CustomerModel) =
    CustomerModel(id = previousValue.id, name = name, email = email, previousValue.status, previousValue.password)

fun PostBookRequest.toBookModel(customer: CustomerModel) =
    BookModel(name = name, price = price, status = BookStatus.ACTIVE, customer = customer)

fun PutBookRequest.toBookModel(previousValue: BookModel) =
    BookModel(
        id = previousValue.id,
        name = name ?: previousValue.name,
        price = price ?: previousValue.price,
        status = previousValue.status,
        customer = previousValue.customer
    )

fun CustomerModel.toResponse() = CustomerResponse(id, name, email, status)

fun BookModel.toResponse() = BookResponse(id, name, price, customer?.id, status)

fun List<PurchaseModel>.toResponse(): PurchaseResponse {
    val books = mutableListOf<BookModel>()
    val booksPurchase = mutableListOf<BookPurchaseResponse>()
    this.forEach {
        books.addAll(it.books)
    }
    books.forEach {
        booksPurchase.add(BookPurchaseResponse(it.id, it.name, it.price, it.status))
    }
    return PurchaseResponse(booksPurchase.filter { it.status == BookStatus.SOLD })
}

fun <T> Page<T>.toPageResponse(): PageResponse<T> {
    return PageResponse(content, number, totalElements, totalPages)
}