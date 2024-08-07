package com.mismayilov.domain.entities.remote

import com.google.gson.annotations.SerializedName

data class ResponseItemModel(
    @SerializedName("message")
    val message: String,
)
