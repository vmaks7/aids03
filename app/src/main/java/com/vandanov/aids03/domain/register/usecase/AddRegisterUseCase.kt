package com.vandanov.aids03.domain.register.usecase

import com.vandanov.aids03.domain.register.repository.RegisterRepository
import com.vandanov.aids03.domain.register.entity.RegisterItem

class AddRegisterUseCase(private val repository: RegisterRepository) {

//    fun addRegister(registerItem: RegisterItem) {
//        repository.addRegister(registerItem)
//    }

    suspend operator fun invoke(registerItem: RegisterItem) {
        repository.addRegister(registerItem)
    }

}