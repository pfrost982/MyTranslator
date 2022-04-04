package ru.gb.mytranslator.di

import org.koin.dsl.module
import ru.gb.mytranslator.model.RepositoryImpl
import ru.gb.mytranslator.model.retrofit.RetrofitImpl
import ru.gb.mytranslator.view_model.MainViewModel
import ru.gb.mytranslator.view_model.Repository


val application = module {
    single<Repository> { RepositoryImpl(apiService = RetrofitImpl().getService()) }
}

val mainScreen = module {
    factory { MainViewModel(repository = get()) }
}
