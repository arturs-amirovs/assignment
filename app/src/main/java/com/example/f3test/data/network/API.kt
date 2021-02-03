package com.example.f3test.data.network

import com.example.f3test.data.albums.Albums
import com.example.f3test.data.photo.Photos
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface API {
    @GET("{userId}/albums")
    suspend fun getAlbumsList(
        @Path("userId") userId: String? = "",
        @Query("fields") fields: String,
        @Query("access_token") token: String,
        @Query("after") after: String = "",
        @Query("limit") limit: String = ""
    ): Albums

    @GET("{albumId}/photos")
    suspend fun getPhotos(
            @Path("albumId") albumId: String? = "",
            @Query("fields") fields: String,
            @Query("access_token") token: String,
            @Query("after") after: String = "",
            @Query("limit") limit: String = ""
    ): Photos
}