package com.innouti.mercadolivro.repository

import com.innouti.mercadolivro.model.CustomerModel
import com.innouti.mercadolivro.model.PurchaseModel
import org.springframework.data.jpa.repository.JpaRepository

interface PurchaseRepository : JpaRepository<PurchaseModel, Int> {
    fun findByCustomer(customer: CustomerModel): List<PurchaseModel>
}
