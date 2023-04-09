package com.vandanov.aids03.domain.register

class AddRegisterUseCase(private val repository: RegisterRepository) {

    fun addRegister(registerItem: RegisterItem) {
        repository.addRegister(registerItem)
    }
}