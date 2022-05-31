package com.innouti.mercadolivro.exception

class PurchaseBookException(
    override val message: String,
    val errorCode: String
) : Exception()