package com.mismayilov.home.flow.home

import com.mismayilov.home.flow.create_item.CreateItemEvent

sealed class HomeEvent {
    data class DeleteItem(val position: Int) : HomeEvent()
    data class EditItem(val position: Int) : HomeEvent()
    data class SearchItems(val query: String) : HomeEvent()
    data object RefreshItems : HomeEvent()
}