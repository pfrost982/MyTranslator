package ru.gb.mytranslator.di

import dagger.Module
import dagger.Provides
import ru.gb.mytranslator.model.retrofit.RetrofitApiService
import ru.gb.mytranslator.model.retrofit.RetrofitImpl
import javax.inject.Singleton

@Module
class RetrofitModule {

    @Singleton
    @Provides
    fun provideApiService(): RetrofitApiService {
        return RetrofitImpl().getService()
    }
}