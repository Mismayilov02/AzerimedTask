package com.mismayilov.home.flow.create_item

import com.mismayilov.domain.entities.remote.CategoriesModel


data class CreateItemState (
val category: List<CategoriesModel> = emptyList(),
)