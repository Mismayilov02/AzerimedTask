package com.mismayilov.domain.usecases.remote

import com.mismayilov.domain.entities.remote.ResponseItemsModel
import com.mismayilov.domain.repositories.NetworkRepository
import javax.inject.Inject

class GetItemsUseCase @Inject constructor(
    private val networkRepository: NetworkRepository
) {
    suspend operator fun invoke(): List<ResponseItemsModel> {
        return networkRepository.getItems()
    }
}