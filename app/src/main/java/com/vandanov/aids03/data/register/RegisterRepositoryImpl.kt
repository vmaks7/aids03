package com.vandanov.aids03.data.register

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.vandanov.aids03.data.retrofit.RetrofitInstance
import com.vandanov.aids03.data.room.AppDatabase
import com.vandanov.aids03.domain.register.entity.RegisterItem
import com.vandanov.aids03.domain.register.entity.appointmentDateTime.Appointment
import com.vandanov.aids03.domain.register.repository.RegisterRepository

//до ROOM был object
//object RegisterRepositoryImpl : RegisterRepository {
class RegisterRepositoryImpl(
    application: Application
) : RegisterRepository {

    private val registerListDao = AppDatabase.getInstance(application).registerListDao()
    private val mapper = RegisterListMapper()

    override suspend fun getListSpecialists(): Appointment {

//        RetrofitInstance.api.getListSpecialistsAPI().enqueue(object : retrofit2.Callback<List<Specialists>> {
//            override fun onResponse(
//                call: Call<List<Specialists>>,
//                response: Response<List<Specialists>>
//            ) {
//                if (response.isSuccessful) {
//                    response.body()?.let {
//                        for (comment in it) {
//                            Log.d("MyLog", "onResponse: ${comment.fio}")
//                        }
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<List<Specialists>>, t: Throwable) {
//                Log.d("MyLog", "onFailure: ${t.message}")
//            }
//
//        })

        return RetrofitInstance.api.getListSpecialistsAPI()

    }

//    override suspend fun getAppointmentTime(): AppointmentDate {
//        return RetrofitInstance.api.getAppointmentTimeAPI()
//    }
//
//    override suspend fun getSizeListSpecialists(): Int {
//        return RetrofitInstance.api.getSizeListSpecialistsAPI()
//    }

    //private val registerListLiveData = MutableLiveData<List<RegisterItem>>()

    //до ROOM
//    //    private val registerList = mutableListOf<RegisterItem>()
//    // сортировка
//    // 1й вариант
////    private val registerList = sortedSetOf<RegisterItem>(object : Comparator<RegisterItem> {
////        override fun compare(o1: RegisterItem?, o2: RegisterItem?): Int {
////
////        }
////    })
//    //2й вариант через лямбда выражение
//    private val registerList = sortedSetOf<RegisterItem>({ o1, o2 -> o1.id.compareTo(o2.id)})
//
//    private var autoIncrementID = 0
//
//    init {
//        for (i in 0 until 10) {
//            val item = RegisterItem("$i", "", "", "", "")
//            addRegister(item)
//        }
//    }

    override suspend fun addRegister(registerItem: RegisterItem) {
//        if (registerItem.id == RegisterItem.DEFAULT_ID) {
//            registerItem.id = autoIncrementID++
//            // autoIncrementID++
//        }
//        registerList.add(registerItem)
//        updateList()


//        registerListDao.addRegisterItem(registerItem)
        registerListDao.addRegisterItem(mapper.mapEntityToDBModel(registerItem))
    }

    override suspend fun deleteRegister(registerItem: RegisterItem) {
//        registerList.remove(registerItem)
//        updateList()

        registerListDao.deleteRegisterItem(registerItem.id)
    }

    override suspend fun editRegister(registerItem: RegisterItem) {
//        val oldElement = getRegisterID(registerItem.id)
////        registerList.remove(oldElement)
//        deleteRegister(oldElement)
//        addRegister(registerItem)

        // тот же метод, что и для добавления
        registerListDao.addRegisterItem(mapper.mapEntityToDBModel(registerItem))
    }

    override fun getListRegister(): LiveData<List<RegisterItem>> {
////        //возращаем копию
////        return registerList.toList()
//        return registerListLiveData

        // не подходит
//        return registerListDao.getRegisterList()

        // MediatorLiveData позволяет перехватывать события из liveData
        return MediatorLiveData<List<RegisterItem>>().apply {
            addSource(registerListDao.getRegisterList()) {
                value = mapper.mapListDBModelToListEntity(it)
            }
        }

    }

    override suspend fun getRegisterID(register_ID: Int): RegisterItem {
//        return registerList.find {
//            it.id == register_ID
//        } ?: throw RuntimeException("Element with id $register_ID not found")

        val dbModel = registerListDao.getRegisterListID(register_ID)
        return mapper.mapDBModelToEntity(dbModel)
    }


}