package com.vandanov.aids03.domain.register.usecase

import androidx.lifecycle.LiveData
import com.vandanov.aids03.domain.register.repository.RegisterRepository
import com.vandanov.aids03.domain.register.entity.RegisterItem

class GetListRegisterUseCase(private val repository: RegisterRepository) {

    operator fun invoke(): LiveData<List<RegisterItem>> {
        return repository.getListRegister()
    }
}