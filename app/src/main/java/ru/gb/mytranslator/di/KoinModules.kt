package ru.gb.mytranslator.di

import androidx.room.Room
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.gb.mytranslator.model.RepositoryImpl
import ru.gb.mytranslator.model.retrofit.RetrofitImpl
import ru.gb.mytranslator.model.room.HistoryDao
import ru.gb.mytranslator.model.room.HistoryDataBase
import ru.gb.mytranslator.view.MainActivity
import ru.gb.mytranslator.view_model.MainViewModel
import ru.gb.mytranslator.view_model.Repository


val application = module {
    single<HistoryDataBase> {
        Room.databaseBuilder(get(), HistoryDataBase::class.java, "HistoryDB").build()
    }
    single<HistoryDao> { get<HistoryDataBase>().historyDao() }
    single<Repository> {
        RepositoryImpl(
            apiService = RetrofitImpl().getService(),
            historyDao = get()
        )
    }
}

val mainScreen = module {
    scope(named<MainActivity>()) {
        scoped { MainViewModel(repository = get()) }
    }
}
