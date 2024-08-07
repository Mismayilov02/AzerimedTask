package com.mismayilov.home.flow.home

import com.mismayilov.home.flow.create_item.CreateItemEffect

sealed class HomeEffect {
    data class ShowLoading(val isLoading: Boolean) : HomeEffect()
    data class ShowError(val error: String) : HomeEffect()
    data class ShowToast(val message: String) : HomeEffect()
    data class EditItem(val item:String) : HomeEffect()
    data class DeleteItemError(val position: Int) : HomeEffect()
    data object Back : HomeEffect()
}