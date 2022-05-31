package com.innouti.mercadolivro.controller.request

import com.innouti.mercadolivro.validation.EmailAvailable
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty

data class PostCustomerRequest(
    @field:NotEmpty(message = "Name must be informed")
    var name: String,
    @EmailAvailable
    @field:Email(message = "E-mail should be valid")
    @field:NotEmpty(message = "E-mail must be informed")
    var email: String,

    @field:NotEmpty(message = "Password bust be informed")
    var password: String
)