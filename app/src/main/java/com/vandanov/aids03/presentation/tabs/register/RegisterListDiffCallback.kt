package com.vandanov.aids03.presentation.tabs.register

import androidx.recyclerview.widget.DiffUtil
import com.vandanov.aids03.domain.register.RegisterItem

class RegisterListDiffCallback(
    private val oldList: List<RegisterItem>,
    private val newList: List<RegisterItem>
): DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = oldList[newItemPosition]
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = oldList[newItemPosition]
        return oldItem == newItem
    }
}