package com.example.f3test

import com.example.f3test.di.DaggerAppCmp
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication


class App : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {

        return DaggerAppCmp.factory().create(this)
    }

}