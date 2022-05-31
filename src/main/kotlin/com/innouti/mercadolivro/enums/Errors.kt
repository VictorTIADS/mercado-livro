package com.innouti.mercadolivro.enums

enum class Errors(val code: String, val message: String) {
    ML000("ML-000","Access Denied"),
    ML001("ML-001", "Invalid Request"),
    ML101("ML-101", "Book [%s] doesn't exists"),
    ML102("ML-102", "Can't update book with status [%s]"),
    ML103("ML-103", "Can't purchase a book with status %s"),
    ML104("ML-104", "The customer can't but his own book"),
    ML201("ML-201", "Customer [%s] doesn't exists")
}