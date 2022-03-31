package ru.gb.mytranslator

import android.app.Application
import ru.gb.mytranslator.di.AppComponent
import ru.gb.mytranslator.di.DaggerAppComponent

class App : Application() {

    class App: Application() {

        lateinit var appComponent: AppComponent

        override fun onCreate() {
            super.onCreate()
            appComponent = DaggerAppComponent.create()
        }
    }
}
