package com.feluts.peliscatalog.model

import com.google.gson.annotations.SerializedName

data class PostResp(
    @SerializedName("status_code") val status: Int,
    @SerializedName("status_message") val mensaje: String
)