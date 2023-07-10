package com.vandanov.aids03.presentation

import androidx.lifecycle.ViewModel
import com.vandanov.aids03.domain.auth.usecase.InitFirebaseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val initFirebaseUseCase: InitFirebaseUseCase
): ViewModel() {

    fun initFirebase() : Boolean {
       return initFirebaseUseCase.invoke()
    }
}