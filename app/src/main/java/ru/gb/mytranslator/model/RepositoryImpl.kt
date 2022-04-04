package ru.gb.mytranslator.model

import ru.gb.mytranslator.model.data.DataModel
import ru.gb.mytranslator.model.retrofit.RetrofitApiService
import ru.gb.mytranslator.view_model.Repository

class RepositoryImpl (private val apiService: RetrofitApiService) : Repository {

    override suspend fun getData(word: String, isOnline: Boolean): List<DataModel> {
        return apiService.searchAsync(word).await()
    }
}
