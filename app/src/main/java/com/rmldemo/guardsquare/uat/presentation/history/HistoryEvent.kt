package com.rmldemo.guardsquare.uat.presentation.history

sealed class HistoryEvent {
    data object GetHistory: HistoryEvent()
}