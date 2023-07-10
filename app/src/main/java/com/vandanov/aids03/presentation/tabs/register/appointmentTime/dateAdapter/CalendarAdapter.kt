package com.vandanov.aids03.presentation.tabs.register.appointmentTime.dateAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.vandanov.aids03.R

class CalendarAdapter() :
    RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>() {

    private var list = ArrayList<CalendarDateModel>()
    var adapterPosition = -1

    class CalendarViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val calendarDay = itemView.findViewById<TextView>(R.id.tv_calendar_day)
        val calendarDate = itemView.findViewById<TextView>(R.id.tv_calendar_date)
        val calendarMonth = itemView.findViewById<TextView>(R.id.tv_calendar_month)
        val linear = itemView.findViewById<LinearLayout>(R.id.linear_calendar)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CalendarViewHolder {
        val inflater : LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.date_layout,parent,false)
        return CalendarViewHolder(view)
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {

        val itemList = list[position]
        holder.calendarDay.text = itemList.calendarDay
        holder.calendarDate.text = itemList.calendarDate
        holder.calendarMonth.text = itemList.calendarMonth

        holder.itemView.setOnClickListener {
            adapterPosition = position
            notifyItemRangeChanged(0, list.size)
        }

        if (position == adapterPosition){
            holder.calendarDay.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.white))
            holder.calendarDate.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.white))
            holder.calendarMonth.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.white))
            //holder.linear.background = holder.itemView.context.getDrawable(R.drawable.rectangle_fill)
            holder.linear.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.purple_500))
        } else {
            holder.calendarDay.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.black))
            holder.calendarDate.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.black))
            holder.calendarMonth.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.black))
           // holder.linear.background = holder.itemView.context.getDrawable(R.drawable.rectangle_outline)
            holder.linear.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.blue))
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setData(calendarList: ArrayList<CalendarDateModel>) {
        list.clear()
        list.addAll(calendarList)
        notifyDataSetChanged()
    }

}