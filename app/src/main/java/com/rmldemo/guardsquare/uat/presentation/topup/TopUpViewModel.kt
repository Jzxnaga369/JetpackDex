package com.rmldemo.guardsquare.uat.presentation.topup

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
class TopUpViewModel @Inject constructor(
    private val transactionUseCase: TransactionUseCase
) : ViewModel() {
    var state by mutableStateOf(TopUpState())

    fun onEvent(event: TopUpEvent) {
        when(event) {
            is TopUpEvent.OnAmountChange -> {
                changeAmount(event.amount)
            }
            TopUpEvent.OnTopUp -> {
                topUp()
            }
        }
    }

    private fun topUp() {
        if (state.amount.text == "") {
            state = state.copy(
                isLoading = false,
                isSuccess = false,
                message = "Top Up Amount Cannot Be 0"
            )
        } else {
            viewModelScope.launch {
                val transaction = Transaction(
                    id = "",
                    userId = "",
                    createdAt = "",
                    name = "Top Up",
                    imageUrl = "https://firebasestorage.googleapis.com/v0/b/vdipay-b5b37.appspot.com/o/top-up.svg?alt=media&token=f3b49836-9e7e-4732-9f0b-22b6673af8d7",
                    amount = state.amount.text.toLong(),
                    type = TransactionType.PLUS
                )
                transactionUseCase.invoke(transaction)
                    .collect { result ->
                        when (result) {
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
}