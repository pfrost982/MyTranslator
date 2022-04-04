package ru.gb.mytranslator.model.retrofit

import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query
import ru.gb.mytranslator.model.data.DataModel

interface RetrofitApiService {

    @GET("words/search")
    fun searchAsync(@Query("search") wordToSearch: String): Deferred<List<DataModel>>
}
