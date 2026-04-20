package com.watchthis.business

enum class ValidationError {
    BLANK_TERM,
    SHORT_TERM,
    BLANK_USER_ID,
    INVALID_USER_ID_NUM,
    NON_POSITIVE_USER_ID,
    BLANK_UK,
    BLANK_EN,
    SHORT_UK,
    SHORT_EN,
    WRONG_SCRIPT_UK,
    WRONG_SCRIPT_EN
}

data class ValidationResult(
    val isValid: Boolean,
    val error: ValidationError? = null
)
