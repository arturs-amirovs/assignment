package com.example.f3test.data.albums

import com.example.f3test.data.PagingResp
import com.google.gson.annotations.SerializedName


data class Albums(
    @SerializedName("data")
    var albumsList: List<Album>?,
    @SerializedName("paging")
    var pagingData: PagingResp?
)
