package com.mismayilov.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mismayilov.core.generics.MyDiffUtil
import com.mismayilov.domain.entities.remote.ResponseItemsModel
import com.mismayilov.uikit.databinding.ListItemDesignBinding

class ItemListAdapter:
    ListAdapter<ResponseItemsModel, ItemListAdapter.AccountViewHolder>(MyDiffUtil<ResponseItemsModel>(
        itemsTheSame = { oldItem, newItem -> oldItem == newItem },
        contentsTheSame = { oldItem, newItem -> oldItem == newItem }
    )) {
    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder {
        val binding =
            ListItemDesignBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AccountViewHolder(binding)
    }

    inner class AccountViewHolder(private val binding: ListItemDesignBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: ResponseItemsModel) {
            binding.apply {
                val context = itemView.context
                txtName.text = "${context.getString(com.mismayilov.uikit.R.string.name_txt)} ${item.name}"
                txtNote.text = "${context.getString(com.mismayilov.uikit.R.string.note_txt)} ${item.note}"
                txtAmount.text = "${item.amount} ₼"
                txtDate.text = "${context.getString(com.mismayilov.uikit.R.string.date_txt)} ${item.createdAt}"
            }
        }
    }

    fun cancelDeleteItem(position: Int) {
        notifyItemChanged(position)
    }
}