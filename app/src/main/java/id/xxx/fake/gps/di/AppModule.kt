package id.xxx.fake.gps.di

import id.xxx.base.utils.Executors
import id.xxx.fake.gps.data.di.FakeDataModule
import id.xxx.fake.gps.data.di.MapBoxModule
import id.xxx.fake.gps.data.di.ViewModelModule
import org.koin.dsl.module

object AppModule {
    private val fakeDataModules = arrayOf(
        FakeDataModule.databaseModule,
        FakeDataModule.dataSource,
        FakeDataModule.repositoryModule,
        FakeDataModule.useCaseModule
    )
    private val mapBoxModules = arrayOf(
        MapBoxModule.networkModule,
        MapBoxModule.databaseModule,
        MapBoxModule.dataSource,
        MapBoxModule.repositoryModule,
        MapBoxModule.useCaseModule
    )

    private val viewModelModules = arrayOf(
        ViewModelModule.viewModelModule
    )
    val modules = listOf(
        module { single { Executors() } },
        *fakeDataModules,
        *mapBoxModules,
        *viewModelModules
    )
}