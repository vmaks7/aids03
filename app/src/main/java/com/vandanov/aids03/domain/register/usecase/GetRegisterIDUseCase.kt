package com.vandanov.aids03.domain.register.usecase

import com.vandanov.aids03.domain.register.repository.RegisterRepository
import com.vandanov.aids03.domain.register.entity.RegisterItem

class GetRegisterIDUseCase(private val repository: RegisterRepository) {

    suspend operator fun invoke(register_ID: Int): RegisterItem {
        return repository.getRegisterID(register_ID)
    }
}