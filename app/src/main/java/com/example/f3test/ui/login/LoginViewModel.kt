package com.example.f3test.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.f3test.data.Repository
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val repository: Repository)
    : ViewModel() {

    val loading = MutableLiveData<Boolean>()
}