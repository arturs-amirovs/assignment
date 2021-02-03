package com.example.f3test.data.photo

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.f3test.Preferences
import com.example.f3test.data.network.API
import retrofit2.HttpException
import java.io.IOException

class PhotosDataSource (private val api: API, val preferences: Preferences, val albumId: String) :
    PagingSource<String, Photo>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Photo> {
        val after = params.key
        return try {
            val response = api.getPhotos(
                    albumId,
                "source",
                token = preferences.getToken() ?: "",
                after = after ?: "",
                limit = "10"
            )

            LoadResult.Page(
                data = response.data ?: emptyList(),
                prevKey = null,
                nextKey = response.pagingData?.cursor?.after
            )

        } catch (exception: IOException) {
            val error = IOException("Please Check Internet Connection")
            LoadResult.Error(error)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<String, Photo>): String? {
        TODO("Not yet implemented")
    }

}