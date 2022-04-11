package ru.gb.mytranslator.model

import ru.gb.mytranslator.model.data.DataModel
import ru.gb.mytranslator.model.retrofit.RetrofitApiService
import ru.gb.mytranslator.model.room.HistoryDao
import ru.gb.mytranslator.model.room.HistoryEntity
import ru.gb.mytranslator.view_model.Repository

class RepositoryImpl (private val apiService: RetrofitApiService, private val historyDao: HistoryDao) : Repository {

    override suspend fun getData(word: String, isOnline: Boolean): List<DataModel> {
        return apiService.searchAsync(word).await()
    }

    override suspend fun saveToDB(data: DataModel) {
        val text = data.text ?: ""
        val description = data.meanings?.get(0)?.translation?.translation
        val historyEntity = HistoryEntity(text, description)
        historyDao.insert(historyEntity)
    }

    override suspend fun getFromDB(): List<HistoryEntity> {
        return historyDao.all()
    }

}
