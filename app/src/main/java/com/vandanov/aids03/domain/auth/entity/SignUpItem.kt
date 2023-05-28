package com.vandanov.aids03.domain.auth.entity

data class SignUpItem(
    val login: String,
    val epidN: String,
    val dateBirth: String,
    val password: String,
    val repeatPassword: String
)