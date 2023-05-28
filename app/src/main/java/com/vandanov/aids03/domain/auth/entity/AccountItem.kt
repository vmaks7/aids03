package com.vandanov.aids03.domain.auth.entity

data class AccountItem(
    val login: String,
    val password: String,
    val token: String
)