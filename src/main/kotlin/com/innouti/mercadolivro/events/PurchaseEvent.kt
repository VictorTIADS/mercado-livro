package com.innouti.mercadolivro.events

import com.innouti.mercadolivro.model.PurchaseModel
import org.springframework.context.ApplicationEvent

class PurchaseEvent(
    source: Any,
    val purchaseModel: PurchaseModel
) : ApplicationEvent(source)