package com.vandanov.aids03.domain.register.entity

data class RegisterItem(
    val dateRegister: String,
    val timeRegister: String,
    val department: String,
    val doctor: String,
    val note: String,
    val status: StatusRegister = StatusRegister.REGISTRATION,
    var id: Int = DEFAULT_ID
) {
    companion object {
        const val DEFAULT_ID = 0
    }
}