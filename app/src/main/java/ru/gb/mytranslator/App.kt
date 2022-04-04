package ru.gb.mytranslator

import android.app.Application
import org.koin.core.context.startKoin
import ru.gb.mytranslator.di.application
import ru.gb.mytranslator.di.mainScreen

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(listOf(application, mainScreen))
        }
    }
}
