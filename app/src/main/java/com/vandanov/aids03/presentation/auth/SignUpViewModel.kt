package com.vandanov.aids03.presentation.auth

import android.app.Application
import android.content.Context
import android.os.CountDownTimer
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.vandanov.aids03.data.auth.AuthRepositoryImpl
import com.vandanov.aids03.domain.auth.entity.RegistrationMethod
import com.vandanov.aids03.domain.auth.entity.SignUpItem
import com.vandanov.aids03.domain.auth.usecase.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {

//    private val repository = AuthRepositoryImpl()
//    private val signUpUseCase = SignUpUseCase(repository)

    private val _errorInputEmail = MutableLiveData<Boolean>()
    val errorInputEmail: LiveData<Boolean>
        get() = _errorInputEmail

    private val _errorInputPhoneNumber = MutableLiveData<Boolean>()
    val errorInputPhoneNumber: LiveData<Boolean>
        get() = _errorInputPhoneNumber

    private val _errorInputEpidN = MutableLiveData<Boolean>()
    val errorInputEpidN: LiveData<Boolean>
        get() = _errorInputEpidN

    private val _errorInputDateBirth = MutableLiveData<Boolean>()
    val errorInputDateBirth: LiveData<Boolean>
        get() = _errorInputDateBirth

    private val _errorInputPassword = MutableLiveData<Boolean>()
    val errorInputPassword: LiveData<Boolean>
        get() = _errorInputPassword

    private val _errorInputRepeatPassword = MutableLiveData<Boolean>()
    val errorInputRepeatPassword: LiveData<Boolean>
        get() = _errorInputRepeatPassword

    private val _otpCodeSend = MutableLiveData<Boolean>()
    val otpCodeSend: LiveData<Boolean>
        get() = _otpCodeSend

    private val _navigateLogin = MutableLiveData<Boolean>()
    val navigateLogin: LiveData<Boolean>
        get() = _navigateLogin

    private val _signUpInProgress = MutableLiveData<Boolean>()
    val signUpInProgress: LiveData<Boolean>
        get() = _signUpInProgress

    private fun validateInput(
        signUpData: SignUpItem
    ): Boolean {
        var result = true
        if (signUpData.email.isBlank()) {
            _errorInputEmail.value = true
            result = false
        }
        if (signUpData.phoneNumber.isBlank()) {
            _errorInputPhoneNumber.value = true
            result = false
        }
        if (signUpData.epidN.isBlank()) {
            _errorInputEpidN.value = true
            result = false
        }
        if (signUpData.dateBirth.isBlank()) {
            _errorInputDateBirth.value = true
            result = false
        }
        if (signUpData.password.isBlank()) {
            _errorInputPassword.value = true
            result = false
        }
        if (signUpData.repeatPassword.isBlank()) {
            _errorInputRepeatPassword.value = true
            result = false
        }

        if (!result)
            _signUpInProgress.value = false

        return result
    }

    fun resetErrorInputEmail() {
        _errorInputEmail.value = false
    }

    fun resetErrorInputPhoneNumber() {
        _errorInputPhoneNumber.value = false
    }

    fun resetErrorInputEpidN() {
        _errorInputEpidN.value = false
    }

    fun resetErrorInputDateBirth() {
        _errorInputDateBirth.value = false
    }

    fun resetErrorInputPassword() {
        _errorInputPassword.value = false
    }

    fun resetErrorInputRepeatPassword() {
        _errorInputRepeatPassword.value = false
    }

    fun signUp(signUpData: SignUpItem, registrationMethod: RegistrationMethod) {
        _signUpInProgress.value = true
        val fieldsValid = validateInput(signUpData)
        if (fieldsValid) {
            _otpCodeSend.value = false

            viewModelScope.launch {
                signUpUseCase.invoke(
                    signUpData,
                    registrationMethod,
                    {
                        Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                        startCountDownTimer()
                    },
                    {
                        if (it) _otpCodeSend.value = true
                        _signUpInProgress.value = false
                    },
                    {
                        if (it) _navigateLogin.value = true
                        _signUpInProgress.value = false
                    }
                )
            }
        }
    }

    private var timer: CountDownTimer? = null
    private fun startCountDownTimer() {
        timer?.cancel()
        timer = object : CountDownTimer(4000,1){
            override fun onTick(millisUntilFinished: Long) {
            }
            override fun onFinish() {
                _signUpInProgress.value = false
            }
        }.start()
    }
}