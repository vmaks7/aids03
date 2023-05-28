package com.vandanov.aids03.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vandanov.aids03.data.auth.AuthRepositoryImpl
import com.vandanov.aids03.domain.auth.usecase.SignInEmailUseCase
import kotlinx.coroutines.launch

class SignInViewModel: ViewModel() {

    private val repository = AuthRepositoryImpl()

    private val signInEmailUseCase = SignInEmailUseCase(repository)

    fun signInEmail(email: String, password: String) {
        viewModelScope.launch {
            signInEmailUseCase.invoke(email, password)
        }
    }

}