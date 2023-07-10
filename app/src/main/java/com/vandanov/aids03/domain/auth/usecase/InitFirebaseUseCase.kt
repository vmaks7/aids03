package com.vandanov.aids03.domain.auth.usecase

import com.vandanov.aids03.domain.auth.repository.AuthRepository

class InitFirebaseUseCase(private val repository: AuthRepository) {
    operator fun invoke(): Boolean {
       return repository.initFirebase()
    }
}