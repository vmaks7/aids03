package com.vandanov.aids03.domain.auth.usecase

import com.vandanov.aids03.domain.auth.repository.AuthRepository

class SignInEmailUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String) {
        repository.signInEmail(email, password)
    }
}