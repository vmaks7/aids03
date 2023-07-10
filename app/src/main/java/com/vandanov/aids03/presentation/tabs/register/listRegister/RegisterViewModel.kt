package com.vandanov.aids03.presentation.tabs.register.listRegister

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vandanov.aids03.data.register.RegisterRepositoryImpl
import com.vandanov.aids03.domain.register.entity.RegisterItem
import com.vandanov.aids03.domain.register.entity.appointmentDateTime.Appointment
import com.vandanov.aids03.domain.register.usecase.DeleteRegisterUseCase
import com.vandanov.aids03.domain.register.usecase.GetListRegisterUseCase
import com.vandanov.aids03.domain.register.usecase.GetListSpecialistsUseCase
import com.vandanov.aids03.domain.register.usecase.GetSizeListSpecialistsUseCase
import kotlinx.coroutines.launch

class RegisterViewModel(application: Application): AndroidViewModel(application) {

    private val repository = RegisterRepositoryImpl(application)

    private val getListSpecialistsUseCase = GetListSpecialistsUseCase(repository)
    private val getListRegisterUseCase = GetListRegisterUseCase(repository)
    private val deleteRegisterUseCase = DeleteRegisterUseCase(repository)
//    private val editRegisterUseCase = EditRegisterUseCase(repository)

    //вместо него используем viewModelScope
//    private val scope = CoroutineScope(Dispatchers.IO)

    //старый способ
//    val register = MutableLiveData<List<RegisterItem>>()

    val register = getListRegisterUseCase.invoke()

        //старый способ
//    fun getRegisterList() {
//        val list = getListRegisterUseCase.getListRegister()
//        // 2 способа register.value (из главного потока) и register.postvalue (из любого потока)
//        register.value = list
//    }

    private val _listSpecialists = MutableLiveData<Appointment>()
    val listSpecialists: MutableLiveData<Appointment>
        get() = _listSpecialists

    fun getListSpecialists() {
        viewModelScope.launch {
            _listSpecialists.value = getListSpecialistsUseCase.invoke()
        }
    }

    fun deleteRegister(registerItem: RegisterItem) {
//        deleteRegisterUseCase.invoke(registerItem)

        viewModelScope.launch {
            deleteRegisterUseCase.invoke(registerItem)
        }
    }

//    fun statusEnabledState(registerItem: RegisterItem) {
////        val newItem = registerItem.copy(status = !registerItem.status)
////        editRegisterUseCase.invoke(newItem)
//    }

    // не нужно при использовании viewModelScope
//    override fun onCleared() {
//        super.onCleared()
//        scope.cancel()
//    }

}