package com.vandanov.aids03.domain.auth.usecase

import com.vandanov.aids03.domain.auth.repository.AuthRepository
import javax.inject.Inject

class OtpSignUpUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(
        smsCode: String,
        epidN: String,
        dateBirth: String,
        message: (String) -> Unit,
        navigateTabs: (Boolean) -> Unit
    ) {
        return repository.otpSignUp(smsCode, epidN, dateBirth, message, navigateTabs)
    }
}