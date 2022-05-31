package com.innouti.mercadolivro.controller.response

import com.innouti.mercadolivro.enums.CustomerStatus

data class CustomerResponse(
    var id: Int? = null,
    var name: String,
    var email: String,
    var status: CustomerStatus
)