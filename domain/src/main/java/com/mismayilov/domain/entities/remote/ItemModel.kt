package com.mismayilov.domain.entities.remote

import com.google.gson.annotations.SerializedName

data class ItemModel(
    @SerializedName("name")
    val name: String,
    @SerializedName("amount")
    val amount: Double,
    @SerializedName("category")
    val category: Int,
    @SerializedName("note")
    val note: String,
    @SerializedName("key")
    val key: String,
    @SerializedName("id")
    val id: Long = 0
)