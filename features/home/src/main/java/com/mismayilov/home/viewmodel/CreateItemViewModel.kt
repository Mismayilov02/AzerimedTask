package com.mismayilov.home.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.mismayilov.core.base.viewmodel.BaseViewModel
import com.mismayilov.domain.entities.remote.ItemModel
import com.mismayilov.domain.usecases.remote.GetCategoriesUseCase
import com.mismayilov.domain.usecases.remote.PostItemUseCase
import com.mismayilov.home.flow.create_item.CreateItemEffect
import com.mismayilov.home.flow.create_item.CreateItemEvent
import com.mismayilov.home.flow.create_item.CreateItemState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateItemViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val postItemUseCase: PostItemUseCase,
    @ApplicationContext private val context: Context
) : BaseViewModel<CreateItemState, CreateItemEvent, CreateItemEffect>() {
    override fun getInitialState(): CreateItemState = CreateItemState()

    override fun onEventChanged(event: CreateItemEvent) {
        when (event) {
            is CreateItemEvent.CreateItem -> {
                putItem(event.itemModel,event.isUpdate)
            }
            is CreateItemEvent.RefreshCategories -> {
                getCategories()
            }
        }
    }

    init {
        setEffect(CreateItemEffect.ShowLoading(true))
        getCategories()
    }

    private fun putItem(itemModel: ItemModel,isUpdate:Boolean) {
        if (validateItemModel(itemModel)) {
            setEffect(CreateItemEffect.ShowToast(context.getString(com.mismayilov.uikit.R.string.please_fill_all_fields)))
            return
        }
        setEffect(CreateItemEffect.ShowLoading(true))
        viewModelScope.launch(Dispatchers.IO) {
            invoke(kSuspendFunction = { postItemUseCase(itemModel,isUpdate) },
                onError = { e ->
                    Log.e("CreateItemViewModel", e.message.toString())
                    setEffect(CreateItemEffect.ShowError(e.message.toString()))
                    setEffect(CreateItemEffect.ShowLoading(false))
                },
                onSuccess = { result ->
                    if (result.message == "Item created successfully." || result.message == "Item updated successfully.") {
                        setEffect(CreateItemEffect.ShowLoading(false))
                        setEffect(CreateItemEffect.ShowToast( if (isUpdate) context.getString(com.mismayilov.uikit.R.string.item_updated) else context.getString(com.mismayilov.uikit.R.string.item_created)))
                        setEffect(CreateItemEffect.Success)
                    }else{
                        setEffect(CreateItemEffect.ShowLoading(false))
                        setEffect(CreateItemEffect.ShowToast(result.message))
                    }

                })
        }

    }

    private fun getCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            invoke(kSuspendFunction = { getCategoriesUseCase() },
                onError = { e ->
                    Log.e("CreateItemViewModel", e.message.toString())
                    setEffect(CreateItemEffect.ShowError(e.message.toString()))
                    setEffect(CreateItemEffect.ShowLoading(false))
                    setEffect(CreateItemEffect.Back)
                },
                onSuccess = { result ->
                    setEffect(CreateItemEffect.ShowLoading(false))
                    setState(getCurrentState().copy(category = result))
                })
        }
    }

    private fun validateItemModel(itemModel: ItemModel): Boolean {
        return itemModel.name.isNotEmpty() && itemModel.amount != 0.0 && itemModel.category != 0 && itemModel.note.isNotEmpty()
    }
}