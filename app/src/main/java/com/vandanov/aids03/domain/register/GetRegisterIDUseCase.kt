package com.vandanov.aids03.domain.register

class GetRegisterIDUseCase(private val repository: RegisterRepository) {

    fun getRegister(register_ID: Int): RegisterItem {
        return repository.getRegisterID(register_ID)
    }
}