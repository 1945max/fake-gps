package id.xxx.auth.data.source.repository

import com.google.firebase.auth.FirebaseUser
import id.xxx.auth.domain.model.User

fun FirebaseUser.toUser(loginMode: User.LoginMode) = User(
    id = uid,
    name = displayName ?: "-",
    email = email ?: "-",
    isEmailVerify = isEmailVerified,
    loginMode = loginMode
)