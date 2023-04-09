package com.vandanov.aids03.presentation.register

import androidx.recyclerview.widget.DiffUtil
import com.vandanov.aids03.domain.register.RegisterItem

class RegisterItemDiffCallback: DiffUtil.ItemCallback<RegisterItem>() {

    override fun areItemsTheSame(oldItem: RegisterItem, newItem: RegisterItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: RegisterItem, newItem: RegisterItem): Boolean {
        return oldItem == newItem
    }
}