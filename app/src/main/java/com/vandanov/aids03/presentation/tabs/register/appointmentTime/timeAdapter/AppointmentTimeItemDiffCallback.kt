package com.vandanov.aids03.presentation.tabs.register.appointmentTime.timeAdapter

import androidx.recyclerview.widget.DiffUtil
import com.vandanov.aids03.domain.register.entity.appointmentDateTime.AppointmentDateTime

class AppointmentTimeItemDiffCallback: DiffUtil.ItemCallback<AppointmentDateTime>() {

    override fun areItemsTheSame(
        oldItem: AppointmentDateTime,
        newItem: AppointmentDateTime
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: AppointmentDateTime,
        newItem: AppointmentDateTime
    ): Boolean {
        return oldItem == newItem
    }
}