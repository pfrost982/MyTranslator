package ru.gb.mytranslator

import android.app.Application
import ru.gb.mytranslator.di.AppComponent
import ru.gb.mytranslator.di.DaggerAppComponent

class App : Application() {

    private lateinit var appComponent: AppComponent
    override fun onCreate() {
        super.onCreate()
        instance = this
        appComponent = DaggerAppComponent.builder().setContext(this).build()
    }

    companion object{
        lateinit var instance: App
    }

    fun getAppComponent(): AppComponent{
        return appComponent
    }
}
