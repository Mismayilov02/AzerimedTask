package com.mismayilov.domain.usecases.remote

import com.mismayilov.domain.entities.remote.ItemModel
import com.mismayilov.domain.entities.remote.ResponseItemModel
import com.mismayilov.domain.repositories.NetworkRepository
import javax.inject.Inject

class PostItemUseCase @Inject constructor(
    private val networkRepository: NetworkRepository
) {
    suspend operator fun invoke(itemsModel: ItemModel, isUpdate:Boolean): ResponseItemModel {
        return if (isUpdate) {
            networkRepository.updateItem(itemsModel)
        } else {
            networkRepository.putItem(itemsModel)
        }
    }
}