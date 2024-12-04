package com.rmldemo.guardsquare.uat.domain.model

data class Transaction(
    val id: String,
    val userId: String,
    val createdAt: String,
    val name: String,
    val imageUrl: String,
    val amount: Long,
    val type: TransactionType,
)
