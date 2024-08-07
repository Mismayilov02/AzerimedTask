package com.mismayilov.domain.usecases.remote

import com.mismayilov.domain.entities.remote.CategoriesModel
import com.mismayilov.domain.entities.remote.ResponseItemsModel
import com.mismayilov.domain.repositories.NetworkRepository
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val networkRepository: NetworkRepository
) {
    suspend operator fun invoke(): List<CategoriesModel> {
        return networkRepository.getCategories()
    }
}