package com.mismayilov.home.flow.home

import com.mismayilov.domain.entities.remote.ResponseItemsModel


data class HomeState(
    val items: List<ResponseItemsModel> = emptyList(),
)