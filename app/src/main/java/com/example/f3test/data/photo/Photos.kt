package com.example.f3test.data.photo

import com.example.f3test.data.PagingResp
import com.google.gson.annotations.SerializedName


data class Photos(
    @SerializedName("data")
    var data: List<Photo>?,
    @SerializedName("paging")
    var pagingData: PagingResp?
)
