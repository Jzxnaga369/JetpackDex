package com.rmldemo.guardsquare.uat.presentation.transfer

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rmldemo.guardsquare.uat.domain.model.Transaction
import com.rmldemo.guardsquare.uat.domain.model.TransactionType
import com.rmldemo.guardsquare.uat.domain.usecase.TransactionUseCase
import com.rmldemo.guardsquare.uat.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransferViewModel @Inject constructor(
    private val transactionUseCase: TransactionUseCase
) : ViewModel() {
    var state by mutableStateOf(TransferUpState())

    fun onEvent(event: TransferEvent) {
        when(event) {
            is TransferEvent.OnAmountChange -> {
                changeAmount(event.amount)
            }
            TransferEvent.OnTransfer -> {
                transfer()
            }

            is TransferEvent.OnRecipientChange -> {
                changeRecipient(event.recipient)
            }
        }
    }

    private fun transfer() {
        if (state.amount.text == "") {
            state = state.copy(
                isLoading = false,
                isSuccess = false,
                message = "Transfer Amount Cannot Be 0"
            )
        } else {
            viewModelScope.launch {
                val transaction = Transaction(
                    id = "",
                    userId = "",
                    createdAt = "",
                    name = "Transfer",
                    imageUrl = "https://firebasestorage.googleapis.com/v0/b/vdipay-b5b37.appspot.com/o/transfer.svg?alt=media&token=58933ef7-f287-4341-9c23-acac68e6661d",
                    amount = state.amount.text.toLong(),
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

    private fun changeAmount(amount: TextFieldValue) {
        state = state.copy(
            amount = amount
        )
    }

    private fun changeRecipient(recipient: TextFieldValue) {
        state = state.copy(
            recipient = recipient
        )
    }
}