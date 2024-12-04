package com.rmldemo.guardsquare.uat.presentation.history

import com.rmldemo.guardsquare.uat.domain.model.Transaction

data class HistoryState(
    val isLoading: Boolean = false,
    val histories: List<Transaction> = listOf(),
    val message: String = ""
)
