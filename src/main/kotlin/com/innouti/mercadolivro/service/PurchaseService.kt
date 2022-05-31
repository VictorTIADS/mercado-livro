package com.innouti.mercadolivro.service

import com.innouti.mercadolivro.enums.BookStatus
import com.innouti.mercadolivro.enums.Errors
import com.innouti.mercadolivro.events.PurchaseEvent
import com.innouti.mercadolivro.exception.PurchaseBookException
import com.innouti.mercadolivro.model.CustomerModel
import com.innouti.mercadolivro.model.PurchaseModel
import com.innouti.mercadolivro.repository.PurchaseRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class PurchaseService(
    private val purchaseRepository: PurchaseRepository,
    private val applicationEventPublisher: ApplicationEventPublisher,
) {

    fun create(purchaseModel: PurchaseModel) {
        val books = purchaseModel.books
        when {
            books.find { it.customer?.id == purchaseModel.customer.id } != null ->
                throw PurchaseBookException(
                    Errors.ML104.message,
                    Errors.ML104.code
                )
            !books.none { it.status == BookStatus.SOLD || it.status == BookStatus.DELETED || it.status == BookStatus.CANCELLED } ->
                throw PurchaseBookException(
                    Errors.ML103.message.format(
                        listOf(
                            BookStatus.SOLD.name,
                            BookStatus.DELETED.name,
                            BookStatus.CANCELLED.name
                        )
                    ), Errors.ML103.code
                )
            else -> {
                purchaseRepository.save(purchaseModel)
                applicationEventPublisher.publishEvent(PurchaseEvent(this, purchaseModel))
            }
        }
    }

    fun update(purchaseModel: PurchaseModel) {
        purchaseRepository.save(purchaseModel)
    }

    fun getPurchaseByUserId(customer: CustomerModel): List<PurchaseModel> {
        return purchaseRepository.findByCustomer(customer)
    }

}
