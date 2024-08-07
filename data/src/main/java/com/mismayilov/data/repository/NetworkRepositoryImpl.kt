package com.mismayilov.data.repository
import com.mismayilov.data.api.NetworkService
import com.mismayilov.domain.entities.remote.CategoriesModel
import com.mismayilov.domain.entities.remote.ItemModel
import com.mismayilov.domain.entities.remote.ResponseItemModel
import com.mismayilov.domain.entities.remote.ResponseItemsModel
import com.mismayilov.domain.repositories.NetworkRepository
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(
    private val networkService: NetworkService
) : NetworkRepository {
    override suspend fun putItem(itemModel: ItemModel): ResponseItemModel {
       return networkService.putItem(itemModel)
    }
    override suspend fun getItems(): List<ResponseItemsModel> {
        return networkService.getExchangeRate()
    }

    override suspend fun getCategories(): List<CategoriesModel> {
        return networkService.getCategories()
    }

    override suspend fun deleteItem(id: String):ResponseItemModel {
        return networkService.deleteItem(id)
    }

    override suspend fun updateItem(itemModel: ItemModel): ResponseItemModel {
        return networkService.updateItem(itemModel)
    }

}