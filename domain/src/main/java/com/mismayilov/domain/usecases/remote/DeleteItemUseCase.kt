package com.mismayilov.domain.usecases.remote

import com.mismayilov.domain.entities.remote.CategoriesModel
import com.mismayilov.domain.entities.remote.ItemModel
import com.mismayilov.domain.entities.remote.ResponseItemModel
import com.mismayilov.domain.entities.remote.ResponseItemsModel
import com.mismayilov.domain.repositories.NetworkRepository
import javax.inject.Inject

class DeleteItemUseCase @Inject constructor(
    private val networkRepository: NetworkRepository
) {
    suspend operator fun invoke(id:String): ResponseItemModel {
        return networkRepository.deleteItem(id)
    }
}