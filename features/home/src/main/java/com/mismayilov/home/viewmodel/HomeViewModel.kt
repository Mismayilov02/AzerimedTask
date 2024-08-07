package com.mismayilov.home.viewmodel

import android.content.Context
import android.icu.text.Transliterator.Position
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.mismayilov.core.base.viewmodel.BaseViewModel
import com.mismayilov.domain.entities.remote.ItemModel
import com.mismayilov.domain.entities.remote.ResponseItemsModel
import com.mismayilov.domain.usecases.remote.DeleteItemUseCase
import com.mismayilov.domain.usecases.remote.GetItemsUseCase
import com.mismayilov.home.flow.create_item.CreateItemEffect
import com.mismayilov.home.flow.create_item.CreateItemEvent
import com.mismayilov.home.flow.create_item.CreateItemState
import com.mismayilov.home.flow.home.HomeEffect
import com.mismayilov.home.flow.home.HomeEvent
import com.mismayilov.home.flow.home.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getItemsUseCase: GetItemsUseCase,
    private val deleteItemUseCase: DeleteItemUseCase,
    @ApplicationContext private val context: Context
) : BaseViewModel<HomeState, HomeEvent, HomeEffect>() {
    override fun getInitialState(): HomeState = HomeState()

    private val items:MutableStateFlow<List<ResponseItemsModel>> = MutableStateFlow(emptyList())
    init {
        getItems()
    }

    override fun onEventChanged(event: HomeEvent) {
        when (event) {
            is HomeEvent.RefreshItems -> {
                getItems()
            }
            is HomeEvent.DeleteItem -> {
               deleteItem(event.position)
            }
            is HomeEvent.SearchItems -> {
                searchItems(event.query)
            }
            is HomeEvent.EditItem -> {
                editItem(event.position)
            }
        }
    }

    private fun editItem(position: Int) {
        val item  = Gson().toJson(getCurrentState().items[position])
        setEffect(HomeEffect.EditItem(item))
    }

    private fun searchItems(query: String) {
        val items = items.value.filter { it.name.contains(query, ignoreCase = true) }
        setState(getCurrentState().copy(items = items))
    }

    private fun deleteItem(position: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val id = items.value[position].id
            invoke(kSuspendFunction = { deleteItemUseCase(id) },
                onError = { e ->
                    Log.e("HomeViewModel", e.message.toString())
                    setEffect(HomeEffect.DeleteItemError(position))
                    setEffect(HomeEffect.ShowToast(context.getString(com.mismayilov.uikit.R.string.item_not_deleted)))
                    setEffect(HomeEffect.ShowLoading(false))
                },
                onSuccess = { result ->
                    if (result.message == "Item deleted successfully.") {
                        setEffect(HomeEffect.ShowLoading(false))
                        setEffect(HomeEffect.ShowToast(context.getString(com.mismayilov.uikit.R.string.item_deleted)))
                        setState(getCurrentState().copy(items = getCurrentState().items.filter { it.id != id}))
                    } else {
                        setEffect(HomeEffect.DeleteItemError(position))
                        setEffect(HomeEffect.ShowToast(context.getString(com.mismayilov.uikit.R.string.item_not_deleted)))
                        setEffect(HomeEffect.ShowLoading(false))
                    }
                })
        }
    }

    private fun getItems() {
        setEffect(HomeEffect.ShowLoading(true))
        viewModelScope.launch(Dispatchers.IO) {
            invoke(kSuspendFunction = { getItemsUseCase() },
                onError = { e ->
                    Log.e("HomeViewModel", e.message.toString())
                    setEffect(HomeEffect.ShowError(e.message.toString()))
                    setEffect(HomeEffect.ShowLoading(false))
                },
                onSuccess = { result ->
                    setEffect(HomeEffect.ShowLoading(false))
                    items.value =result
                    setState(HomeState(items = result))
                })
        }
    }
}