package com.vandanov.aids03.data

import com.vandanov.aids03.data.room.RegisterItemDBModel
import com.vandanov.aids03.domain.register.entity.RegisterItem

class RegisterListMapper {

    fun mapEntityToDBModel(registerItem: RegisterItem): RegisterItemDBModel {
        return RegisterItemDBModel(
            id = registerItem.id,
            dateRegister = registerItem.dateRegister,
            timeRegister = registerItem.timeRegister,
            department = registerItem.department,
            doctor = registerItem.doctor,
            note = registerItem.note,
            status = registerItem.status
        )
    }

    fun mapDBModelToEntity(registerItemDBModel: RegisterItemDBModel): RegisterItem {
        return RegisterItem(
            id = registerItemDBModel.id,
            dateRegister = registerItemDBModel.dateRegister,
            timeRegister = registerItemDBModel.timeRegister,
            department = registerItemDBModel.department,
            doctor = registerItemDBModel.doctor,
            note = registerItemDBModel.note,
            status = registerItemDBModel.status
        )
    }

    fun mapListDBModelToListEntity(list: List<RegisterItemDBModel>) = list.map {
        mapDBModelToEntity(it)
    }

}