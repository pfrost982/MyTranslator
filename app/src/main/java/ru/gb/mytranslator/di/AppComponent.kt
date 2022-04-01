package ru.gb.mytranslator.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.gb.mytranslator.view.MainActivity
import ru.gb.mytranslator.view_model.Repository
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        RetrofitModule::class,
        RepositoryModule::class
    ]
)
interface AppComponent {
    @Component.Builder
    interface Builder {

        @BindsInstance
        fun setContext(context: Context): Builder
        fun build(): AppComponent
    }

    fun inject(activity: MainActivity)
}
