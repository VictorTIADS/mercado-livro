package com.innouti.mercadolivro.controller.response

import com.innouti.mercadolivro.enums.BookStatus
import com.innouti.mercadolivro.model.CustomerModel
import java.math.BigDecimal

data class BookPurchaseResponse(
    var id: Int? = null,
    var name: String,
    var price: BigDecimal,
    var status: BookStatus? = null
)
