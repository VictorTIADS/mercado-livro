package com.innouti.mercadolivro.controller.response

import com.innouti.mercadolivro.enums.BookStatus
import java.math.BigDecimal

data class BookResponse(
    var id: Int? = null,
    var name: String,
    var price: BigDecimal,
    var customerId: Int? = null,
    var status: BookStatus? = null
)
