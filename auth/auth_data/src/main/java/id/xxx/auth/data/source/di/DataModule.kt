package id.xxx.auth.data.source.di

import com.google.firebase.auth.FirebaseAuth
import id.xxx.auth.data.source.repository.RepositoryImpl
import id.xxx.auth.domain.repository.IRepository
import org.koin.dsl.module

object DataModule {
    private val repository = module {
        single<IRepository> { RepositoryImpl(FirebaseAuth.getInstance()) }
    }

    val modules = listOf(repository)
}