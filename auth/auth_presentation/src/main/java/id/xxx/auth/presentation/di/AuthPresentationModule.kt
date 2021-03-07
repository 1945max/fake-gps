package id.xxx.auth.presentation.di

import id.xxx.auth.presentation.ui.sign.`in`.SignInWithEmailViewModel
import id.xxx.auth.presentation.ui.sign.up.SignUpViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.dsl.module

@ExperimentalCoroutinesApi
object AuthPresentationModule {
    private val signUpViewModel = module {
        single { SignUpViewModel(get()) }
    }

    private val signInViewModel = module {
        single { SignInWithEmailViewModel(get()) }
    }

    val modules = listOf(
        signUpViewModel,
        signInViewModel
    )
}