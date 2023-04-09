package com.vandanov.aids03.presentation.register

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vandanov.aids03.R

class RegisterItemViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val tvDateRegister = view.findViewById<TextView>(R.id.tvDateRegister)
    val tvTimeRegister = view.findViewById<TextView>(R.id.tvTimeRegister)
    val tvDepartment = view.findViewById<TextView>(R.id.tvDepartment)
    val tvDoctor = view.findViewById<TextView>(R.id.tvDoctor)
}