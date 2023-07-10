package com.vandanov.aids03.presentation.tabs.register.appointmentTime.dateAdapter

import java.text.SimpleDateFormat
import java.util.*

data class CalendarDateModel (var data: Date, var isSelected: Boolean = true) {

    //День недели
    val calendarDay: String
        get() {
            val DayNumber = SimpleDateFormat("EE", Locale.getDefault()).format(data)
            return when (DayNumber) {
                "Mon" -> {
                    "понедельник"
                }
                "Tue" -> {
                    "вторник"
                }
                "Wed" -> {
                    "среда"
                }
                "Thu" -> {
                    "четверг"
                }
                "Fri" -> {
                    "пятница"
                }
                "Sat" -> {
                    "суббота"
                }
                "Sun" -> {
                    "воскресенье"
                }
                else -> {""}
            }
        }

    //Месяц
    val calendarMonth: String
        get() {
            val monthNumber = SimpleDateFormat("MM", Locale.getDefault()).format(data)
            return when (monthNumber) {
                "01" -> {
                    "январь"
                }
                "02" -> {
                    "февраль"
                }
                "03" -> {
                    "март"
                }
                "04" -> {
                    "апрель"
                }
                "05" -> {
                    "май"
                }
                "06" -> {
                    "июнь"
                }
                "07" -> {
                    "июль"
                }
                "08" -> {
                    "август"
                }
                "09" -> {
                    "сентябрь"
                }
                "10" -> {
                    "октябрь"
                }
                "11" -> {
                    "ноябрь"
                }
                "12" -> {
                    "декабрь"
                }
                else -> {""}
            }

        }


//    val calendarYear: String
//        get() = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(data)

    //Число месяца
    val calendarDate: String
        get() {
            val cal = Calendar.getInstance()
            cal.time = data
            return cal[Calendar.DAY_OF_MONTH].toString()
        }
}