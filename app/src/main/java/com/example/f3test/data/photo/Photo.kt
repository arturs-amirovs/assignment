package com.example.f3test.data.photo

import com.example.f3test.data.PagingResp
import com.google.gson.annotations.SerializedName

data class Photo(
    @SerializedName("source")
    var url: String?,
    @SerializedName("id")
    var id: String?
)