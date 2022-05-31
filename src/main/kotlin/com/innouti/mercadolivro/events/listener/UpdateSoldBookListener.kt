package com.innouti.mercadolivro.events.listener

import com.innouti.mercadolivro.events.PurchaseEvent
import com.innouti.mercadolivro.service.BookService
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class UpdateSoldBookListener(
    private val bookService: BookService
) {

    @Async
    @EventListener
    fun listener(purchaseEvent: PurchaseEvent) {
        bookService.purchase(purchaseEvent.purchaseModel.books)
    }
}