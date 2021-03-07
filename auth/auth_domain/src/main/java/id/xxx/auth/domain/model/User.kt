package id.xxx.auth.domain.model

import id.xxx.base.domain.model.BaseModel

data class User(
    override val id: String,
    val name: String,
    val email: String,
    val isEmailVerify: Boolean,
    val loginMode: LoginMode
) : BaseModel<String> {
    enum class LoginMode {
        WITH_EMAIL, WITH_TOKEN
    }
}