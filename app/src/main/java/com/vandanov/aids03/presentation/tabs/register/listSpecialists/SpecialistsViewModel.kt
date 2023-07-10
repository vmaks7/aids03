package com.vandanov.aids03.presentation.tabs.register.listSpecialists

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vandanov.aids03.data.register.RegisterRepositoryImpl
import com.vandanov.aids03.domain.register.entity.appointmentDateTime.Appointment
import com.vandanov.aids03.domain.register.entity.appointmentDateTime.AppointmentSpecialists
import com.vandanov.aids03.domain.register.usecase.GetListSpecialistsUseCase
import kotlinx.coroutines.launch

class SpecialistsViewModel(application: Application): AndroidViewModel(application) {

    private val repository = RegisterRepositoryImpl(application)
    private val getListSpecialistsUseCase = GetListSpecialistsUseCase(repository)

    private val _listSpecialists = MutableLiveData<AppointmentSpecialists>()
    val listSpecialists: LiveData<AppointmentSpecialists>
        get() = _listSpecialists

    fun getListSpecialists() {
//        viewModelScope.launch {
//            _listSpecialists.value = getListSpecialistsUseCase.invoke()
//            //if (list.size == 0) Toast.makeText(context, "Нет свободных окошек для записи", Toast.LENGTH_LONG).show()
//        }
    }

}