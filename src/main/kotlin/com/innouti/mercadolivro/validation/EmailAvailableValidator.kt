package com.innouti.mercadolivro.validation

import com.innouti.mercadolivro.service.CustomerService
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class EmailAvailableValidator(
    private val customerService: CustomerService
) : ConstraintValidator<EmailAvailable, String> {
    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        return if (value.isNullOrEmpty()) {
            false
        } else {
            customerService.emailAvailable(value)
        }
    }
}
