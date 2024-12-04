package com.rmldemo.guardsquare.uat.presentation.notification

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rmldemo.guardsquare.uat.domain.usecase.GetNotificationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val getNotificationUseCase: GetNotificationUseCase
) : ViewModel() {
    var state by mutableStateOf(NotificationState())

    init {
        onEvent(NotificationEvent.GetNotifications)
    }

    private fun onEvent(event: NotificationEvent) {
        when(event) {
            NotificationEvent.GetNotifications -> {
                getNotifications()
            }
        }
    }

    private fun getNotifications() {
        viewModelScope.launch {
            getNotificationUseCase.invoke()
                .collect { result ->
                    state = state.copy(
                        notifications = result
                    )
                }
        }
    }
}