package com.innouti.mercadolivro.controller

import com.innouti.mercadolivro.service.CustomerService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("admin")
class AdminController(
    private val service: CustomerService
) {

    @GetMapping("/report")
    fun getAll() =
        "This is a Report Only Admin"

}