package ru.gb.mytranslator.view_model

import ru.gb.mytranslator.model.data.DataModel
import ru.gb.mytranslator.model.room.HistoryEntity

interface Repository {

    suspend fun getData(word: String, isOnline: Boolean): List<DataModel>
    suspend fun saveToDB(data: DataModel)
    suspend fun getFromDB(): List<HistoryEntity>
}
