package ru.gb.mytranslator.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import ru.gb.mytranslator.model.data.AppState

class MainViewModel (private val repository: Repository) : ViewModel() {

    private val _liveDataForViewToObserve: MutableLiveData<AppState> = MutableLiveData<AppState>()
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    private var appState: AppState? = null
    private val liveDataForViewToObserve: LiveData<AppState>
        get() = _liveDataForViewToObserve

    fun getData(word: String, isOnline: Boolean): LiveData<AppState> {
        compositeDisposable.add(
            repository.getData(word, isOnline).map {
                AppState.Success(it)
            }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { _liveDataForViewToObserve.value = AppState.Loading(null) }
                .subscribeWith(getObserver())
        )
        return liveDataForViewToObserve
    }

    private fun getObserver(): DisposableObserver<AppState> {
        return object : DisposableObserver<AppState>() {

            override fun onNext(state: AppState) {
                appState = state
                _liveDataForViewToObserve.value = state
            }

            override fun onError(e: Throwable) {
                _liveDataForViewToObserve.value = AppState.Error(e)
            }

            override fun onComplete() {
            }
        }
    }
}
