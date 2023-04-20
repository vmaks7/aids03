package com.vandanov.aids03.domain.register.usecase

import com.vandanov.aids03.domain.register.repository.RegisterRepository
import com.vandanov.aids03.domain.register.entity.RegisterItem

class DeleteRegisterUseCase(private val repository: RegisterRepository) {

    suspend operator fun invoke(registerItem: RegisterItem) {
        repository.deleteRegister(registerItem)
    }
}