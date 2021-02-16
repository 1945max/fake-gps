package id.xxx.fake.test

import android.app.Application
import androidx.paging.ExperimentalPagingApi
import id.xxx.fake.test.di.AppModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

@FlowPreview
@ExperimentalPagingApi
@ExperimentalCoroutinesApi
class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@App)
            modules(AppModule.modules)
        }
    }
}