package com.rmldemo.guardsquare.uat.presentation.history

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rmldemo.guardsquare.uat.domain.usecase.GetHistoryUseCase
import com.rmldemo.guardsquare.uat.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getHistoryUseCase: GetHistoryUseCase
) : ViewModel() {
    var state by mutableStateOf(HistoryState())

    init {
        onEvent(HistoryEvent.GetHistory)
    }

    private fun onEvent(event: HistoryEvent) {
        when (event) {
            is HistoryEvent.GetHistory -> {
                getHistory()
            }
        }
    }

    private fun getHistory() {
        viewModelScope.launch {
            getHistoryUseCase.invoke()
                .collect { result ->
                    when(result) {
                        is Resource.Success -> {
                            result.data?.let { histories ->
                                state = state.copy(
                                    isLoading = false,
                                    histories = histories,
                                )
                            }
                        }
                        is Resource.Error -> {
                            state = state.copy(
                                isLoading = false,
                            )
                        }
                        is Resource.Loading -> {
                            state = state.copy(
                                isLoading = result.isLoading,
                            )
                        }
                    }
                }
        }
    }
}