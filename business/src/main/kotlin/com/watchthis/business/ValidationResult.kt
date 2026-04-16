package com.watchthis.business

data class ValidationResult(
    val isValid: Boolean,
    val message: String? = null
)
