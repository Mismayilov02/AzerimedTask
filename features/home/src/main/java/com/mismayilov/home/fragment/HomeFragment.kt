package com.mismayilov.home.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import com.mismayilov.common.generic.SwipeToActionCallback
import com.mismayilov.common.manager.LoadingDialogManager
import com.mismayilov.core.base.fragment.BaseFragment
import com.mismayilov.core.managers.NavigationManager
import com.mismayilov.home.adapter.ItemListAdapter
import com.mismayilov.home.databinding.FragmentHomeBinding
import com.mismayilov.home.flow.create_item.CreateItemEvent
import com.mismayilov.home.flow.home.HomeEffect
import com.mismayilov.home.flow.home.HomeEvent
import com.mismayilov.home.flow.home.HomeState
import com.mismayilov.home.viewmodel.HomeViewModel
import com.mismayilov.uikit.util.showDialog
import com.mismayilov.uikit.util.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment :
    BaseFragment<FragmentHomeBinding, HomeViewModel, HomeState, HomeEvent, HomeEffect>() {
    override fun getViewModelClass(): Class<HomeViewModel> = HomeViewModel::class.java
    override val inflateBinding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding =
        { inflater, container, attachToRoot ->
            FragmentHomeBinding.inflate(inflater, container, attachToRoot)
        }
    private val adapter = ItemListAdapter()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as NavigationManager).bottomNavigationVisibility(true)
        binding.floatingActionButton.setOnClickListener {
            (activity as NavigationManager).navigateByNavigationName("createItemFragment")
        }
        initAdapter()
        setupRecyclerView()
        initSearchView()

    }

    private fun initSearchView() {
        binding.customSearcView.setOnQueryTextListener {
            viewModel.setEvent(HomeEvent.SearchItems(it))
        }
    }

    private fun initAdapter() {
        val swipeHandler = SwipeToActionCallback(requireContext(), { id ->
            showDialog(
                message = getString(com.mismayilov.uikit.R.string.delete_item),
                positiveButton = {
                    viewModel.setEvent(HomeEvent.DeleteItem(id))
                },
                negativeButton = {
                    adapter.cancelDeleteItem(id)
                }
            )
        },
            {
                showDialog(
                    message = getString(com.mismayilov.uikit.R.string.edit_item),
                    positiveButton = {
                        setEvent(HomeEvent.EditItem(it))
                    },
                    negativeButton = {
                        adapter.cancelDeleteItem(it)
                    }
                )
            })
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
    }

    private fun setupRecyclerView() {
        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = GridLayoutManager(requireContext(), 1)
            recyclerView.adapter = adapter
        }
    }

    override fun renderEffect(effect: HomeEffect) {
        when (effect) {
            is HomeEffect.ShowToast -> showToast(effect.message)
            is HomeEffect.ShowLoading -> {
                if (effect.isLoading) {
                    LoadingDialogManager.showLoadingDialog(requireContext())
                } else {
                    LoadingDialogManager.hideLoadingDialog()
                }
            }
            is HomeEffect.DeleteItemError -> {
                adapter.cancelDeleteItem(effect.position)
            }
            is HomeEffect.ShowError ->{
                showDialog(
                    message = getString(com.mismayilov.uikit.R.string.error_message),
                    positiveButtonText = getString(com.mismayilov.uikit.R.string.retry),
                    negativeButtonText = getString(com.mismayilov.uikit.R.string.back),
                    positiveButton = {setEvent(HomeEvent.RefreshItems)},
                    negativeButton = {
                        findNavController().popBackStack()
                    }
                )
            }
            is HomeEffect.EditItem -> {
                val directions =
                    HomeFragmentDirections.actionHomeFragmentToCreateItemFragment(effect.item)
                (activity as NavigationManager).navigateByDirection(directions)
            }

            is HomeEffect.Back -> {
                (activity as NavigationManager).back()
            }
        }
    }

    override fun renderState(state: HomeState) {
        adapter.submitList(state.items)
    }
}