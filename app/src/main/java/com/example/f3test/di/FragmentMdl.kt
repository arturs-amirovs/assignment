package com.example.f3test.di

import com.example.f3test.ui.album.AlbumFrg
import com.example.f3test.ui.home.HomeFrg
import com.example.f3test.ui.login.LoginFrg
import com.example.f3test.ui.photo.PhotoFrg
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class FragmentMdl {

    @ContributesAndroidInjector
    abstract fun loginFrg(): LoginFrg

    @ContributesAndroidInjector
    abstract fun homeFrg(): HomeFrg

    @ContributesAndroidInjector
    abstract fun albumFrg(): AlbumFrg

    @ContributesAndroidInjector
    abstract fun photoFrg(): PhotoFrg

}