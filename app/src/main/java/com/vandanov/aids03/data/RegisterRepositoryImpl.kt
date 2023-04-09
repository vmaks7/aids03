package com.vandanov.aids03.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vandanov.aids03.domain.register.RegisterItem
import com.vandanov.aids03.domain.register.RegisterRepository

object RegisterRepositoryImpl : RegisterRepository {

    private val registerListLiveData = MutableLiveData<List<RegisterItem>>()

    //    private val registerList = mutableListOf<RegisterItem>()
    // сортировка
    // 1й вариант
//    private val registerList = sortedSetOf<RegisterItem>(object : Comparator<RegisterItem> {
//        override fun compare(o1: RegisterItem?, o2: RegisterItem?): Int {
//
//        }
//    })
    //2й вариант через лямбда выражение
    private val registerList = sortedSetOf<RegisterItem>({ o1, o2 -> o1.id.compareTo(o2.id)})

    private var autoIncrementID = 0

    init {
        for (i in 0 until 10) {
            val item = RegisterItem("$i", "", "", "", "", false)
            addRegister(item)
        }
    }

    override fun addRegister(registerItem: RegisterItem) {
        if (registerItem.id == RegisterItem.DEFAULT_ID) {
            registerItem.id = autoIncrementID++
            // autoIncrementID++
        }
        registerList.add(registerItem)
        updateList()
    }

    override fun deleteRegister(registerItem: RegisterItem) {
        registerList.remove(registerItem)
        updateList()
    }

    override fun editRegister(registerItem: RegisterItem) {
        val oldElement = getRegisterID(registerItem.id)
//        registerList.remove(oldElement)
        deleteRegister(oldElement)
        addRegister(registerItem)
    }

    override fun getListRegister(): LiveData<List<RegisterItem>> {
//        //возращаем копию
//        return registerList.toList()
        return registerListLiveData
    }

    override fun getRegisterID(register_ID: Int): RegisterItem {
        return registerList.find {
            it.id == register_ID
        } ?: throw java.lang.RuntimeException("Element with id $register_ID not found")
    }

    private fun updateList() {
        registerListLiveData.value = registerList.toList()
    }

}