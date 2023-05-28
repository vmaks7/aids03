package com.vandanov.aids03.domain.auth.repository

import com.vandanov.aids03.domain.auth.entity.SignUpItem

interface AuthRepository {

    suspend fun signInEmail(email: String, password: String)

    suspend fun signUp(signUpData: SignUpItem, message: (String) -> Unit)

    fun logout()

}