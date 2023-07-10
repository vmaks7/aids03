package com.vandanov.aids03.presentation.tabs.register.appointmentTime.timeAdapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vandanov.aids03.R

class AppointmentTimeViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val appointmentTime = itemView.findViewById<TextView>(R.id.tvAppointmentTime)
}