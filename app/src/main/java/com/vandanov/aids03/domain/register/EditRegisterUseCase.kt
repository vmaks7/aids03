package com.vandanov.aids03.domain.register

class EditRegisterUseCase(private val repository: RegisterRepository) {

    fun editRegister (registerItem: RegisterItem) {
        repository.editRegister(registerItem)
    }
}