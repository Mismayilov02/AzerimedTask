package com.mismayilov.domain.repositories

import com.mismayilov.domain.entities.remote.CategoriesModel
import com.mismayilov.domain.entities.remote.ItemModel
import com.mismayilov.domain.entities.remote.ResponseItemModel
import com.mismayilov.domain.entities.remote.ResponseItemsModel

interface NetworkRepository {
    suspend fun putItem(itemModel: ItemModel): ResponseItemModel
    suspend fun getItems(): List<ResponseItemsModel>
    suspend fun getCategories(): List<CategoriesModel>
    suspend fun deleteItem(id: String): ResponseItemModel
    suspend fun updateItem(itemModel: ItemModel): ResponseItemModel
}