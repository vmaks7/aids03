package com.vandanov.aids03.domain.register.entity.appointmentDateTime

data class AppointmentSpecialists(
    val doctor: String,
    val department: String,
    val dateList: List<AppointmentDateTime>
)
