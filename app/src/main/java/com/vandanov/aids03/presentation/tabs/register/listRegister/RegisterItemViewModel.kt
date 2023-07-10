package com.vandanov.aids03.presentation.tabs.register.listRegister

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vandanov.aids03.data.register.RegisterRepositoryImpl
import com.vandanov.aids03.domain.register.entity.RegisterItem
import com.vandanov.aids03.domain.register.usecase.AddRegisterUseCase
import com.vandanov.aids03.domain.register.usecase.EditRegisterUseCase
import com.vandanov.aids03.domain.register.usecase.GetRegisterIDUseCase
import kotlinx.coroutines.launch

class RegisterItemViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = RegisterRepositoryImpl(application)

    private val getRegisterIDUseCase = GetRegisterIDUseCase(repository)
    private val addRegisterUseCase = AddRegisterUseCase(repository)
    private val editRegisterUseCase = EditRegisterUseCase(repository)

        //вместо него используем viewModelScope
    //private val scope = CoroutineScope(Dispatchers.IO)

    private val _errorInputDepartment = MutableLiveData<Boolean>()
    val errorInputDepartment: LiveData<Boolean>
        get() = _errorInputDepartment

    private val _errorInputDoctor = MutableLiveData<Boolean>()
    val errorInputDoctor: LiveData<Boolean>
        get() = _errorInputDoctor

    private val _errorInputDateRegister = MutableLiveData<Boolean>()
    val errorInputDateRegister: LiveData<Boolean>
        get() = _errorInputDateRegister

    private val _errorInputTimeRegister = MutableLiveData<Boolean>()
    val errorInputTimeRegister: LiveData<Boolean>
        get() = _errorInputTimeRegister


    private val _registerItem = MutableLiveData<RegisterItem>()
    val registerItem: LiveData<RegisterItem>
        get() = _registerItem

//    лайф-дата для закрытия экрана
    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen


    fun getRegisterID(registerID: Int) {
        viewModelScope.launch {
            val item = getRegisterIDUseCase.invoke(registerID)
            _registerItem.value = item
        }
    }

    fun addRegister(
        inputDepartment: String?,
        inputDoctor: String?,
        inputDateRegister: String?,
        inputTimeRegister: String?,
        inputNote: String?
    ) {
        val department = parseInputName(inputDepartment)
        val doctor = parseInputName(inputDoctor)
        val dateRegister = parseInputName(inputDateRegister)
        val timeRegister = parseInputName(inputTimeRegister)
        val note = parseInputName(inputNote)

        val fieldsValid = validateInput(department, doctor, dateRegister, timeRegister)

        if (fieldsValid) {
            viewModelScope.launch {
                val registerItem =
                    RegisterItem(dateRegister, timeRegister, department, doctor, note)
                addRegisterUseCase.invoke(registerItem)
                closeScreen()
            }
        }
    }

    fun editRegister(
        inputDepartment: String?,
        inputDoctor: String?,
        inputDateRegister: String?,
        inputTimeRegister: String?,
        inputNote: String?
    ) {
        val department = parseInputName(inputDepartment)
        val doctor = parseInputName(inputDoctor)
        val dateRegister = parseInputName(inputDateRegister)
        val timeRegister = parseInputName(inputTimeRegister)
        val note = parseInputName(inputNote)

        val fieldsValid = validateInput(department, doctor, dateRegister, timeRegister)

        if (fieldsValid) {
//            val registerItem =
//                RegisterItem(dateRegister, timeRegister, department, doctor, note, false)
//            val registerItem = _registerItem.value

            //вытаскиваем объект _registerItem из лайф-даты
            //может вернуться null-объект, поэтому редактируем объект с помощью оператора ".let"
            _registerItem.value?.let {
                viewModelScope.launch {
                    val item = it.copy(
                        dateRegister = dateRegister,
                        timeRegister = timeRegister,
                        department = department,
                        doctor = doctor,
                        note = note
                    )
                    editRegisterUseCase.invoke(item)
                    closeScreen()
                }
            }
        }
    }

    //убираем лишние пробелы
    private fun parseInputName(inputName: String?): String {
        return inputName?.trim() ?: ""
    }

//    // преобразование строки в число
//    private fun parseCount(inputCount: String?): Int {
//        return try {
//            inputCount?.trim()?.toInt() ?: 0
//        } catch (e: Exception) {
//            0
//        }
//    }

    private fun validateInput(
        department: String,
        doctor: String,
        dateRegister: String,
        timeRegister: String
    ): Boolean {
        var result = true
        if (department.isBlank()) {
            _errorInputDepartment.value = true
            result = false
        }
        if (doctor.isBlank()) {
            _errorInputDoctor.value = true
            result = false
        }
        if (dateRegister.isBlank()) {
            _errorInputDateRegister.value = true
            result = false
        }
        if (timeRegister.isBlank()) {
            _errorInputTimeRegister.value = true
            result = false
        }
        return result
    }

    fun resetErrorInputDepartment() {
        _errorInputDepartment.value = false
    }

    fun resetErrorInputDoctor() {
        _errorInputDoctor.value = false
    }

    fun resetErrorInputDateRegister() {
        _errorInputDateRegister.value = false
    }

    fun resetErrorInputTimeRegister() {
        _errorInputTimeRegister.value = false
    }

    private fun closeScreen() {
        _shouldCloseScreen.value = Unit
    }

    // не нужно при использовании viewModelScope
//    override fun onCleared() {
//        super.onCleared()
//        scope.cancel()
//    }

}