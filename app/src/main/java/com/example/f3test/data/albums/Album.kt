package com.example.f3test.data.albums

import com.example.f3test.data.PagingResp
import com.google.gson.annotations.SerializedName

data class Album(
    @SerializedName("id")
    var id: String?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("type")
    var type: String?,
    @SerializedName("photos")
    var photo: Photo?,
    @SerializedName("count")
    var count: String?
) {
    data class Photo(
        @SerializedName("data")
        var data: List<PhotoData>?
    )

    data class PhotoData(
        @SerializedName("source")
        var url: String
    )
}