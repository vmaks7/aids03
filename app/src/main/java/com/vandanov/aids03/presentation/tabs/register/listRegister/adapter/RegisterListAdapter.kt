package com.vandanov.aids03.presentation.tabs.register.listRegister.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.vandanov.aids03.R
import com.vandanov.aids03.domain.register.entity.RegisterItem

class RegisterListAdapter: ListAdapter<RegisterItem, RegisterItemViewHolder>(
    RegisterItemDiffCallback()
) {

    var onRegisterItemClickListener: ((RegisterItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegisterItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_register, parent, false)
        return RegisterItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: RegisterItemViewHolder, position: Int) {
        val registerItem = getItem(position)
        holder.itemView.setOnClickListener {
            onRegisterItemClickListener?.invoke(registerItem)
        }
        holder.tvDateRegister.text = registerItem.dateRegister
        holder.tvTimeRegister.text = registerItem.timeRegister
        holder.tvDepartment.text = registerItem.department
        holder.tvDoctor.text = registerItem.doctor
    }
}