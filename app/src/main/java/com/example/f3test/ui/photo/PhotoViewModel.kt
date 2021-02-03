package com.example.f3test.ui.photo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.f3test.data.Repository
import com.example.f3test.data.photo.Photo
import javax.inject.Inject

class PhotoViewModel @Inject constructor(private val repository: Repository)
    : ViewModel() {

    val loading = MutableLiveData<Boolean>()

    private var currentResultLiveData: LiveData<PagingData<Photo>>? = null

    fun searchPhotosLiveData(albumId: String): LiveData<PagingData<Photo>> {
        val newResultLiveData: LiveData<PagingData<Photo>> =
                repository.getPhotosLiveData(albumId).cachedIn(viewModelScope)
        currentResultLiveData = newResultLiveData
        return newResultLiveData
    }
}