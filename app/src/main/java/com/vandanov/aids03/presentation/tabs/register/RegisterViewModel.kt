package com.vandanov.aids03.presentation.tabs.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vandanov.aids03.data.RegisterRepositoryImpl
import com.vandanov.aids03.domain.register.DeleteRegisterUseCase
import com.vandanov.aids03.domain.register.EditRegisterUseCase
import com.vandanov.aids03.domain.register.GetListRegisterUseCase
import com.vandanov.aids03.domain.register.RegisterItem

class RegisterViewModel: ViewModel() {

    private val repository = RegisterRepositoryImpl

    private val getListRegisterUseCase = GetListRegisterUseCase(repository)
    private val deleteRegisterUseCase = DeleteRegisterUseCase(repository)
    private val editRegisterUseCase = EditRegisterUseCase(repository)

//    val register = MutableLiveData<List<RegisterItem>>()

    val register = getListRegisterUseCase.getListRegister()

//    fun getRegisterList() {
//        val list = getListRegisterUseCase.getListRegister()
//        // 2 способа register.value (из главного потока) и register.postvalue (из любого потока)
//        register.value = list
//    }

    fun deleteRegister(registerItem: RegisterItem) {
        deleteRegisterUseCase.deleteRegister(registerItem)
    }

    fun statusEnabledState(registerItem: RegisterItem) {
        val newItem = registerItem.copy(status = !registerItem.status)
        editRegisterUseCase.editRegister(newItem)
    }

}