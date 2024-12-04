package com.rmldemo.guardsquare.uat.domain.model

data class User(
    val id: String,
    val name: String,
    val email: String,
    val photoUrl: String,
    val balance: Long,
)
