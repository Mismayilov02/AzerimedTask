package com.mismayilov.home.flow.create_item

import com.mismayilov.domain.entities.remote.ItemModel

sealed class CreateItemEvent {
    data class CreateItem(val itemModel: ItemModel,val isUpdate:Boolean) : CreateItemEvent()
    data object RefreshCategories : CreateItemEvent()
}