package com.vandanov.aids03.presentation.tabs.register.appointmentTime

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vandanov.aids03.data.register.RegisterRepositoryImpl
import com.vandanov.aids03.domain.register.usecase.GetAppointmentTimeUseCase
import kotlinx.coroutines.launch

class RegisterDateViewModel(application: Application): AndroidViewModel(application) {

//    private val repository = RegisterRepositoryImpl(application)
//    private val getAppointmentTimeUseCase = GetAppointmentTimeUseCase(repository)
//
//    private val _appointmentTimeList = MutableLiveData<ArrayList<AppointmentTime>>()
//    val appointmentTimeList: MutableLiveData<ArrayList<AppointmentTime>>
//        get() = _appointmentTimeList
//
//    private var spec : Specialists = Specialists("000000033", "КО", "Нехланова Л.А.")
//
//    fun getAppointmentTime() {
//        viewModelScope.launch {
//            _appointmentTimeList.value = getAppointmentTimeUseCase.invoke(spec)
//        }
//    }

}