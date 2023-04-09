package com.vandanov.aids03.domain.register

import androidx.lifecycle.LiveData

interface RegisterRepository {

    fun addRegister(registerItem: RegisterItem)

    fun deleteRegister(registerItem: RegisterItem)

    fun editRegister (registerItem: RegisterItem)

    fun getListRegister(): LiveData<List<RegisterItem>>

    fun getRegisterID(register_ID: Int): RegisterItem
}