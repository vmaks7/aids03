package com.vandanov.aids03.domain.register

class DeleteRegisterUseCase(private val repository: RegisterRepository) {

    fun deleteRegister(registerItem: RegisterItem) {
        repository.deleteRegister(registerItem)
    }
}