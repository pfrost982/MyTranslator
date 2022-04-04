package ru.gb.mytranslator.view_model

import ru.gb.mytranslator.model.data.DataModel

interface Repository {

    suspend fun getData(word: String, isOnline: Boolean): List<DataModel>
}
