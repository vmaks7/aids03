package com.vandanov.aids03.presentation.tabs.settings

import androidx.lifecycle.ViewModel
import com.vandanov.aids03.domain.auth.usecase.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsFragmentViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase
): ViewModel() {

    fun logout() {
        logoutUseCase.invoke()
    }

}