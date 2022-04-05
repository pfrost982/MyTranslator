package ru.gb.mytranslator.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import ru.gb.mytranslator.model.data.AppState

class MainViewModel(private val repository: Repository) : ViewModel() {

    private val _liveDataForViewToObserve: MutableLiveData<AppState> = MutableLiveData<AppState>()

    private val liveDataForViewToObserve: LiveData<AppState>
        get() = _liveDataForViewToObserve

    private val viewModelCoroutineScope = CoroutineScope(
        Dispatchers.IO
                + SupervisorJob()
                + CoroutineExceptionHandler { _, throwable ->
            handleError(throwable)
        })

    private var job: Job? = null

    fun subscribe(): LiveData<AppState> {
        return liveDataForViewToObserve
    }

    private fun handleError(error: Throwable) {
        _liveDataForViewToObserve.postValue(AppState.Error(error))
    }

    fun getData(word: String, isOnline: Boolean) {
        _liveDataForViewToObserve.value = AppState.Loading(null)
        job?.cancel()
        job = viewModelCoroutineScope.launch {
            _liveDataForViewToObserve.postValue(
                AppState.Success(repository.getData(word, isOnline))
            )
        }
    }

    override fun onCleared() {
        viewModelCoroutineScope.coroutineContext.cancel()
        super.onCleared()
    }
}
