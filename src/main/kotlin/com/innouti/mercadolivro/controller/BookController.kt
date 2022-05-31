package com.innouti.mercadolivro.controller

import com.innouti.mercadolivro.controller.request.PostBookRequest
import com.innouti.mercadolivro.controller.request.PutBookRequest
import com.innouti.mercadolivro.extension.toBookModel
import com.innouti.mercadolivro.extension.toPageResponse
import com.innouti.mercadolivro.extension.toResponse
import com.innouti.mercadolivro.service.BookService
import com.innouti.mercadolivro.service.CustomerService
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("book")
class BookController(
    private val bookService: BookService,
    private val customerService: CustomerService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createBook(@RequestBody @Valid request: PostBookRequest) {
        val customer = customerService.findById(request.customerId)
        bookService.create(request.toBookModel(customer))
    }

    @GetMapping
    fun findAll(@PageableDefault(page = 0, size = 10) pageable: Pageable) =
        bookService.findAll(pageable).map { it.toResponse() }.toPageResponse()

    @GetMapping("/active")
    fun findAllActive(@PageableDefault(page = 0, size = 10) pageable: Pageable) =
        bookService.findAllActive(pageable).map { it.toResponse() }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Int) = bookService.findById(id).toResponse()

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Int) {
        bookService.delete(id)
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun update(@PathVariable id: Int, @RequestBody putBookRequest: PutBookRequest) {
        val bookSaved = bookService.findById(id)
        bookService.update(putBookRequest.toBookModel(bookSaved))
    }
}