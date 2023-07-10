package com.vandanov.aids03.domain.register.repository

import androidx.lifecycle.LiveData
import com.vandanov.aids03.domain.register.entity.RegisterItem
import com.vandanov.aids03.domain.register.entity.appointmentDateTime.Appointment

interface RegisterRepository {

    suspend fun getListSpecialists(): Appointment

//    suspend fun getAppointmentTime(specialists: Specialists): ArrayList<AppointmentTime>
//
//    suspend fun getSizeListSpecialists(): Int

    suspend fun addRegister(registerItem: RegisterItem)

    suspend fun deleteRegister(registerItem: RegisterItem)

    suspend fun editRegister(registerItem: RegisterItem)

    suspend fun getRegisterID(register_ID: Int): RegisterItem

    fun getListRegister(): LiveData<List<RegisterItem>>

}