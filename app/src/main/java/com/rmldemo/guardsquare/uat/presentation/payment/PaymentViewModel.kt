package com.rmldemo.guardsquare.uat.presentation.payment

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.rmldemo.guardsquare.uat.domain.model.Service
import com.rmldemo.guardsquare.uat.domain.model.Transaction
import com.rmldemo.guardsquare.uat.domain.model.TransactionType
import com.rmldemo.guardsquare.uat.domain.usecase.TransactionUseCase
import com.rmldemo.guardsquare.uat.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val transactionUseCase: TransactionUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    var state by mutableStateOf(PaymentState())

    init {
        savedStateHandle.get<String>("service")?.let {
            state = state.copy(
                service = Gson().fromJson(it, Service::class.java)
            )
        }
    }

    fun onEvent(event: PaymentEvent) {
        when(event) {
            PaymentEvent.OnPayment -> {
                payment()
            }
        }
    }

    private fun payment() {
        viewModelScope.launch {
            val transaction = Transaction(
                id = "",
                userId = "",
                createdAt = "",
                name = state.service.name,
                imageUrl = state.service.imageUrl,
                amount = state.service.amount,
                type = TransactionType.MIN
            )
            transactionUseCase.invoke(transaction)
                .collect { result ->
                    when(result) {
                        is Resource.Success -> {
                            result.data?.let { version ->
                                state = state.copy(
                                    isLoading = false,
                                    isSuccess = true,
                                )
                            }
                        }
                        is Resource.Error -> {
                            state = state.copy(
                                isLoading = false,
                                isSuccess = false,
                                message = result.message.toString()
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