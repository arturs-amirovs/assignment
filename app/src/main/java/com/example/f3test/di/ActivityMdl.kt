package com.example.f3test.di

import android.app.Application
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.example.f3test.App
import com.example.f3test.MainAct
import com.example.f3test.Preferences
import com.example.f3test.data.*
import com.example.f3test.data.network.API
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import javax.inject.Inject
import javax.inject.Singleton


@Module
abstract class ActivityMdl {

    @ContributesAndroidInjector(
        modules = [
            ViewModelModule::class,
            RetrofitMdl::class,
            FragmentMdl::class
        ]
    )
    abstract fun contributeMyActivity(): MainAct

    companion object {
        private val cicerone = Cicerone.create()

        @Singleton @Provides
        fun repository(api: API, preferences: Preferences) : Repository{
            return Repository.getInstance(api, preferences)
        }

        @Provides @Singleton internal fun provideRouter() : Router {
            return cicerone.router
        }

        @Provides @Singleton internal fun provideNavigatorHolder(): NavigatorHolder {
            return cicerone.navigatorHolder
        }

        @Singleton @Provides
        fun preferences(context: App) : Preferences{
            return Preferences.getInstance(context.getSharedPreferences(null, MODE_PRIVATE))
        }
    }

}