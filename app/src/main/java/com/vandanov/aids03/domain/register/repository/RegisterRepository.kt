package com.vandanov.aids03.domain.register.repository

import androidx.lifecycle.LiveData
import com.vandanov.aids03.domain.register.entity.RegisterItem

interface RegisterRepository {

    suspend fun addRegister(registerItem: RegisterItem)

    suspend fun deleteRegister(registerItem: RegisterItem)

    suspend fun editRegister (registerItem: RegisterItem)

    suspend fun getRegisterID(register_ID: Int): RegisterItem

    fun getListRegister(): LiveData<List<RegisterItem>>

}