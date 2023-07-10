package com.vandanov.aids03.presentation.tabs.register.listSpecialists

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.vandanov.aids03.R
import com.vandanov.aids03.domain.register.entity.appointmentDateTime.AppointmentSpecialists

class SpecialistsAdapter :
    ListAdapter<AppointmentSpecialists, SpecialistsViewHolder>(SpecialistsDiffCallback()) {

    var onRegisterSpecialistsClickListener: ((AppointmentSpecialists) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecialistsViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View =
            inflater.inflate(R.layout.fragment_register_item_specialists, parent, false)
        return SpecialistsViewHolder(view)
    }

    override fun onBindViewHolder(holder: SpecialistsViewHolder, position: Int) {
        val itemList = getItem(position)

        holder.itemView.setOnClickListener {
            onRegisterSpecialistsClickListener?.invoke(itemList)
        }

        holder.speciality.text = itemList.department
        holder.fio.text = itemList.doctor
        holder.photo.setImageResource(R.drawable.ic_user_avatar)
    }

}