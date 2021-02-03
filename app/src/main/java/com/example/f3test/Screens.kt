package com.example.f3test

import androidx.fragment.app.Fragment
import com.example.f3test.ui.album.AlbumFrg
import com.example.f3test.ui.home.HomeFrg
import com.example.f3test.ui.login.LoginFrg
import com.example.f3test.ui.photo.PhotoFrg
import ru.terrakok.cicerone.android.support.SupportAppScreen


class Screens {

    class LoginScr : SupportAppScreen() {

        override fun getFragment(): Fragment {
            return LoginFrg()
        }

    }

    class HomeScr : SupportAppScreen() {

        override fun getFragment(): Fragment {
            return HomeFrg()
        }

    }

    class AlbumScr : SupportAppScreen() {

        override fun getFragment(): Fragment {
            return AlbumFrg()
        }

    }

    class PhotoScr constructor(private var albumId: String): SupportAppScreen() {

        override fun getFragment(): Fragment {
            return PhotoFrg(albumId)
        }

    }

}