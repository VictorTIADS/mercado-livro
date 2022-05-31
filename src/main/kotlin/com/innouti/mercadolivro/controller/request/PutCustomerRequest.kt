package com.innouti.mercadolivro.controller.request

import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty

data class PutCustomerRequest(
    @field:NotEmpty(message = "Name must be informed")
    var name: String,
    @field:Email(message = "E-mail should be valid")
    @field:NotEmpty(message = "E-mail must be informed")
    var email: String
)