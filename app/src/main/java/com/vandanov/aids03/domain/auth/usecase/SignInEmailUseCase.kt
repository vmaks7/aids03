package com.vandanov.aids03.domain.auth.usecase

import com.vandanov.aids03.domain.auth.entity.RegistrationMethod
import com.vandanov.aids03.domain.auth.repository.AuthRepository
import javax.inject.Inject

class SignInUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(
        email: String,
        password: String,
        registrationMethod: RegistrationMethod,
        message: (String) -> Unit,
        navigate: (Boolean) -> Unit
    ) {
        repository.signIn(email, password, registrationMethod, message, navigate)
    }
}