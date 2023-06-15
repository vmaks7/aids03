package com.vandanov.aids03.presentation.auth

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vandanov.aids03.data.auth.AuthRepositoryImpl
import com.vandanov.aids03.domain.auth.entity.RegistrationMethod
import com.vandanov.aids03.domain.auth.entity.SignUpItem
import com.vandanov.aids03.domain.auth.usecase.OtpSignUpUseCase
import com.vandanov.aids03.domain.auth.usecase.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OtpDialogViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val otpSignUpUseCase: OtpSignUpUseCase
) : ViewModel() {

//    private val repository = AuthRepositoryImpl()
//
//    private val otpSignUpUseCase = OtpSignUpUseCase(repository)

    private val _navigateTabs = MutableLiveData<Boolean>()
    val navigateTabs: LiveData<Boolean>
        get() = _navigateTabs

    fun otpSignUp(smsCode: String, epidN: String, dateBirth: String) {

        viewModelScope.launch {
            otpSignUpUseCase.invoke(
                smsCode,
                epidN,
                dateBirth,
                {
                    if (it == "Учетная запись успешно зарегистрована!") {
                        Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                        //навигация
                    } else {
                        Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                    }
                },
                {   Log.d("MyLog", "_navigateTabs $it")
                    if (it) _navigateTabs.value = true }
            )
        }
    }
}
