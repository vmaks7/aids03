package com.vandanov.aids03.domain.auth.repository

import com.vandanov.aids03.domain.auth.entity.RegistrationMethod
import com.vandanov.aids03.domain.auth.entity.SignUpItem

interface AuthRepository {

    suspend fun signIn(
        email: String,
        password: String,
        registrationMethod: RegistrationMethod,
        message: (String) -> Unit,
        navigate: (Boolean) -> Unit
    )

    suspend fun signUp(
        signUpData: SignUpItem,
        registrationMethod: RegistrationMethod,
        message: (String) -> Unit,
        otpCodeSend: (Boolean) -> Unit,
        navigate: (Boolean) -> Unit
    )

    suspend fun otpSignUp(
        smsCode: String,
        epidN: String,
        dateBirth: String,
        message: (String) -> Unit,
        navigateTabs: (Boolean) -> Unit
    )

    fun logout()

}