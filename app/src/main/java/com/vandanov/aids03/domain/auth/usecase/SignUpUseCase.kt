package com.vandanov.aids03.domain.auth.usecase

import com.vandanov.aids03.domain.auth.entity.RegistrationMethod
import com.vandanov.aids03.domain.auth.entity.SignUpItem
import com.vandanov.aids03.domain.auth.repository.AuthRepository
import javax.inject.Inject

class SignUpUseCase (private val repository: AuthRepository) {
    suspend operator fun invoke(
        signUpData: SignUpItem,
        registrationMethod: RegistrationMethod,
        message: (String) -> Unit,
        otpCodeSend: (Boolean) -> Unit,
        navigate: (Boolean) -> Unit
    ) {
        return repository.signUp(
            signUpData,
            registrationMethod,
            message,
            otpCodeSend,
            navigate)
    }
}