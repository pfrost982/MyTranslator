package ru.gb.mytranslator.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import ru.gb.mytranslator.model.RepositoryImpl
import ru.gb.mytranslator.model.data.AppState

class MainViewModel(
    private val repository: Repository = RepositoryImpl(),
    private val liveDataForViewToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val compositeDisposable: CompositeDisposable = CompositeDisposable(),
) : ViewModel() {

    private var appState: AppState? = null

    fun getData(word: String, isOnline: Boolean): LiveData<AppState> {
        compositeDisposable.add(
            repository.getData(word, isOnline).map {
                AppState.Success(it)
            }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { liveDataForViewToObserve.value = AppState.Loading(null) }
                .subscribeWith(getObserver())
        )
        return liveDataForViewToObserve
    }

    private fun getObserver(): DisposableObserver<AppState> {
        return object : DisposableObserver<AppState>() {

            override fun onNext(state: AppState) {
                appState = state
                liveDataForViewToObserve.value = state
            }

            override fun onError(e: Throwable) {
                liveDataForViewToObserve.value = AppState.Error(e)
            }

            override fun onComplete() {
            }
        }
    }
}
