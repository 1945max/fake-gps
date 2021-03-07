package id.xxx.auth.presentation.ui.sign

import android.util.Patterns
import id.xxx.auth.domain.model.InputValidation

object Utils {
    fun nameIsValid(value: String): Boolean {
        return value.isNotBlank()
    }

    fun emailIsValid(value: String): Boolean {
        return !Patterns.EMAIL_ADDRESS.matcher(value).matches()
    }

    fun passwordValidation(password: String): InputValidation<String> {
        if (password.contains(" ")) return InputValidation.NotValid("Password Can Not Contain With Space")
        if (password.length <= 5) return InputValidation.NotValid("Minimum Length Password 6 Character")
        return InputValidation.Valid
    }

    fun tokenIsValid(token: String): Boolean {
        return token.length > 10
    }
}