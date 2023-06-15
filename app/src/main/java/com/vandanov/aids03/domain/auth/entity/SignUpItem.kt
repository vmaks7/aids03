package com.vandanov.aids03.domain.auth.entity


data class SignUpItem(
    val phoneNumber: String,
    val email: String,
    val epidN: String,
    val dateBirth: String,
    val password: String,
    val repeatPassword: String
)