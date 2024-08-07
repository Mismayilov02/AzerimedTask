package com.mismayilov.home.flow.create_item

sealed class CreateItemEffect {
    data class ShowLoading(val isLoading: Boolean) : CreateItemEffect()
    data class ShowError(val error: String) : CreateItemEffect()
    data class ShowToast(val message: String) : CreateItemEffect()
    data object Back : CreateItemEffect()
    data object Success : CreateItemEffect()
}