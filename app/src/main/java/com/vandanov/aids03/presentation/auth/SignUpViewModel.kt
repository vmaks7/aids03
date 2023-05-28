package com.vandanov.aids03.presentation.auth

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vandanov.aids03.data.auth.AuthRepositoryImpl
import com.vandanov.aids03.domain.auth.entity.SignUpItem
import com.vandanov.aids03.domain.auth.usecase.AddNewAccountUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class SignUpViewModel(private val application: Application): AndroidViewModel(application) {

    private val repository = AuthRepositoryImpl()
    private val addNewAccountUseCase = AddNewAccountUseCase(repository)

    fun signUp(signUpData: SignUpItem) {
        viewModelScope.launch {
            addNewAccountUseCase.invoke(signUpData, {Toast.makeText(application, it, Toast.LENGTH_LONG).show()} )
        }
    }

}