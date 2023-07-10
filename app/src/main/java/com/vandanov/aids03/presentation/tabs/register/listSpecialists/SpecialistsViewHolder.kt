package com.vandanov.aids03.presentation.tabs.register.listSpecialists

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vandanov.aids03.R

class SpecialistsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val speciality = itemView.findViewById<TextView>(R.id.tvSpeciality)
    val fio = itemView.findViewById<TextView>(R.id.tvFIO)
    val photo = itemView.findViewById<ImageView>(R.id.photoImageView)
}