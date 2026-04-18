package com.watchthis.ui

import android.content.Context
import android.widget.EditText
import com.watchthis.business.ValidationError
import com.watchthis.business.ValidationResult

fun ValidationError.toMessage(context: Context): String = when (this) {
    ValidationError.BLANK_TERM -> context.getString(R.string.err_blank_term)
    ValidationError.SHORT_TERM -> context.getString(R.string.err_short_term)
    ValidationError.BLANK_USER_ID -> context.getString(R.string.err_blank_user_id)
    ValidationError.INVALID_USER_ID_NUM -> context.getString(R.string.err_invalid_user_id)
    ValidationError.NON_POSITIVE_USER_ID -> context.getString(R.string.err_non_positive_user_id)
    ValidationError.BLANK_UK -> context.getString(R.string.err_blank_uk)
    ValidationError.BLANK_EN -> context.getString(R.string.err_blank_en)
    ValidationError.SHORT_UK -> context.getString(R.string.err_short_uk)
    ValidationError.SHORT_EN -> context.getString(R.string.err_short_en)
    ValidationError.WRONG_SCRIPT_UK -> context.getString(R.string.err_wrong_script_uk)
    ValidationError.WRONG_SCRIPT_EN -> context.getString(R.string.err_wrong_script_en)
}

/** @return true if valid; otherwise sets [EditText.error] on the matching field and returns false */
fun applyWordPairValidationErrors(
    context: Context,
    validation: ValidationResult,
    etUk: EditText,
    etEn: EditText
): Boolean {
    if (validation.isValid) return true
    val err = validation.error ?: return false
    val msg = err.toMessage(context)
    when (err) {
        ValidationError.BLANK_UK, ValidationError.SHORT_UK, ValidationError.WRONG_SCRIPT_UK -> etUk.error = msg
        ValidationError.BLANK_EN, ValidationError.SHORT_EN, ValidationError.WRONG_SCRIPT_EN -> etEn.error = msg
        else -> {}
    }
    return false
}
