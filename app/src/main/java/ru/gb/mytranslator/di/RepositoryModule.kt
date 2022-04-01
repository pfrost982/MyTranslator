package ru.gb.mytranslator.di

import dagger.Module
import dagger.Provides
import ru.gb.mytranslator.model.RepositoryImpl
import ru.gb.mytranslator.model.retrofit.RetrofitApiService
import ru.gb.mytranslator.view_model.Repository
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Singleton
    @Provides
    fun provideRepository(api: RetrofitApiService): Repository {
        return RepositoryImpl(api)
    }
}