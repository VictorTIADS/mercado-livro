package com.innouti.mercadolivro.service

import com.innouti.mercadolivro.enums.BookStatus
import com.innouti.mercadolivro.enums.Errors
import com.innouti.mercadolivro.exception.NotFoundException
import com.innouti.mercadolivro.model.BookModel
import com.innouti.mercadolivro.model.CustomerModel
import com.innouti.mercadolivro.repository.BookRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class BookService(
    private val repository: BookRepository
) {

    fun create(bookModel: BookModel) {
        repository.save(bookModel)
    }

    fun findAll(pageable: Pageable) = repository.findAll(pageable)

    fun findAllActive(pageable: Pageable) = repository.findByStatus(BookStatus.ACTIVE, pageable)

    fun findById(id: Int): BookModel =
        repository.findById(id).orElseThrow {
            NotFoundException(Errors.ML101.message.format(id), Errors.ML101.code)
        }

    fun delete(id: Int) {
        val book = findById(id)
        book.status = BookStatus.CANCELLED
        update(book)
    }

    fun update(book: BookModel) {
        repository.save(book)
    }

    fun deleteByCustomer(customer: CustomerModel) {
        val customerBooks = repository.findByCustomer(customer)
        customerBooks.forEach { book ->
            book.status = BookStatus.DELETED
        }
        repository.saveAll(customerBooks)
    }

    fun findAllByIds(bookIds: Set<Int>): List<BookModel> {
        return repository.findAllById(bookIds).toList()
    }

    fun purchase(books: MutableList<BookModel>) {
        books.map {
            it.status = BookStatus.SOLD
        }
        repository.saveAll(books)
    }
}