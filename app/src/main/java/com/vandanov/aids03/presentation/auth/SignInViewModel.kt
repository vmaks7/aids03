package com.vandanov.aids03.presentation.auth

import android.content.Context
import android.os.CountDownTimer
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vandanov.aids03.domain.auth.entity.RegistrationMethod
import com.vandanov.aids03.domain.auth.usecase.SignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val signInUseCase: SignInUseCase
) : ViewModel() {

//    private val repository = AuthRepositoryImpl()
//
//    private val signInEmailUseCase = SignInEmailUseCase(repository)

    private val _errorInputLogin = MutableLiveData<Boolean>()
    val errorInputLogin: LiveData<Boolean>
        get() = _errorInputLogin

    private val _errorInputPassword = MutableLiveData<Boolean>()
    val errorInputPassword: LiveData<Boolean>
        get() = _errorInputPassword


    private val _navigateTabs = MutableLiveData<Boolean>()
    val navigateTabs: LiveData<Boolean>
        get() = _navigateTabs

    private val _signInInProgress = MutableLiveData<Boolean>()
    val signInInProgress: LiveData<Boolean>
        get() = _signInInProgress

    private fun validateInput(
        login: String,
        password: String,
    ): Boolean {
        var result = true
        if (login.isBlank()) {
            _errorInputLogin.value = true
            result = false
        }
        if (password.isBlank()) {
            _errorInputPassword.value = true
            result = false
        }

        if (!result)
            _signInInProgress.value = false

        return result
    }

    fun resetErrorInputLogin() {
        _errorInputLogin.value = false
    }

    fun resetErrorInputPassword() {
        _errorInputPassword.value = false
    }

    fun signInEmail(email: String, password: String, registrationMethod: RegistrationMethod) {
        _signInInProgress.value = true
        val fieldsValid = validateInput(email, password)
        if (fieldsValid) {
            viewModelScope.launch {
                signInUseCase.invoke(
                    email,
                    password,
                    registrationMethod,
                    {
                        Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                        startCountDownTimer()
//                        _signInInProgress.value = false
                    },
                    {
                        if (it) _navigateTabs.value = true
                        _signInInProgress.value = false
                    }
                )
            }
        }
    }

    private var timer: CountDownTimer? = null
    fun startCountDownTimer() {
        timer?.cancel()
        timer = object : CountDownTimer(4000,1){
            override fun onTick(millisUntilFinished: Long) {
            }
            override fun onFinish() {
                _signInInProgress.value = false
            }
        }.start()
    }

}