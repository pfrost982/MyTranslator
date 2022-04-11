package ru.gb.mytranslator.di

import androidx.room.Room
import org.koin.dsl.module
import ru.gb.mytranslator.model.RepositoryImpl
import ru.gb.mytranslator.model.retrofit.RetrofitImpl
import ru.gb.mytranslator.model.room.HistoryDataBase
import ru.gb.mytranslator.view_model.MainViewModel
import ru.gb.mytranslator.view_model.Repository


val application = module {
    single { Room.databaseBuilder(get(), HistoryDataBase::class.java, "HistoryDB").build() }
    single { get<HistoryDataBase>().historyDao() }
    single<Repository> {
        RepositoryImpl(
            apiService = RetrofitImpl().getService(),
            historyDao = get()
        )
    }
}

val mainScreen = module {
    factory { MainViewModel(repository = get()) }
}
