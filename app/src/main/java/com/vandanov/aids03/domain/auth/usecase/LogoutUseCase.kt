package com.vandanov.aids03.domain.auth.usecase

import com.vandanov.aids03.domain.auth.repository.AuthRepository

class LogoutUseCase(private val repository: AuthRepository) {
    operator fun invoke() {
        return repository.logout()
    }
}