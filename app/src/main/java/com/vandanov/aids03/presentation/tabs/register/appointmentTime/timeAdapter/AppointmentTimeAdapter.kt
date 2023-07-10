package com.vandanov.aids03.presentation.tabs.register.appointmentTime.timeAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.vandanov.aids03.R
import com.vandanov.aids03.domain.register.entity.appointmentDateTime.AppointmentDateTime
import com.vandanov.aids03.domain.register.entity.appointmentDateTime.AppointmentSpecialists

class AppointmentTimeAdapter() :
    ListAdapter<AppointmentDateTime, AppointmentTimeViewHolder>(
        AppointmentTimeItemDiffCallback()
    ) {

    var list = ArrayList<AppointmentDateTime>()
    var adapterPosition = -1

    init {
//        list.add(AppointmentTime(specialist, "","08:00"))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentTimeViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.fragment_register_time_item, parent, false)
        return AppointmentTimeViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: AppointmentTimeViewHolder,
        position: Int
    ) {
        val appointmentTimeItem = getItem(position)

//        holder.appointmentTime.text = appointmentTimeItem.time

//       val itemList = list[position]
//        holder.appointmentTime.text = itemList.appointmentTime
//
//        holder.itemView.setOnClickListener {
//            adapterPosition = position
//            notifyItemRangeChanged(0, list.size)
//        }
//
//        if (position == adapterPosition) {
//            holder.appointmentTime.setTextColor(
//                ContextCompat.getColor(
//                    holder.itemView.context,
//                    R.color.white
//                )
//            )
//            holder.appointmentTime.setBackgroundResource(R.color.purple_500)
//        } else {
//            holder.appointmentTime.setTextColor(
//                ContextCompat.getColor(
//                    holder.itemView.context,
//                    R.color.black
//                )
//            )
//            holder.appointmentTime.setBackgroundResource(R.color.blue)
//        }

    }
}