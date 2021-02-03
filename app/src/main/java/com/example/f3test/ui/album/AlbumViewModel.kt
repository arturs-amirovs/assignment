package com.example.f3test.ui.album

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.f3test.data.Repository
import com.example.f3test.data.albums.Album
import javax.inject.Inject

class AlbumViewModel @Inject constructor(private val repository: Repository)
    : ViewModel() {

    val loading = MutableLiveData<Boolean>()

    private var currentResultLiveData: LiveData<PagingData<Album>>? = null

    fun searchAlbumsLiveData(): LiveData<PagingData<Album>> {
        if(currentResultLiveData != null) return currentResultLiveData as LiveData<PagingData<Album>>

        currentResultLiveData = repository.getAlbumsLiveData().cachedIn(viewModelScope)
        return currentResultLiveData as LiveData<PagingData<Album>>
    }
}