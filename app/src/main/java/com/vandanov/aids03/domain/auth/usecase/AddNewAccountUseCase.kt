package com.vandanov.aids03.domain.auth.usecase

import com.vandanov.aids03.domain.auth.entity.SignUpItem
import com.vandanov.aids03.domain.auth.repository.AuthRepository

class AddNewAccountUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(signUpData: SignUpItem, message: (String) -> Unit) {
        return repository.signUp(signUpData, message)
    }
}