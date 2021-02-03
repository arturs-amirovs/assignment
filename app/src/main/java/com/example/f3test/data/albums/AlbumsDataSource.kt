package com.example.f3test.data.albums

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.f3test.Preferences
import com.example.f3test.data.network.API
import retrofit2.HttpException
import java.io.IOException

class AlbumsDataSource (private val api: API, val preferences: Preferences) :
    PagingSource<String, Album>() {

    private var firstTime = true;

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Album> {
        val after = params.key
        return try {
            val response = api.getAlbumsList(
                preferences.getUserID(),
                "name,count,type,photos.limit(1){source}",
                token = preferences.getToken() ?: "",
                after = after ?: "",
                limit = "5"
            )
            var response2: Albums? = null
            if(firstTime) {
                // TODO it is a bit overkill to get all albums only to get Profile album,
                    //  but I didn't find a way to get only profile album from Graph API,
                        //  therefore pagination isn't really useful here, as we get all albums beforehand,
                            //  but I will keep it for demonstration purposes
                response2 = api.getAlbumsList(
                        preferences.getUserID(),
                        "name,count,type,photos.limit(1){source}",
                        token = preferences.getToken() ?: ""
                )
                firstTime = false
            }

            LoadResult.Page(
                data = order(response.albumsList, response2?.albumsList?.filter { it.type.equals("profile") }),
                prevKey = null,
                nextKey = response.pagingData?.cursor?.after
            )

        } catch (exception: IOException) {
            // TODO handle cases when internet not available
            val error = IOException("Please Check Internet Connection")
            LoadResult.Error(error)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    private fun order(list: List<Album>?, profileAlbum: List<Album>?): List<Album> {
        val orderedList = list?.filter { it.type.equals("normal") }?.toMutableList()
        profileAlbum?.get(0)?.let { it1 -> orderedList?.add(0, it1) }
        return orderedList ?: emptyList()
    }

    override fun getRefreshKey(state: PagingState<String, Album>): String? {
        TODO("Not yet implemented")
    }

}