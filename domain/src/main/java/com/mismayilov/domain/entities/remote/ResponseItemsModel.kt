package com.mismayilov.domain.entities.remote

import com.google.gson.annotations.SerializedName

data class ResponseItemsModel(
    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("amount")
    val amount: String,

    @SerializedName("category")
    val category: String,

    @SerializedName("note")
    val note: String,

    @SerializedName("createdAt")
    val createdAt: String,

    @SerializedName("apiKey")
    val apiKey: String
)
