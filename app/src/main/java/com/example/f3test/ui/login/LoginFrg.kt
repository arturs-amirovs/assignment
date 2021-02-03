package com.example.f3test.ui.login

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.example.f3test.Preferences
import com.example.f3test.R
import com.example.f3test.Screens
import com.example.f3test.databinding.LoginFrgBinding
import com.facebook.*
import com.facebook.login.LoginResult
import dagger.android.support.DaggerAppCompatActivity
import dagger.android.support.DaggerFragment
import ru.terrakok.cicerone.Router
import javax.inject.Inject


class LoginFrg : DaggerFragment() {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject lateinit var router: Router
    @Inject lateinit var preferences: Preferences

    private lateinit var viewModel: LoginViewModel
    private lateinit var callbackManager: CallbackManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<LoginFrgBinding>(
            inflater,
            R.layout.fragment_login,
            container,
            false
        )
        (activity as DaggerAppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        val view = binding.root
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(LoginViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        init(viewModel, binding)
        return view
    }

    private fun init(viewModel: LoginViewModel, view: LoginFrgBinding) {
        callbackManager = CallbackManager.Factory.create()
        view.loginButton.setReadPermissions("public_profile", "user_photos")
        view.loginButton.fragment = this
        if(isLoggedIn()) {
            val mHandler = Handler()
            mHandler.post {
                router.newRootScreen(Screens.HomeScr())
            }
        }
        view.loginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult?> {
            override fun onSuccess(loginResult: LoginResult?) {
                Log.e("TTTT", "onSuccess")
                preferences.setUserID(loginResult?.accessToken?.userId)
                preferences.setToken(loginResult?.accessToken?.token)
                router.newRootScreen(Screens.HomeScr())
            }

            override fun onCancel() {
                Log.e("TTTT", "onCancel")
                // TODO handle this case
            }

            override fun onError(exception: FacebookException) {
                Toast.makeText(context, exception.message, Toast.LENGTH_LONG).show()
                // TODO handle this case
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    fun isLoggedIn(): Boolean {
        return AccessToken.getCurrentAccessToken() != null && Profile.getCurrentProfile() != null
    }
}