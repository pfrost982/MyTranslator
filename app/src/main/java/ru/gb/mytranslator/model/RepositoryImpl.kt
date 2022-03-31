package ru.gb.mytranslator.model

import ru.gb.mytranslator.model.data.DataModel
import ru.gb.mytranslator.model.retrofit.RetrofitImpl
import io.reactivex.Observable
import ru.gb.mytranslator.view_model.Repository

class RepositoryImpl(
    private val remoteProvider: RetrofitImpl = RetrofitImpl()
) : Repository {

    override fun getData(word: String, isOnline: Boolean): Observable<List<DataModel>> {
        return remoteProvider.getData(word)
    }
}
