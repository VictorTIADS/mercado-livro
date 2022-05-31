package com.innouti.mercadolivro.controller.request

import com.fasterxml.jackson.annotation.JsonAlias
import java.math.BigDecimal
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

data class PostBookRequest(
    @field:NotEmpty(message = "Name must be informed")
    var name: String,
    @field:NotNull(message = "Price must be informed")
    var price: BigDecimal,
    @JsonAlias("customer_id")
    @field:Positive
    var customerId: Int
)