package com.vandanov.aids03.domain.auth.usecase

import com.vandanov.aids03.domain.auth.entity.RegistrationMethod
import com.vandanov.aids03.domain.auth.repository.AuthRepository

class SignInUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(
        login: String,
        password: String,
        registrationMethod: RegistrationMethod,
        message: (String) -> Unit,
        navigate: (Boolean) -> Unit
    ) {
        return repository.signIn(login, password, registrationMethod, message, navigate)
    }
}