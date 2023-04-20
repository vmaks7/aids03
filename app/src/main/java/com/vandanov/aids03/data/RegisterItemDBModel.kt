package com.vandanov.aids03.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vandanov.aids03.domain.register.entity.StatusRegister

@Entity(tableName = "register_items")
data class RegisterItemDBModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val dateRegister: String,
    val timeRegister: String,
    val department: String,
    val doctor: String,
    val note: String,
    val status: StatusRegister = StatusRegister.REGISTRATION
)
