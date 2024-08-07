package com.mismayilov.data.api

import com.mismayilov.domain.entities.remote.CategoriesModel
import com.mismayilov.domain.entities.remote.ItemModel
import com.mismayilov.domain.entities.remote.ResponseItemModel
import com.mismayilov.domain.entities.remote.ResponseItemsModel
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface NetworkService {

    @GET("/items")
    suspend fun getExchangeRate(
        @Query("key") key: String = "zm1an242"
    ): List<ResponseItemsModel>

    @GET("/categories")
    suspend fun getCategories(): List<CategoriesModel>

    @POST("/items")
    suspend fun putItem(
        @Body itemModel: ItemModel
    ): ResponseItemModel

    @DELETE("/items/{id}")
    suspend fun deleteItem(
        @Path("id") id: String
    ): ResponseItemModel

    @PUT("/items")
    suspend fun updateItem(
        @Body itemModel: ItemModel
    ): ResponseItemModel

}