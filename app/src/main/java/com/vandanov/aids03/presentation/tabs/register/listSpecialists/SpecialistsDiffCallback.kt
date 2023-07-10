package com.vandanov.aids03.presentation.tabs.register.listSpecialists

import androidx.recyclerview.widget.DiffUtil
import com.vandanov.aids03.domain.register.entity.appointmentDateTime.AppointmentSpecialists

class SpecialistsDiffCallback : DiffUtil.ItemCallback<AppointmentSpecialists>() {

    override fun areItemsTheSame(
        oldItem: AppointmentSpecialists,
        newItem: AppointmentSpecialists
    ): Boolean {
        return oldItem.doctor == newItem.doctor && oldItem.department == newItem.department
    }

    override fun areContentsTheSame(
        oldItem: AppointmentSpecialists,
        newItem: AppointmentSpecialists
    ): Boolean {
        return oldItem == newItem
    }

}