package com.example.f3test.data

import com.google.gson.annotations.SerializedName

data class PagingResp(
    @SerializedName("cursors")
    var cursor: Cursor?
) {
    data class Cursor(
        @SerializedName("before")
        var before: String?,
        @SerializedName("after")
        var after: String?
    )
}