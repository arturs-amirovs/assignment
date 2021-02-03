package com.example.f3test.data

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.f3test.Preferences
import com.example.f3test.data.albums.Album
import com.example.f3test.data.albums.AlbumsDataSource
import com.example.f3test.data.network.API
import com.example.f3test.data.photo.Photo
import com.example.f3test.data.photo.PhotosDataSource
import javax.inject.Inject

class Repository @Inject constructor(private val api: API, private val preferences: Preferences) {


    companion object {
        @Volatile private var instance: Repository? = null

        fun getInstance(api: API, preferences: Preferences) =
            instance ?: synchronized(this) {
                instance ?: Repository(api, preferences).also { instance = it }
            }
    }

    fun getAlbumsLiveData(): LiveData<PagingData<Album>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = true, pageSize = 5),
            pagingSourceFactory = {
                AlbumsDataSource(api, preferences)
            }
        ).liveData
    }

    fun getPhotosLiveData(albumId: String): LiveData<PagingData<Photo>> {
        return Pager(
                config = PagingConfig(enablePlaceholders = true, pageSize = 10),
                pagingSourceFactory = {
                    PhotosDataSource(api, preferences, albumId)
                }
        ).liveData
    }
}