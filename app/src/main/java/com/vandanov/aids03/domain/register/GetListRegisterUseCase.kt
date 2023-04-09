package com.vandanov.aids03.domain.register

import androidx.lifecycle.LiveData

class GetListRegisterUseCase(private val repository: RegisterRepository) {

    fun getListRegister(): LiveData<List<RegisterItem>> {
        return repository.getListRegister()
    }
}