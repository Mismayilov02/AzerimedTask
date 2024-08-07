package com.mismayilov.home.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.gson.Gson
import com.mismayilov.common.manager.LoadingDialogManager
import com.mismayilov.core.base.fragment.BaseFragment
import com.mismayilov.core.managers.NavigationManager
import com.mismayilov.domain.entities.remote.CategoriesModel
import com.mismayilov.domain.entities.remote.ItemModel
import com.mismayilov.home.databinding.FragmentCreateItemBinding
import com.mismayilov.home.flow.create_item.CreateItemEffect
import com.mismayilov.home.flow.create_item.CreateItemEvent
import com.mismayilov.home.flow.create_item.CreateItemState
import com.mismayilov.home.viewmodel.CreateItemViewModel
import com.mismayilov.uikit.util.showDialog
import com.mismayilov.uikit.util.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateItemFragment :
    BaseFragment<FragmentCreateItemBinding, CreateItemViewModel, CreateItemState, CreateItemEvent, CreateItemEffect>() {

    override fun getViewModelClass(): Class<CreateItemViewModel> = CreateItemViewModel::class.java

    private val args: CreateItemFragmentArgs by navArgs()
    override val inflateBinding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCreateItemBinding =
        { inflater, container, attachToRoot ->
            FragmentCreateItemBinding.inflate(inflater, container, attachToRoot)
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as NavigationManager).bottomNavigationVisibility(false)

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnAdd.setOnClickListener {
            saveData()
        }
    }

    private fun saveData() {
        binding.apply {
            val itemModel = ItemModel(
                name = txtName.text.toString(),
                amount = txtAmount.text.isNullOrEmpty().not()
                    .let { if (it) txtAmount.text.toString().toDouble() else 0.0 },
                category = customSpinner.getSelectedItemPosition(),
                note = txtNote.text.toString(),
                key = "zm1an242",
                id = if (args.updateItem != " ") Gson().fromJson(args.updateItem, ItemModel::class.java).id else 0
            )
            setEvent(CreateItemEvent.CreateItem(itemModel,args.updateItem != " "))
        }
    }

    override fun renderEffect(effect: CreateItemEffect) {
        when (effect) {
            is CreateItemEffect.ShowLoading -> {
                if (effect.isLoading) {
                    LoadingDialogManager.showLoadingDialog(requireContext())
                } else {
                    LoadingDialogManager.hideLoadingDialog()
                }
            }

            is CreateItemEffect.Success -> {
                (activity as NavigationManager).navigateByNavigationName(
                    "homeFragment",
                    "homeFragment"
                )
            }

            is CreateItemEffect.ShowError -> {
                showDialog(
                    message = getString(com.mismayilov.uikit.R.string.error_message),
                    positiveButtonText = getString(com.mismayilov.uikit.R.string.retry),
                    negativeButtonText = getString(com.mismayilov.uikit.R.string.back),
                    positiveButton = { setEvent(CreateItemEvent.RefreshCategories) },
                    negativeButton = {
                        findNavController().popBackStack()
                    }
                )
            }

            is CreateItemEffect.ShowToast -> {
                showToast(effect.message)
            }

            is CreateItemEffect.Back -> {
                findNavController().popBackStack()
            }
        }
    }

    override fun renderState(state: CreateItemState) {
        setSpinnerValues(state.category)
    }

    private fun setSpinnerValues(category: List<CategoriesModel>) {
        binding.customSpinner.setSpinnerValues(category.map { it.name }.toMutableList())
        if (category.isNotEmpty()) {
            checkDataIsUpdated(category)
        }
    }

    private fun checkDataIsUpdated(category: List<CategoriesModel>) {
        if (args.updateItem == " ") return
        val itemModel = Gson().fromJson(args.updateItem, ItemModel::class.java)
        binding.apply {
            txtName.setText(itemModel.name)
            txtAmount.setText(itemModel.amount.toString())
            txtNote.setText(itemModel.note)
            customSpinner.setSelection(category.indexOfFirst { it.id == itemModel.category.toString() })
            txtTop.text = getString(com.mismayilov.uikit.R.string.update_item)
        }
    }
}