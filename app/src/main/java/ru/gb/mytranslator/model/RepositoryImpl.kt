package ru.gb.mytranslator.model

import io.reactivex.Observable
import ru.gb.mytranslator.model.data.DataModel
import ru.gb.mytranslator.model.retrofit.RetrofitApiService
import ru.gb.mytranslator.view_model.Repository

class RepositoryImpl (private val apiService: RetrofitApiService) : Repository {

    override fun getData(word: String, isOnline: Boolean): Observable<List<DataModel>> {
        return apiService.search(word)
    }
}
