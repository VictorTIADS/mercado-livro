package com.innouti.mercadolivro.repository

import com.innouti.mercadolivro.enums.BookStatus
import com.innouti.mercadolivro.model.BookModel
import com.innouti.mercadolivro.model.CustomerModel
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface BookRepository : JpaRepository<BookModel, Int> {
    fun findByStatus(active: BookStatus, pageable: Pageable): Page<BookModel>
    fun findByCustomer(customer: CustomerModel): List<BookModel>
}