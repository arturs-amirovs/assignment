package com.example.f3test.di

import com.example.f3test.App
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
        modules = [
            AndroidSupportInjectionModule::class,
            ViewModelModule::class,
            RetrofitMdl::class,
            ActivityMdl::class,
            FragmentMdl::class
        ]
)
interface AppCmp : AndroidInjector<App> {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: App): AppCmp
    }
}