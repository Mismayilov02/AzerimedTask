package com.mismayilov.domain.entities.remote

import com.google.gson.annotations.SerializedName

data class CategoriesModel(
    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String
)
